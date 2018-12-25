import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoXkMain {
    public static void main(String[] args) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date untileDate = simpleDateFormat.parse("2018-12-25 07:00:00");
            while(new Date().before(untileDate)){
                Thread.sleep(10000);
            }
            while(true){
                FileReader fr = new FileReader("userAndPassword");
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] user = line.split(" ");
                    String username = user[0];
                    String password = user[1];
//                    String username = "cuiyr208";
//                    String password = "2017012450cuiyi";
                    new Thread(new AutoXk(username, password)).start();
                }
                Thread.sleep(600000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
