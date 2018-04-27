package de.iotioten.testclient;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestClient {


    public static void main(String[] args) throws IOException {
        String postURL = "http://localhost:8080/rest/iot/anything";

        HttpPost post = new HttpPost(postURL);

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("topic", "test/test2"));
        params.add(new BasicNameValuePair("msg", "halloWastl"));

        UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(ent);

        HttpClient client = new DefaultHttpClient();
        HttpResponse responsePOST = client.execute(post);
        System.out.println("ResponseCode: " + responsePOST.getStatusLine());
    }


}
