import email.EmailSender;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoXk implements Runnable {
    private String username;
    private String password;

    public AutoXk(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void run() {
        try {
            //Thread.sleep(10000);
            Request request = new Request();
            //获取登录界面
            String urlPath = "http://222.27.96.92/qzxk/xk/LoginToXkLdap?url=http://222.27.96.92:80/qzxk/xk/LoginToXkLdap";
            HttpResponse httpResponse = request.get(urlPath, new HashMap<String, String>());
            String response = EntityUtils.toString(httpResponse.getEntity());

//            String pattern = "<div class=\"images\".*?<img src=\"(.*?)\".*?</a>";
            String pattern = "sRand=(.*?)\"";
            Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
            Matcher m = r.matcher(response);
            String verityCode = new String();
            while (m.find()){
                verityCode = m.group(1);
            }
            //发送登录请求获得重定向地址和cookie
            urlPath = "http://222.27.96.92/qzxk/xk/LoginToXkLdap?url=http://222.27.96.92:80/qzxk/xk/LoginToXkLdap";
            String url = "222.27.96.92";
//            String str = "IDToken1=" + username + "&IDToken2=" + password + "&RANDOMCODE=m1c2&ymyzm=m1c2";
            String str = "IDToken1=" + username + "&IDToken2=" + password + "&RANDOMCODE="+ verityCode +"&ymyzm=" + verityCode;
            Map<String, String> headerMap = new HashMap<String, String>();
            headerMap.put("Host", "222.27.96.92");
            headerMap.put("Connection", "keep-alive");
            headerMap.put("Cache-Control", "max-age=0");
            headerMap.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            headerMap.put("Origin", "http://xk.nenu.edu.cn");
            headerMap.put("Upgrade-Insecure-Requests", "1");
            headerMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
            headerMap.put("Content-Type", "application/x-www-form-urlencoded");
            headerMap.put("Referer", "http://xk.nenu.edu.cn/xk/LoginToXkLdap?url=http://xk.nenu.edu.cn:80/xk/LoginToXkLdap");
            headerMap.put("Accept-Encoding", "gzip, deflate");
            headerMap.put("Accept-Language", "zh-CN,zh;q=0.8");
            httpResponse = request.post(url, urlPath, headerMap, str);
            if (httpResponse.getStatusLine().getStatusCode() == 500)
                throw new Exception("登录失败， 抛出500");
//            EmailSender.send("547240561@qq.com", "工作室程序成功执行");
//            EmailSender.send("2424996320@qq.com");
//            EmailSender.send("2428735896@qq.com");
//            EmailSender.send("1057936257@qq.com");
//            EmailSender.send("1240493284@qq.com");

            String cookie = httpResponse.getHeaders("Set-Cookie")[0].getValue();
            if (httpResponse.getHeaders("Location").length != 0) {
                urlPath = httpResponse.getHeaders("Location")[0].getValue();
            }
            url = urlPath.split("/")[2];//得到服务器地址
            //print(httpResponse);
            cookie = cookie.split(";")[0];//得到cookie

            //AccessToXk
            urlPath = "http://" + url + "/qzxk/xk/AccessToXk";
            headerMap = new HashMap<String, String>();
            headerMap.put("Cookie", cookie);
            request.get(urlPath, headerMap);
            //getXkInfo
            urlPath = "http://" + url + "/qzxk/xk/getXkInfo?jx0502zbid=85&jx0502id=39&sfktx=1&sfkxk=1";
            request.get(urlPath, headerMap);


//            String[] numbers = new String[]{"201820192005084", "201820192005071", "201820192005073", "201820192005086", "201820192005095", "201820192005094", "201820192005074", "201820192005087",
//                    "201820192005069", "201820192005082", "201820192005070", "201820192005083", "201820192005107", "201820192005105", "201820192005076", "201820192005089", "201820192003614",
//                    "201820192004511", "201820192003196", "201820192003208", "201820192003595", "201820192003635", "201820192003405", "201820192004677", "201820192003262", "201820192003268",
//                    "201820192005098", "201820192004421", "201820192003270", "201820192003271", "201820192004420", "201820192003337", "201820192003348", "201820192003322", "201820192003327",
//                    "201820192003318", "201820192003323", "201820192003320", "201820192003325", "201820192003225", "201820192003244", "201820192004497", "201820192003200", "201820192003212",
//                    "201820192003197", "201820192003209", "201820192003195", "201820192003207", "201820192003357", "201820192004541", "201820192003199", "201820192003211", "201820192003202",
//                    "201820192003214", "201820192003443", "201820192003497", "201820192004423", "201820192003440", "201820192003494", "201820192004425", "201820192004424", "201820192003522",
//                    "201820192004428", "201820192004427", "201820192004429", "201820192003455", "201820192003509", "201820192004430", "201820192004432", "201820192003429", "201820192003483",
//                    "201820192004431", "201820192004433", "201820192003437", "201820192003491"};
            String studentId = username;
            for (String number : Data.courseMap.keySet())
                new Thread(new Xk(number, cookie, studentId, url)).start();
        } catch (Exception e) {
            System.out.println("学号 ： " + username + "出现异常");
            run();
        }
    }
    private static void print(HttpResponse httpResponse) throws IOException {
        System.out.println(EntityUtils.toString(httpResponse.getEntity()));
    }
}
