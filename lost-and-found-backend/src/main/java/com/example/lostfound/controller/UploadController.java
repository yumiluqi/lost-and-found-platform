package com.example.lostfound.controller;

import com.example.lostfound.common.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传接口
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    /** 上传文件存储目录（项目根目录下的 upload 文件夹） */
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/upload/";

    @PostMapping
    public Result<Map<String, String>> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error(400, "文件为空");
        }

        // 获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";

        // 重命名，防止重名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + suffix;

        // 确保目录存在
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 保存文件
        try {
            file.transferTo(new File(UPLOAD_DIR + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(500, "上传失败：" + e.getMessage());
        }

        // 返回访问URL
        String url = "/upload/" + newFileName;
        Map<String, String> data = new HashMap<>();
        data.put("url", url);
        return Result.success("上传成功", data);
    }
}
