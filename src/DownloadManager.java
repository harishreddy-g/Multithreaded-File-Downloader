package src;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class DownloadManager {

    private String url;
    private int fileSize;
    private int numberOfThreads;

    public DownloadManager(
            String url,
            int fileSize,
            int numberOfThreads) {

        this.url = url;
        this.fileSize = fileSize;
        this.numberOfThreads = numberOfThreads;
    }

    public void startDownload() {

        try {

            int chunkSize = fileSize / numberOfThreads;

            Thread[] threads = new Thread[numberOfThreads];

            for (int i = 0; i < numberOfThreads; i++) {

                int start = i * chunkSize;

                int end = (start + chunkSize) - 1;

                if (i == numberOfThreads - 1) {

                    end = fileSize - 1;
                }

                System.out.println(
                        "Thread "
                                + (i + 1)
                                + " -> "
                                + start
                                + " to "
                                + end);

                DownloadTask task = new DownloadTask(
                        url,
                        start,
                        end);

                Thread t = new Thread(task);

                threads[i] = t;

                t.start();
            }

            System.out.println(
                    "Main Thread Waiting...");

            for (int i = 0; i < numberOfThreads; i++) {

                threads[i].join();
            }

            System.out.println(
                    "\nAll downloads completed.");

            mergeFiles(chunkSize);

        } catch (Exception e) {

            System.out.println(e);
        }
    }

    private void mergeFiles(
            int chunkSize) {

        try {

            FileOutputStream mergedFile = new FileOutputStream(
                    "final_image.jpg");

            byte[] buffer = new byte[4096];

            for (int i = 0; i < numberOfThreads; i++) {

                int start = i * chunkSize;

                String fileName = "part_"
                        + start
                        + ".tmp";

                System.out.println(
                        "Merging "
                                + fileName);

                FileInputStream in = new FileInputStream(
                        fileName);

                int bytesRead;

                while ((bytesRead = in.read(buffer)) != -1) {

                    mergedFile.write(
                            buffer,
                            0,
                            bytesRead);
                }

                in.close();
            }

            mergedFile.close();

            System.out.println(
                    "\nMerge Complete!");

            System.out.println(
                    "Final File: final_image.jpg");

        } catch (Exception e) {

            System.out.println(e);
        }
    }
}