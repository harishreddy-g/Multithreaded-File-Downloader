import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.FileOutputStream;

class DownloadTask implements Runnable {

    public void run() {

        try {

            URL url = new URL("https://picsum.photos/200");

            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            int responseCode = con.getResponseCode();

            int fileSize = con.getContentLength();

            System.out.println("Response Code: " + responseCode);

            System.out.println("File Size: " + fileSize);

            InputStream in = con.getInputStream();

            FileOutputStream out =
                    new FileOutputStream("img.jpg");

            byte[] buffer = new byte[4096];

            int bytesRead;

            int downloaded = 0;

            int lastPercentage = 0;

            while ((bytesRead = in.read(buffer)) != -1) {

                out.write(buffer, 0, bytesRead);

                downloaded += bytesRead;

                if (fileSize > 0) {

                    int percentage =
                            (downloaded * 100) / fileSize;

                    if (percentage != lastPercentage) {

                        System.out.println(
                                "Downloaded : "
                                + percentage + "%"
                        );

                        lastPercentage = percentage;
                    }
                }

                Thread.sleep(100);

            }

            in.close();
            out.close();

            System.out.println("Download Complete");

        } catch (Exception e) {

            System.out.println(e);

        }
    }
}

public class Main {

    public static void main(String[] args) {

        DownloadTask task = new DownloadTask();

        Thread t1 = new Thread(task);

        t1.start();

        System.out.println("Main Thread Running...");

    }
}