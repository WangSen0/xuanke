import com.google.gson.Gson;
import email.EmailSender;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import java.util.HashMap;
import java.util.Map;

public class Xk implements Runnable {
    private String number;
    private String cookie;
    private String url;
    private String urlPath;
    private String studentId;
    private volatile static Map<String,Integer> countMap = new HashMap<String, Integer>();;

    public Xk(String number, String cookie, String studentId, String url) {
        this.number = number;
        this.cookie = cookie;
        this.studentId = studentId;
        countMap.put(studentId, 0);
        this.url = url;
        if(cookie.matches(".*amlbcookie.*")) {
            this.urlPath = "http://" + url + "/xk/processXk";
        }else {
            this.urlPath = "http://" + url + "/qzxk/xk/processXk";
        }
    }

    public void run() {
        int i=0;
        try{
            HttpPost httpPost = getGttpPost();
            while(true){
                Thread.sleep(200);
                HttpClient httpClient = new DefaultHttpClient();
                String str = "jx0502id=39&jx0404id=" + number + "&jx0502zbid=85";
                StringEntity entity = new StringEntity(str);
                httpPost.setEntity(entity);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                String response = EntityUtils.toString(httpEntity, "utf-8");

                Gson gson = new Gson();
                DataReturn data = gson.fromJson(response,DataReturn.class);
                if(data.getMsgContent().matches("您当前选择的课程与.*上课时间冲突")){
                    return;
                }else if(data.getMsgContent().matches("选课成功")){
                    countMap.put(studentId, countMap.get(studentId)+1);
                    System.out.println(data.getMsgContent() + " : " + Data.courseMap.get(number));
                    EmailSender.send(studentId + "@nenu.edu.cn", "成功抢到选修课 ： " + Data.courseMap.get(number));
                    return;
                }else if(data.getMsgContent().matches("不允许跨校区选课！")){
                    return;
                }else if(data.getMsgContent().matches("当前所选课程人数已满")){

                }else if(data.getMsgContent().matches("您已选过此课程")){
                    return;
                }else if(data.getMsgContent().matches("本次选课最多只能选34学分.*")){
                    System.out.println(data.getMsgContent() + "     " + studentId);
                    return;
                }else if(data.getMsgContent().matches("您的账号在其它地方登录")){
                    System.out.println(data.getMsgContent() + "     " + studentId);
                    return;
                }else if(data.getMsgContent().matches("系统更新了选课数据,请重新登录系统")){
                    System.out.println(data.getMsgContent() + "     " + studentId);
                    return;
                }else if(data.getMsgContent().matches("不在选课时间范围内，无法选课!!")){
                    System.out.println(data.getMsgContent());
                    return;
                }else if(data.getMsgContent().matches("请先登录系统")){
                    System.out.println(data.getMsgContent());
                    return;
                }else{
                    System.out.println("1111111111111" + data.getMsgContent());
                }
                int code= httpResponse.getStatusLine().getStatusCode();
                //System.out.println("学号" + studentId + " : " + code + "   " + response);
                i++;
                System.out.println(i + "    学号" + studentId + " : 已成功选取" + countMap.get(studentId) + "节课" + "         info : " + data.getMsgContent());
            }
        }catch (Exception e){
            i=0;
            run();
        }
    }

    public HttpPost getGttpPost() throws InterruptedException {
        HttpPost httpPost;
        if(cookie.matches(".*amlbcookie.*")) {
            httpPost = new HttpPost(urlPath);
            httpPost.setHeader("Host",url);
            httpPost.setHeader("Connection","keep-alive");
            httpPost.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
            httpPost.setHeader("Origin","http://" + url);
            httpPost.setHeader("X-Requested-With","XMLHttpRequest");
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setHeader("Referer","http://xk.nenu.edu.cn/xk/getXkInfo?jx0502zbid=85&jx0502id=39&sfktx=1&sfkxk=1");
            httpPost.setHeader("Accept-Encoding","gzip, deflate");
            httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
            httpPost.setHeader("Cookie",cookie);
        }else {
            httpPost = new HttpPost(urlPath);
            httpPost.setHeader("Host",url);
            httpPost.setHeader("Connection","keep-alive");
            httpPost.setHeader("Accept","application/json, text/javascript, */*; q=0.01");
            httpPost.setHeader("X-Requested-With","XMLHttpRequest");
            httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
            httpPost.setHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            httpPost.setHeader("Referer","http://"+ url +"/qzxk/xk/getXkInfo?jx0502zbid=85&jx0502id=39&sfktx=1&sfkxk=1");
            httpPost.setHeader("Accept-Encoding","gzip, deflate");
            httpPost.setHeader("Accept-Language","zh-Hans-CN, zh-Hans; q=0.5");
            httpPost.setHeader("Cache-Control","no-cache");
            httpPost.setHeader("Cookie",cookie);
        }
        return httpPost;
    }
}
