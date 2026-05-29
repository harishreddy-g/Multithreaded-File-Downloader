package src;
public class Main {

        public static void main(String[] args) {

                String url = "https://picsum.photos/600";

                int fileSize = 1000;

                int numberOfThreads = 5;

                DownloadManager manager = new DownloadManager(
                                url,
                                fileSize,
                                numberOfThreads);

                manager.startDownload();
        }
}