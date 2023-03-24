package com.archine.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.archine.domain.ResponseResult;
import com.archine.enums.AppHttpCodeEnum;
import com.archine.exception.SystemException;
import com.archine.service.UploadService;
import com.archine.utils.PathUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssUploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        //判断文件类型
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originalFilename.endsWith(".png")&&!originalFilename.endsWith(".jpg")&&!originalFilename.endsWith(".jpeg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //如果判断通过上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);
        String url = uploadOss(img,filePath);//  2099/2/3/wqeqeqe.png
        return ResponseResult.okResult(url);
    }

    private  String accessKeyId;
    private  String accessKeySecret;
    private  String bucketName;
    private  String endpoint;

    private String uploadOss(MultipartFile imgFile, String filePath) {


        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 文件存储入OSS，Object的名称为fileKey。详细请参看“SDK手册 > Java-SDK > 上传文件”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/upload_object.html?spm=5176.docoss/user_guide/upload_object
            String fileKey = filePath;
            ossClient.putObject(bucketName, fileKey, imgFile.getInputStream());
            System.out.println("Object：" + fileKey + "存入OSS成功。");
            return "https://dxq-images.oss-cn-shanghai.aliyuncs.com/"+fileKey;

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return "上传失败";
    }
}

