import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.FileOutputStream;

class DownloadTask implements Runnable {

    int start;
    int end;

    DownloadTask(int start, int end) {

        this.start = start;
        this.end = end;

    }

    public void run() {

        try {

            System.out.println(
                    Thread.currentThread().getName()
            );

            System.out.println(
                    "Downloading bytes: "
                    + start + " to " + end
            );

            URL url = new URL("https://picsum.photos/600");

            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            // DAY 9 CONCEPT
            con.setRequestProperty(
                    "Range",
                    "bytes=" + start + "-" + end
            );

            int responseCode =
                    con.getResponseCode();

            int fileSize =
                    con.getContentLength();

            System.out.println(
                    "Response Code: "
                    + responseCode
            );

            System.out.println(
                    "File Size: "
                    + fileSize
            );

            InputStream in =
                    con.getInputStream();

            FileOutputStream out =
                    new FileOutputStream(
                            "part_" + start + ".tmp"
                    );

            byte[] buffer =
                    new byte[4096];

            int bytesRead;

            int downloaded = 0;

            int lastPercentage = 0;

            while((bytesRead =
                    in.read(buffer)) != -1) {

                out.write(
                        buffer,
                        0,
                        bytesRead
                );

                downloaded += bytesRead;

                if(fileSize > 0) {

                    int percentage =
                            (downloaded * 100)
                            / fileSize;

                    if(percentage
                            != lastPercentage) {

                        System.out.println(

                                Thread.currentThread()
                                        .getName()

                                + " : "

                                + percentage + "%"

                        );

                        lastPercentage =
                                percentage;
                    }
                }

                Thread.sleep(100);

            }

            in.close();
            out.close();

            System.out.println(

                    Thread.currentThread()
                            .getName()

                    + " Download Complete"

            );

        } catch(Exception e) {

            System.out.println(e);

        }
    }
}

public class Main {

    public static void main(String[] args) {

        int fileSize = 1000;

        int numberOfThreads = 5;

        int chunkSize =
                fileSize / numberOfThreads;

        for(int i = 0;
            i < numberOfThreads;
            i++) {

            int start =
                    i * chunkSize;

            int end =
                    (start + chunkSize) - 1;

            if(i == numberOfThreads - 1) {

                end = fileSize - 1;

            }

            DownloadTask task =
                    new DownloadTask(start, end);

            Thread t =
                    new Thread(task);

            t.start();

        }

        System.out.println(
                "Main Thread Running..."
        );
    }
}