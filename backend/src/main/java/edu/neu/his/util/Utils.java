package edu.neu.his.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.neu.his.bean.user.User;
import edu.neu.his.bean.user.UserMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Utils {


    public static Utils initUtils;

    @PostConstruct
    public void init() {
        initUtils = this;
    }


    public static String getSystemTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static <T> T fromMap(Map map, Class<T> tClass){
        if(map.get("_uid")!=null){
            map.put("user_id", map.get("_uid"));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String json = JSONObject.fromObject(map).toString();
        T object = null;
        try {
            object = objectMapper.readValue(json, tClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }


    public static Map<String, Object> objectToMap(Object obj){
        if(obj == null)
            return null;

        Map<String, Object> map = new HashMap<String, Object>();

        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object value = null;
            try {
                value = getter!=null ? getter.invoke(obj) : null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            map.put(key, value);
        }
        return map;
    }


    @Autowired
    private UserMapper userMapper;

    public static User getSystemUser(Map req){
        return initUtils.userMapper.find((int)req.get("_uid"));
    }

    public static String getFileSuffix(String filename){
        String[] arr = filename.split("\\.");
        if(arr.length < 2){
            return null;
        }
        return arr[arr.length - 1];

    }
    
    public static Map initMap(Map<String,Object> map){
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue()==null)
                entry.setValue("");
        }
        return map;
    }
}
