package cn.yog.oss.controller.base;

import cn.yog.core.bean.Result;
import cn.yog.core.exception.GlobalException;
import cn.yog.oss.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yog
 * @date：Created in 2020/6/2 22:45
 */
@RestController("/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/picture")
    public Result pictureUpload(@RequestParam MultipartFile file) throws GlobalException {
        return fileUploadService.pictureUpload(file);
    }
}
