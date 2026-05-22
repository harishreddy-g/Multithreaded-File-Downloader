import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.FileOutputStream;

public class Main {
    public static void main(String[] args) {
        try {

            URL url = new URL("https://picsum.photos/200");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream in = con.getInputStream();
            FileOutputStream out= new FileOutputStream("img.jpg");

            // int responseCode = con.getResponseCode();
            // int length = con.getContentLength();
            // System.out.println("Response Code : " + responseCode);
            // System.out.println(length);
            byte[] buffer = new byte[4096];
            int byteread;

            while ((byteread = in.read(buffer)) !=-1) {
                System.out.println(byteread);
                out.write(buffer, 0 , byteread);
            }


            in.close();
            out.close();
            System.out.println("download completed");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
