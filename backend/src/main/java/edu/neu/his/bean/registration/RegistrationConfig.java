package edu.neu.his.bean.registration;

import java.util.HashMap;

public class RegistrationConfig {
    public static String registrationAvailable = "未看诊";
    public static String registrationCanceled = "已退号";
    public static String registrationFinished = "已看诊";
    public static HashMap<Integer, String> titleMap = new HashMap<>();

    public static void initTitleMap(){
        titleMap.put(1,"专家");
        titleMap.put(2,"主任");
    }

    public static String hashToTitle(int registration_level_id){
        RegistrationConfig.initTitleMap();
        String title = null;
        if(RegistrationConfig.titleMap.containsKey(registration_level_id))
            title = RegistrationConfig.titleMap.get(registration_level_id);
        return title;
    }
}
