package com.ipicbook.fangdoc.controller;

import com.ipicbook.fangdoc.utils.ExtractImg;
import com.ipicbook.fangdoc.utils.ExtractText;
import com.ipicbook.fangdoc.utils.ResultUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FileController {

    @RequestMapping(value = "loadDoc", method = RequestMethod.POST)
    @ResponseBody
    public Object loadDoc(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file.isEmpty()) {
            return ResultUtil.error(20001, "没有上传文件");
        } else {
            String filename = file.getOriginalFilename();
            if (StringUtils.isEmpty(filename)) {
                return ResultUtil.error(20002, "上传文件为空");
            } else {
                String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadFiles");
                String suffix = FilenameUtils.getExtension(file.getOriginalFilename());//原文件后缀
                int fileSize = 100000000;
                if (file.getSize() > fileSize) {//上传大小不得超过 5000k
                    return ResultUtil.error(20003, "上传大小不得超过100M");
                } else if (suffix.equalsIgnoreCase("doc") || suffix.equalsIgnoreCase("docx")) {//上传图片格式不正确
                    String fileName = System.currentTimeMillis() + "." + suffix;
                    File targetFile = new File(path, fileName);
                    if (!targetFile.exists()) {
                        targetFile.mkdirs();
                    }
                    //保存
                    try {
                        file.transferTo(targetFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return ResultUtil.error(20004, "上传失败");
                    }
                    String text = ExtractText.parse(targetFile.getAbsolutePath());
                    List<String> imgUrls = ExtractImg.parse(path,targetFile.getAbsolutePath());
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("text", text);
                    map.put("imgUrls", imgUrls);
                    return ResultUtil.success(map);
                }
                return ResultUtil.error(20005, "上传文件不是WORD文件");
            }
        }
    }
}
