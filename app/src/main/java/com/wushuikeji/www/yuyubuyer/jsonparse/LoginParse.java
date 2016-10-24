package com.wushuikeji.www.yuyubuyer.jsonparse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jack_chentao
 * @time 2016/10/13 0013 下午 1:56.
 * @des ${TODO}
 */
public class LoginParse {
    public static Map<String, String> loginParse(String jsonString) {

        Map<String, String> map = new HashMap<String, String>();
        try {
            JSONObject obj = new JSONObject(jsonString);
            JSONObject userObj = obj.getJSONObject("content");
            //用户的别名
            String username = userObj.optString("username");
            if (username == null || username.equals("")) {
                map.put("username", "");
            } else {
                map.put("username", username);
            }
            //用户的别名
            String id = userObj.optString("id");
            if (id == null || id.equals("")) {
                map.put("id", "");
            } else {
                map.put("id", id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return map;
    }
}
