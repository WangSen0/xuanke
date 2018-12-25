import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Map;

public class Request {

    public HttpResponse get(String url, Map<String, String> cookie) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        for(String key : cookie.keySet()) {
            httpGet.setHeader(key, cookie.get(key));
        }
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        return httpCilent.execute(httpGet);
    }

    public HttpResponse post(String url, String urlPath, Map<String, String> cookie, String str) throws InterruptedException, IOException {
        HttpPost httpPost = new HttpPost(urlPath);
        for(String key : cookie.keySet()) {
            httpPost.setHeader(key, cookie.get(key));
        }
        HttpClient httpClient = new DefaultHttpClient();
        StringEntity entity = new StringEntity(str);
        httpPost.setEntity(entity);
        return  httpClient.execute(httpPost);
    }

}
