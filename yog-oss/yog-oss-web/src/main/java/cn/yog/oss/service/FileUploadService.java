package cn.yog.oss.service;

import cn.yog.core.bean.Result;
import cn.yog.core.exception.GlobalException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author yog
 * @date：Created in 2020/6/2 22:45
 */
public interface FileUploadService {
    Result pictureUpload(MultipartFile file) throws GlobalException;
}
