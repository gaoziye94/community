package life.zwp.community.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.BucketAuthorization;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileBucketLocalAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import cn.ucloud.ufile.http.OnProgressListener;
import life.zwp.community.exception.CustomizeErrorCode;
import life.zwp.community.exception.CustomizeException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * ucloud 文件上传
 */
@Service
public class UCloudProvider {

    @Value("${ucloud.ufile.public_key}")
    private String publicKey;
    @Value("${ucloud.ufile.private_key}")
    private String privateKey;
    @Value("${ucloud.ufile.bucket}")
    private String bucket;
    @Value("${ucloud.ufile.region}")
    private String region;
    @Value("${ucloud.ufile.proxy_suffix}")
    private String proxySuffix;
    @Value("${ucloud.ufile.expires_duration}")
    private Integer expiresDuration;


    /**
     * 上传图片到云服务器
     * @param fileInputStream 文件流
     * @param mimeType 文件类型
     * @param fileName 文件名
     * @return
     */
    public String upload(InputStream fileInputStream, String mimeType, String fileName){
        String generatedFileName = null;
        try {
            // Bucket相关API的授权器
            ObjectAuthorization objectAuthorization = new UfileObjectLocalAuthorization(publicKey, privateKey);
            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig(region, proxySuffix);

            String[] fileNameSplit =StringUtils.split(fileName,".");
            if(fileNameSplit.length >1){
                generatedFileName = UUID.randomUUID().toString()+"."+  fileNameSplit[fileNameSplit.length-1];
            } else {
                //图片上传失败，抛出异常
                throw new CustomizeException(CustomizeErrorCode.File_UPLOAD_FAIL);
            }
            PutObjectResultBean response = UfileClient.object(objectAuthorization, config)
                    .putObject(fileInputStream, mimeType)
                    .nameAs(generatedFileName)
                    .toBucket(bucket)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten, contentLength) -> {

                    })
                    .execute();
                    if(response !=null && response.getRetCode() == 0){
                        //图片上传成功，返回图片url
                        String url = UfileClient.object(objectAuthorization, config)
                                .getDownloadUrlFromPrivateBucket(generatedFileName, bucket, expiresDuration)
                                .createUrl();
                        return url;
                    } else {
                        //图片上传失败，抛出异常
                        throw new CustomizeException(CustomizeErrorCode.File_UPLOAD_FAIL);
                    }
        } catch (UfileClientException e) {
            //图片上传失败，抛出异常
            throw new CustomizeException(CustomizeErrorCode.File_UPLOAD_FAIL);
        } catch (UfileServerException e) {
            //图片上传失败，抛出异常
            throw new CustomizeException(CustomizeErrorCode.File_UPLOAD_FAIL);
        }
    }

}
