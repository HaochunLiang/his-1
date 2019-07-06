package edu.neu.his.config;

import java.util.Map;

public class Auth {
    public static int uid(Map req){
        if(req.containsKey("_uid")){
            return (int)req.get("_uid");
        }else {
            System.out.println("CommonError:uid can't be found, check middleware config plz.");
            System.exit(1);
            return  -1;
        }
    }
}
