package com.xula.service.oss;


import com.xula.base.constant.FileCategory;
import com.xula.base.constant.ImgCategory;

/**
 * 上传图片
 * @author xla
 */
public interface IUploadFileService {

    /**
     * 下载文件
     * @param name
     */
     void downloadContent(String name);

    /**
     * 上传图片
     * 
     * @param imgByte		图片文件字节数组
     * @param ic			使用范畴
     * 	
     * @return 成功上传url
     */
     String uploadImg(byte[] imgByte, ImgCategory ic);

    /**
     * 上传图片
     *
     * @param imgByte		图片文件字节数组
     * @param fileName		文件名， 不带后缀(用作覆盖图片用户)
     * @param ic			使用范畴
     *
     * @return 成功上传url
     */
     String uploadImg(byte[] imgByte, String fileName, ImgCategory ic);

    /**
    * 上传文件
    * 
    * @param fileByte		文件字节数组
    * @param fileType		文件类型（后缀）
    * @param fc				使用范畴
    * 	
    * @return 成功上传url
    */
    String uploadFile(byte[] fileByte, String fileType, FileCategory fc);


    /**
     * 上传文件
     *
     * @param fileByte		文件字节数组
     * @param fileType		文件类型（后缀）
     * @param fc				使用范畴
     *
     * @return 成功上传url
     */
     String uploadFile(byte[] fileByte, String fileType, String fileName, FileCategory fc);

}
