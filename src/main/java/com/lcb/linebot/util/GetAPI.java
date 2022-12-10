package com.lcb.linebot.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
public class GetAPI {

    public static String httpRequest(String requestUrl){
            //buffer用於接受返回的字元
            StringBuffer buffer = new StringBuffer();
            try {
                //建立URL,把請求地址給補全,其中urlencode()方法用於把params裡的引數給取出來
                URL url = new URL(requestUrl);
                //開啟http連線
                HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
                httpUrlConn.setDoInput(true);
                httpUrlConn.setRequestMethod("GET");
                httpUrlConn.connect();

                //獲得輸入
                InputStream inputStream = httpUrlConn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                //將bufferReader的值給放到buffer裡
                String str = null;
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                //關閉bufferReader和輸入流
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                inputStream = null;
                //斷開連線
                httpUrlConn.disconnect();

            } catch (Exception e) {
                e.printStackTrace();
            }
            //返回字串
            return buffer.toString();
        }


        public static void main(String[]args){
            String str = GetAPI.httpRequest("https://tour.klcg.gov.tw/data/restaurants.json");
            System.out.println(str);
        }
}
