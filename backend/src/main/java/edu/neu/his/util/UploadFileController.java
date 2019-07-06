package edu.neu.his.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UploadFileController {
    private static final Logger log = LoggerFactory.getLogger(UploadFileController.class);

    @PostMapping("/upload")
    @ResponseBody
    public Map uploadFile(@RequestBody Map req){
        MultipartFile file = (MultipartFile)req.get("uploadFile");
        Map res = new HashMap();
        try {
            if (file.isEmpty()) {
                res.put("code",500);
                res.put("msg","The file is empty.");
                res.put("data",null);
                return  res;
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);
            // 设置文件存储路径
            String filePath = "/src/main/upload/";
            String path = filePath + fileName;
            File dest = new File(path);
            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }
            file.transferTo(dest);// 文件写入
            res.put("code",200);
            res.put("msg","");
            res.put("data",null);
        } catch (Exception e) {
            res.put("code",500);
            res.put("msg","Upload failed.");
            res.put("data",null);
        }finally{
            return res;
        }
    }
}
