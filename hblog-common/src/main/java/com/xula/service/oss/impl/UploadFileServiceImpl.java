package com.xula.service.oss.impl;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.xula.base.constant.FileCategory;
import com.xula.base.constant.ImgCategory;
import com.xula.base.constant.UploadConfig;
import com.xula.base.utils.CommonUtil;
import com.xula.base.utils.ImgUtil;
import com.xula.service.oss.IUploadFileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * 上传文件内容
 * @author xla
 */
@Service("IUploadFileService")
public class UploadFileServiceImpl implements IUploadFileService {

	private static Logger logger = LoggerFactory.getLogger(UploadFileServiceImpl.class);
	private static OSSClient ossClient = null;

	/**
     * 上传图片
     * 
     * @param imgByte		图片文件字节数组
     * @param ic			使用范畴
     * 	
     * @return 成功上传url
     */
	@Override
    public String uploadImg(byte[] imgByte, ImgCategory ic) {
		return uploadImg(imgByte, null, ic);
	}

	/**
	 * 上传图片
	 * 
	 * @param imgByte		图片文件字节数组
	 * @param fileName		文件名， 不带后缀(用作覆盖图片用户)
	 * @param ic			使用范畴
	 * 
	 * @return 成功上传url
	 */
	@Override
	public String uploadImg(byte[] imgByte, String fileName, ImgCategory ic) {
		if (imgByte == null) {
			return null;
		}
		// 并发时生成文件名会有问题
		fileName = (StringUtils.isBlank(fileName) ? CommonUtil.getNumberRandom(12) : fileName) + ".jpg";
		String fileDir = ic.getDir();

		return uploadContent(imgByte, fileName, fileDir);
	}
	
	/**
	* 上传文件
	* 
	* @param fileByte		文件字节数组
	* @param fileType		文件类型（后缀）
	* @param fc				使用范畴
	* 	
	* @return 成功上传url
	*/
	@Override
	public String uploadFile(byte[] fileByte, String fileType, FileCategory fc) {
		String fileName = CommonUtil.getNumberRandom(12) + "." + fileType;
		return uploadContent(fileByte, fileName, fc.getDir());
	}

	/**
	 * 上传文件
	 *
	 * @param fileByte		文件字节数组
	 * @param fileType		文件类型（后缀）
	 * @param fileName 			上传的名字
	 * @param fc				使用范畴
	 *
	 * @return 成功上传url
	 */
	@Override
	public String uploadFile(byte[] fileByte, String fileType, String fileName, FileCategory fc) {
		return uploadContent(fileByte, fileName, fc.getDir());
	}

	/**
	 * 上传文件内容
	 * */
	private String uploadContent(byte[] file, String fileName, String filedir) {
		try {
			ossClient = OSSClientManger.getInstance().getOSSClient();
			InputStream inputStream = new ByteArrayInputStream(file);
			return uploadFile2OSS(inputStream, fileName, filedir);
		} catch (Exception e) {
			logger.error("文件上传失败！", e);
		}
		return null;
	}

	/**
	 * 上传到OSS服务器 如果同名文件会覆盖服务器上的
	 *
	 * @param inputStream		文件流
	 * @param fileName		文件名称 包括后缀名
	 * @return
	 */
	private String uploadFile2OSS(InputStream inputStream, String fileName, String fileDir) {
		try {
			String filePath = fileDir + fileName;
			String contentType = ImgUtil.getContentType(fileName.substring(fileName.lastIndexOf(".") + 1));
			if (StringUtils.isBlank(contentType)) {
				return null;
			}
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(inputStream.available());
			objectMetadata.setCacheControl("max-age=2592000");
			objectMetadata.setContentType(contentType);
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// 上传文件
			PutObjectResult putResult = ossClient.putObject(UploadConfig.bucketName, filePath, inputStream, objectMetadata);
			String ret = putResult.getETag();
			if (StringUtils.isNotBlank(ret)) {
				return  UploadConfig.HTTP+"://"+UploadConfig.endpoint + "/" + filePath;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 下载文件内容
	 * */
	@Override
	public void downloadContent(String name) {
		ossClient = OSSClientManger.getInstance().getOSSClient();
		OSSObject ossObject = ossClient.getObject(UploadConfig.bucketName, name);
		InputStream content = ossObject.getObjectContent();
		if (content != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(content));
			try {
				while (true) {
					String line = reader.readLine();
					if (line == null) {
						break;
					}
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			} finally {
				try {
					if (content != null) {
						content.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
