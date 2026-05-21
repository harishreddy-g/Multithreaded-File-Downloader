import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;



public class Main {
    public static void main(String[] args) {
        try{
           
            URL url = new URL("https://google.com");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
             InputStream in = con.getInputStream();
            int responseCode = con.getResponseCode();
            int length= con.getContentLength();
            System.out.println("Response Code : "+ responseCode);
            System.out.println(length);
            int data;

            while((data= in.read())<1480){
                System.out.print((char)data);

            }
           

            in.close();
        }catch(Exception e){
            System.out.println(e);
        }

    }
}
