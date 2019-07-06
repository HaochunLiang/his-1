package edu.neu.his.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response  {

    public static Map ok(Object data) {
        Map res = new HashMap();
        res.put("code",200);
        res.put("data",data);
        return res;
    }

    public static Map ok() {
        Map res = new HashMap();
        res.put("code",200);
        return res;
    }

    public static Map permissionDenied() {
        Map res = new HashMap();
        res.put("code",403);
        res.put("msg","permission denied");
        return res;
    }


    public static Map error(String errMsg) {
        Map res = new HashMap();
        res.put("code",500);
        res.put("msg",errMsg);
        return res;
    }

    public static Map error(Object data) {
        Map res = new HashMap();
        res.put("code",500);
        res.put("data",data);
        return res;
    }

}
