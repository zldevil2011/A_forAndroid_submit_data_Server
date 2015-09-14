package com.example.demo;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button submit = (Button)findViewById(R.id.submit);

        submit.setOnClickListener(new OnClickListener() {
          public void onClick(View v) {
        	TextView result = (TextView)findViewById(R.id.login_result);
        	EditText e_u = (EditText)findViewById(R.id.username), e_p = (EditText)findViewById(R.id.password);
        	String login_username = e_u.getText().toString(), login_password = e_p.getText().toString();
        	String user_info = "username:" + login_username + "  password:" + login_password;
            Toast tst = Toast.makeText(MainActivity.this, user_info, Toast.LENGTH_SHORT);
            tst.show();
            Map map = new HashMap<String, String>();
            map.put("username", login_username);
            map.put("password", login_password);
            String path_post = "http://123.57.206.52/api/user/login/";
            String path_post2 = "http://139.162.25.140:8000/api/login/";
            String path_get = "http://123.57.206.52/homepage/";
            try {
//            	String login_res = submitDataByHttpClientDoGet(map, path);
				String login_res = submitDataByDoPost(map, path_post2);
				result.setText(login_res);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Log.v("zl_exp", "exception");
				result.setText("netword error");
				e.printStackTrace();
			}
          }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public String submitDataByDoPost(Map<String, String> map, String path) throws Exception {
        URL Url = new URL(path);
        JSONObject user_info = new JSONObject();
        user_info.put("username", "kdq");
        user_info.put("password", "kdq");
        String content = String.valueOf(user_info);
        HttpURLConnection HttpConn = (HttpURLConnection) Url.openConnection();
        HttpConn.setRequestMethod("POST");
        HttpConn.setReadTimeout(5000);
        HttpConn.setDoOutput(true);
        HttpConn.setRequestProperty("Content-Type", "application/json");
//        HttpConn.setRequestProperty("Content-Length", String.valueOf(str.getBytes().length));
        OutputStream os = HttpConn.getOutputStream();
        os.write(content.getBytes());
        if (HttpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return "login_success";
        }
        return "login_failed";
    }
    
    
    public String submintDataByHttpClientDoPost(Map<String, String> map, String path) throws Exception {
    	Log.v("zl_start", "start");
        // 1. 获得一个相当于浏览器对象HttpClient，使用这个接口的实现类来创建对象，DefaultHttpClient
        HttpClient hc = new DefaultHttpClient();
        // DoPost方式请求的时候设置请求，关键是路径
        HttpPost request = new HttpPost(path);
        // 2. 为请求设置请求参数，也即是将要上传到web服务器上的参数
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        
//        for (Map.Entry<String, String> entry : map.entrySet()) {
//            NameValuePair nameValuePairs = new BasicNameValuePair(entry.getKey(), entry.getValue());
//            parameters.add(nameValuePairs);
//        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "kdq");
        jsonObject.put("password", "kdq");
//        jsonObject.put("client_id", "5Cyhkmk00K8rbuks730nQb9cfKrjVeYf8oDyZkTy");
//        jsonObject.put("client_secret", "Bpxd0QjFu1F1qWKFcjSL5ymM0ngfmBvpwvRBlnaya60qAvfynd7XYxEDJGw8aYzZYczJ9ePGl1GymALDijS0ocCTRnNleZbpkdRB2bcxK20USckP77entF4SYyhj3BrR");
        parameters.add(new BasicNameValuePair("jsonString", jsonObject.toString()));
//        parameters.add(jsonObject.toString());
        System.out.println("the_paramter");
        System.out.println(jsonObject);
        System.out.println(parameters);
        Log.v("zl_middle", "middle");
        // 请求实体HttpEntity也是一个接口，我们用它的实现类UrlEncodedFormEntity来创建对象，注意后面一个String类型的参数是用来指定编码的
        HttpEntity entity = new UrlEncodedFormEntity(parameters, "UTF-8");
        request.setEntity(entity);
        Log.v("zl_response", "response");
        // 3. 执行请求
        HttpResponse response = hc.execute(request);
        // 4. 通过返回码来判断请求成功与否
        Log.v("zl_end", "end");
        if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
            return "login_success";
        }
        return "login_failed";
    }
    public String submitDataByHttpClientDoGet(Map<String, String> map, String path) throws Exception {
        HttpClient hc = new DefaultHttpClient();
        // 请求路径
        StringBuilder sb = new StringBuilder(path);
        sb.append("?");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        String str = sb.toString();
        System.out.println(str);
        HttpGet request = new HttpGet(sb.toString());
        HttpResponse response = hc.execute(request);
        if (response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK) {
            return "get_OK";
        }
        return "get_failed";
    }
}
