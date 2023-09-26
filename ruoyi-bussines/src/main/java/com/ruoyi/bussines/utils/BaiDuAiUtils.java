package com.ruoyi.bussines.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;

public class BaiDuAiUtils {
    static final OkHttpClient HTTP_CLIENT = new OkHttpClient().newBuilder().build();

    static final String ACCESS_TOKEN = "24.c9f1627d00e9c2e4b696e2b4d0f6cd4e.2592000.1698299336.282335-40008522";

    public static JSONArray baiduAiParsePic(String base64Content) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        // 当前线程沉睡3秒
        try {
            Thread.sleep(3000);
            RequestBody body = RequestBody.create(mediaType, "image=" + base64Content
                    + "&detect_direction=false&paragraph=false&probability=false");
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=" + ACCESS_TOKEN)
                    .method("POST", body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Accept", "application/json")
                    .build();
            Response response = HTTP_CLIENT.newCall(request).execute();
            ResponseBody responseBody = response.body();

            String json = responseBody.string();

            JSONObject jsonObject = JSONObject.parseObject(json);
            JSONArray wordsResult = jsonObject.getJSONArray("words_result");
            JSONArray jsonArray = new JSONArray();
            for (Object o : wordsResult) {
                JSONObject wordObject = (JSONObject) o;
                String words = wordObject.getString("words");
                jsonArray.add(words);
            }
            return jsonArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}