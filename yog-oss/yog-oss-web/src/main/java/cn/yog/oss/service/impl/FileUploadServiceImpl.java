package cn.yog.oss.service.impl;

import cn.yog.core.bean.Result;
import cn.yog.core.bean.ResultCode;
import cn.yog.core.exception.GlobalException;
import cn.yog.core.util.ResultUtil;
import cn.yog.oss.config.UploadConfig;
import cn.yog.oss.service.FileUploadService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

/**
 * @author yog
 * @date：Created in 2020/6/2 22:45
 */
@Service
@Slf4j
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private UploadConfig uploadConfig;

    @Autowired
    private OSS oss;


    @Override
    public Result pictureUpload(MultipartFile file) throws GlobalException {
        return ResultUtil.success(upload(file));
    }

    /**
     * 上传文件---去除URL中的？后的时间戳
     *
     * @param file 文件
     * @return 文件的访问地址
     */
    public String upload(MultipartFile file) throws GlobalException {
        createBucket(uploadConfig.getBucketName());
        String fileName = uploadFile(file, uploadConfig.getBucketName(), uploadConfig.getDir());
        String fileOssUrl = getImgUrl(fileName, uploadConfig.getBucketName(), uploadConfig.getDir());
        int firstChar = fileOssUrl.indexOf("?");
        if (firstChar > 0) {
            fileOssUrl = fileOssUrl.substring(0, firstChar);
        }
        return fileOssUrl;
    }

    /**
     * 当Bucket不存在时创建Bucket
     */
    private void createBucket(String bucketName) {
        try {
            if (!oss.doesBucketExist(bucketName)) {
                oss.createBucket(bucketName);
            }
        } catch (Exception e) {
            log.error("{}", "创建Bucket失败,请核对Bucket名称(规则：只能包含小写字母、数字和短横线，必须以小写字母和数字开头和结尾，长度在3-63之间)");
        }
    }

    /**
     * 上传到OSS服务器 如果同名文件会覆盖服务器上的
     *
     * @param file       文件
     * @param bucketName 桶名
     * @param dir        文件夹
     * @return 文件的访问地址
     */
    private String uploadFile(MultipartFile file, String bucketName, String dir) throws GlobalException {
        String fileName =
                String.format(
                        "%s.%s",
                        UUID.randomUUID().toString(), FilenameUtils.getExtension(file.getOriginalFilename()));
        try (InputStream inputStream = file.getInputStream()) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getContentType(FilenameUtils.getExtension("." + file.getOriginalFilename())));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            PutObjectResult putResult = oss.putObject(bucketName, dir + fileName, inputStream, objectMetadata);
        } catch (Exception e) {
            log.error("{}", "上传文件失败");
            throw new GlobalException(ResultCode.ERROR.getCode(),e.getMessage());
        }
        return fileName;
    }

    /**
     * 获得文件路径
     *
     * @param fileUrl    文件的URL
     * @param bucketName 桶名
     * @param dir        文件夹
     * @return 文件的路径
     */
    private String getImgUrl(String fileUrl, String bucketName, String dir) {
        if (StringUtils.isEmpty(fileUrl)) {
            log.error("{}", "文件地址为空");
            throw new RuntimeException("文件地址为空");
        }
        String[] split = fileUrl.split("/");

        URL url =
                oss.generatePresignedUrl(
                        bucketName, dir + split[split.length - 1]
                        , DateUtils.addDays(new Date(), 365 * 10));
        if (url == null) {
            log.error("{}", "获取oss文件URL失败");
        }
        return url.toString();
    }

    /**
     * 判断OSS服务文件上传时文件的contentType
     *
     * @param filenameExtension 文件后缀
     * @return 后缀
     */
    private String getContentType(String filenameExtension) {
        if (filenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (filenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (filenameExtension.equalsIgnoreCase("jpeg")
                || filenameExtension.equalsIgnoreCase("jpg")
                || filenameExtension.equalsIgnoreCase("png")) {
            return "image/jpg";
        }
        if (filenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (filenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (filenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (filenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }

}
