package edu.neu.his.bean.operateLog;

import java.util.HashMap;

public class OperateStatus {
    public static String Register = "挂号";
    public static String Cancel = "退号";
    public static String PrescribeMedicine = "开药";
    public static HashMap<Integer, String> operateMap = new HashMap<>();

    public static void initOperateMap(){
        operateMap.put(3, "检验");
        operateMap.put(7, "检查");
        operateMap.put(16, "处置");
    }
}
