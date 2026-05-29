import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;

class DownloadTask implements Runnable {

    private int start;
    private int end;

    DownloadTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " downloading bytes "
                            + start + " - " + end
            );

            URL url = new URL("https://picsum.photos/600");

            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            // DAY 9: RANGE HEADER
            con.setRequestProperty(
                    "Range",
                    "bytes=" + start + "-" + end
            );

            int responseCode =
                    con.getResponseCode();

            int fileSize =
                    con.getContentLength();

            System.out.println(
                    Thread.currentThread().getName()
                            + " Response Code: "
                            + responseCode
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

            while ((bytesRead = in.read(buffer)) != -1) {

                out.write(
                        buffer,
                        0,
                        bytesRead
                );

                downloaded += bytesRead;

                if (fileSize > 0) {

                    int percentage =
                            (downloaded * 100)
                                    / fileSize;

                    if (percentage != lastPercentage) {

                        System.out.println(
                                Thread.currentThread()
                                        .getName()
                                        + " : "
                                        + percentage
                                        + "%"
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
                    Thread.currentThread().getName()
                            + " Download Complete"
            );

        } catch (Exception e) {

            System.out.println(
                    Thread.currentThread().getName()
                            + " Error: "
                            + e
            );
        }
    }
}

public class Main {

    public static void main(String[] args) {

        try {

            // DAY 8: FILE SPLITTING

            int fileSize = 1000; // learning purpose

            int numberOfThreads = 5;

            int chunkSize =
                    fileSize / numberOfThreads;

            Thread[] threads =
                    new Thread[numberOfThreads];

            for (int i = 0;
                 i < numberOfThreads;
                 i++) {

                int start =
                        i * chunkSize;

                int end =
                        (start + chunkSize) - 1;

                if (i ==
                        numberOfThreads - 1) {

                    end = fileSize - 1;
                }

                System.out.println(
                        "Thread "
                                + (i + 1)
                                + " -> "
                                + start
                                + " to "
                                + end
                );

                DownloadTask task =
                        new DownloadTask(
                                start,
                                end
                        );

                Thread t =
                        new Thread(task);

                threads[i] = t;

                t.start();
            }

            System.out.println(
                    "Main Thread Waiting..."
            );

            // DAY 10: JOIN

            for (int i = 0;
                 i < numberOfThreads;
                 i++) {

                threads[i].join();
            }

            System.out.println(
                    "\nAll downloads completed."
            );

            // DAY 10: MERGE FILES

            FileOutputStream mergedFile =
                    new FileOutputStream(
                            "final_image.jpg"
                    );

            byte[] buffer =
                    new byte[4096];

            for (int i = 0;
                 i < numberOfThreads;
                 i++) {

                int start =
                        i * chunkSize;

                String fileName =
                        "part_"
                                + start
                                + ".tmp";

                System.out.println(
                        "Merging "
                                + fileName
                );

                FileInputStream in =
                        new FileInputStream(
                                fileName
                        );

                int bytesRead;

                while ((bytesRead =
                        in.read(buffer)) != -1) {

                    mergedFile.write(
                            buffer,
                            0,
                            bytesRead
                    );
                }

                in.close();
            }

            mergedFile.close();

            System.out.println(
                    "\nMerge Complete!"
            );

            System.out.println(
                    "Final File: final_image.jpg"
            );

        } catch (Exception e) {

            System.out.println(e);
        }
    }
}