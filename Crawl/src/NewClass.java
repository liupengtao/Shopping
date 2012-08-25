/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 *
 * @author liupengtao
 */
public class NewClass {
    public static void main(String[] args) {
        String datas = "{\"watershed\":100,\"isBeta\":true,\"isShowDefaultSort\":true,\"data\":{\"correspond\":\"4.7\",\"correspondCount\":267268,\"correspondList\":[\"80.13\",\"14.54\",\"3.90\",\"0.43\",\"0.99\"],\"count\":{\"additional\":1,\"bad\":0,\"correspond\":0,\"good\":120,\"goodFull\":427,\"hascontent\":0,\"normal\":2,\"total\":122},\"impress\":[],\"links\":null,\"refundTime\":0,\"spuRatting\":[]}}";
        JSONObject o = (JSONObject) JSON.parse(datas);
        System.out.println(o.getJSONObject("data").getString("correspond"));
        System.out.println(JSON.parse(datas));
    }
}
