import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/12/23.
 */
public class LoginTest {
    @org.junit.Test
    public void loginTest(){
        try {
            String url = "http://222.27.96.92/qzxk/xk/processXk";
            Request request = new Request();
            HttpResponse httpResponse = request.get(url, new HashMap<String, String>());
            String cookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
            System.out.println(cookie);

            String urlPath = "http://222.27.96.92/qzxk/xk/LoginToXkLdap?url=http://222.27.96.92:80/qzxk/xk/LoginToXkLdap";
            url = "222.27.96.92";
            Scanner scanner = new Scanner(System.in);
//        String username = scanner.nextLine();
////        String password = scanner.nextLine();
            String username = "dingxs206";
            String password = "Z131420..";
            String str = "IDToken1="+ username +"&IDToken2="+ password +"&RANDOMCODE=m1c2&ymyzm=m1c2";
            Map<String,String> headerMap = new HashMap<String, String>();
            headerMap.put("Host","222.27.96.92");
            headerMap.put("Connection","keep-alive");
            headerMap.put("Cache-Control","max-age=0");
            headerMap.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            headerMap.put("Origin","http://xk.nenu.edu.cn");
            headerMap.put("Upgrade-Insecure-Requests","1");
            headerMap.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
            headerMap.put("Content-Type","application/x-www-form-urlencoded");
            headerMap.put("Referer","http://xk.nenu.edu.cn/xk/LoginToXkLdap?url=http://xk.nenu.edu.cn:80/xk/LoginToXkLdap");
            headerMap.put("Accept-Encoding","gzip, deflate");
            headerMap.put("Accept-Language","zh-CN,zh;q=0.8");

            httpResponse = request.post(url, urlPath, headerMap, str);
            cookie = httpResponse.getHeaders("Set-Cookie")[0].getValue();
            urlPath = httpResponse.getHeaders("Location")[0].getValue();
            url = urlPath.split("/")[2];

            headerMap = new HashMap<String, String>();
            headerMap.put("Cookie", cookie);


            httpResponse = request.post("", urlPath, headerMap, "");

            cookie = cookie.split(";")[0];

            String[] numbers = new String[]{"201820192005084", "201820192005071", "201820192005073", "201820192005086", "201820192005095", "201820192005094", "201820192005074", "201820192005087",
                    "201820192005069", "201820192005082", "201820192005070", "201820192005083", "201820192005107", "201820192005105", "201820192005076", "201820192005089", "201820192003614",
                    "201820192004511", "201820192003196", "201820192003208", "201820192003595", "201820192003635", "201820192003405", "201820192004677", "201820192003262", "201820192003268",
                    "201820192005098", "201820192004421", "201820192003270", "201820192003271", "201820192004420", "201820192003337", "201820192003348", "201820192003322", "201820192003327",
                    "201820192003318", "201820192003323", "201820192003320", "201820192003325", "201820192003225", "201820192003244", "201820192004497", "201820192003200", "201820192003212",
                    "201820192003197", "201820192003209", "201820192003195", "201820192003207", "201820192003357", "201820192004541", "201820192003199", "201820192003211", "201820192003202",
                    "201820192003214", "201820192003443", "201820192003497", "201820192004423", "201820192003440", "201820192003494", "201820192004425", "201820192004424", "201820192003522",
                    "201820192004428", "201820192004427", "201820192004429", "201820192003455", "201820192003509", "201820192004430", "201820192004432", "201820192003429", "201820192003483",
                    "201820192004431", "201820192004433", "201820192003437", "201820192003491"};
            String studentId = "2016102530";
            for (String number : numbers)
                new Thread(new Xk(number, cookie, studentId, url)).start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void test2() throws InterruptedException, ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date untileDate = simpleDateFormat.parse("2018-12-24 07:00:00");
        while(new Date().before(untileDate)){
            Thread.sleep(10000);
        }
    }
    @Test
    public void test3(){
//        String response = " <td><span id=\"SafeCodeImg\">\n" +
//                "                        <input type=\"hidden\" id=\"ymyzm\" name=\"ymyzm\" value=\"2mnb\" />\n" +
//                "                         <img src=\"/qzxk/verifycode.servlet?sRand=2mnb\" onclick=\"ReShowCode()\" align=\"middle\" width=\"50\" height=\"22\" /></span></td>\n" +
//                "        </tr>";
//
////            String pattern = "<div class=\"images\".*?<img src=\"(.*?)\".*?</a>";
//        String pattern = "<img src=\"/qzxk/verifycode.servlet?sRand=(.*?)\" onclick=\"ReShowCode()\"";
//        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
//        Matcher m = r.matcher(response);
//        String verityCode = new String();
//        while (m.find()){
//            verityCode = m.group(1);
//        }
//        System.out.println(verityCode);
        String response = "<img src=\"/qzxk/verifycode.servlet?sRand=2mnb\" onclick=\"ReShowCode()\"";

//            String pattern = "<div class=\"images\".*?<img src=\"(.*?)\".*?</a>";
        String pattern = "sRand=(.*?)\"";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(response);
        String verityCode = new String();
        while (m.find()){
            verityCode = m.group(1);
        }
        System.out.println(verityCode);
    }
}
