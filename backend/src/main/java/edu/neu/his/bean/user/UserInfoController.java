package edu.neu.his.bean.user;

import edu.neu.his.config.Response;
import edu.neu.his.util.Crypto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserService userService;

    @PostMapping("/info")
    @ResponseBody
    public Map getUserInfo(@RequestBody Map req) {
        //System.out.println(req);
        int uid = (int)req.get("_uid");
        User user = userService.findByUid(uid);
        if(user!=null) {
            user.setPassword(null);
            return Response.ok(user);
        }else
            return Response.error("用户信息读取失败，请检查数据库约束！");
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public Map updateUserInfo(@RequestBody Map req) {
        //System.out.println(req);
        int uid = (int)req.get("_uid");
        String password = (String)req.get("password");
        User user = userService.findByUid(uid);
        user.setPassword(Crypto.md5(password));
        if(user!=null) {
            user.setPassword(null);
            return Response.ok(user);
        }else
            return Response.error("用户信息修改失败，请检查数据库约束！");
    }

}
