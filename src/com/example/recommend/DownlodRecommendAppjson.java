package com.example.recommend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;


public class DownlodRecommendAppjson {
	
	private static final String TAG = DownlodRecommendAppjson.class.getSimpleName();

	public static String getJsonString(String Url) {
        HttpClient client = new DefaultHttpClient();
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000); // 接続のタイムアウト
        HttpConnectionParams.setSoTimeout(params, 5000); // データ取得のタイムアウト
        
        String jsonString = "";
        
        try {
            HttpGet httpGet = new HttpGet(Url);
            HttpResponse response = client.execute(httpGet);
            
            // エラーコードが帰ってきた場合
            boolean isSuccessCode = response.getStatusLine().getStatusCode() < 400;
            if (!isSuccessCode) {
                return null;
            }
            
            InputStream inputStream = response.getEntity().getContent();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            
            StringBuilder jsonBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonBuilder.append(line);
            }
            
            jsonString = jsonBuilder.toString();
            inputStream.close();
            
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            
            return null;
        }
    }
	
}
