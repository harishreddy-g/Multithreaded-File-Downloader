import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) {

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
            int downloaded =0;

            while((bytesRead = in.read(buffer)) != -1) {

                System.out.println(bytesRead);

                out.write(buffer, 0, bytesRead);
                downloaded+=bytesRead;
                int percentage = (downloaded*100)/fileSize;
                System.out.println("Downloaded : "+percentage+"%");

            }

            in.close();
            out.close();

            System.out.println("Download Complete");

        } catch(Exception e) {

            System.out.println(e);

        }
    }
}