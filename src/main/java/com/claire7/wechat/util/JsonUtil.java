package com.claire7.wechat.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by zhongnanhuang209074 on 2018/5/12.
 */
public class JsonUtil {


    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static GsonBuilder gsonBuilder = new GsonBuilder();
    private static Gson gson = null;

    public static String toJson(Object obj) {
        if (gson == null){
            init();
        }
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        if (gson == null){
            init();
        }
        return gson.fromJson(json,classOfT);
    }

    private synchronized static void init(){
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.serializeSpecialFloatingPointValues();
        gson = gsonBuilder.create();
    }

    public static Map<String,Object> jsonToMap(String json){
        try {
            Map<String,Object> result = new HashMap<String,Object>();
            JSONObject jsonObject = new JSONObject(json);
            result = (Map<String, Object>) parseNewsNode(jsonObject);
            return result;
        }catch (Exception e){
            logger.error("json to map error {}",e);
        }
        return null;
    }

    private static Object parseNewsNode(Object node){

        try {
            if (node instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) node;
                List<Object> res = new ArrayList<Object>();
                int len = jsonArray.length();
                for (int i = 0;i<len;i++){
                    res.add(parseNewsNode(jsonArray.get(i)));
                }
                return res;
            } else if (node instanceof JSONObject){
                Map<String,Object> result = new HashMap<String,Object>();
                JSONObject jsonObject = (JSONObject)node;
                Iterator<String> keys = jsonObject.keys();
                while(keys.hasNext()){
                    String key = keys.next();
                    result.put(key,parseNewsNode(jsonObject.get(key)));
                }
                return result;
            } else if (node instanceof String || node instanceof Integer || node instanceof Double || node instanceof Long) {
                return node;
            }else{
                return null;
            }
        }catch (Exception e) {
            return null;
        }
    }

}
