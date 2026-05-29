package src;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.FileOutputStream;

public class DownloadTask implements Runnable {

    private String url;
    private int start;
    private int end;

    public DownloadTask(String url, int start, int end) {
        this.url = url;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " downloading bytes "
                            + start + " - " + end);

            URL fileUrl = new URL(url);

            HttpURLConnection con = (HttpURLConnection) fileUrl.openConnection();

            con.setRequestProperty(
                    "Range",
                    "bytes=" + start + "-" + end);

            int responseCode = con.getResponseCode();

            int fileSize = con.getContentLength();

            System.out.println(
                    Thread.currentThread().getName()
                            + " Response Code: "
                            + responseCode);

            InputStream in = con.getInputStream();

            FileOutputStream out = new FileOutputStream(
                    "part_" + start + ".tmp");

            byte[] buffer = new byte[4096];

            int bytesRead;

            int downloaded = 0;

            int lastPercentage = 0;

            while ((bytesRead = in.read(buffer)) != -1) {

                out.write(buffer, 0, bytesRead);

                downloaded += bytesRead;

                if (fileSize > 0) {

                    int percentage = (downloaded * 100)
                            / fileSize;

                    if (percentage != lastPercentage) {

                        System.out.println(
                                Thread.currentThread()
                                        .getName()
                                        + " : "
                                        + percentage
                                        + "%");

                        lastPercentage = percentage;
                    }
                }

                Thread.sleep(100);
            }

            in.close();
            out.close();

            System.out.println(
                    Thread.currentThread().getName()
                            + " Download Complete");

        } catch (Exception e) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " Error: "
                            + e);
        }
    }
}