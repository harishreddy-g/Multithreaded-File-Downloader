import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;


// class Calc {

//     int i ;
//     int j;
//     Calc(int i , int j){
//         this.i = i;
//         this.j =j;
//     }
//     public int add(){
//         return i+j;
//     }
//     public int sub(){
//         return i-j;
//     }
//     public int multiply(){
//         return i*j;
//     }
// }




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





        // System.out.println("===  Student Info: ===");
        // System.out.println("Harish Reddy");
        // System.out.println("Age: 20");
        // System.out.println("Roll No : 85");
        // Calc c = new Calc(2,2);
        
        // System.out.println(c.add());
        // System.out.println(c.sub());
        // System.out.println(c.multiply());

    }
}
