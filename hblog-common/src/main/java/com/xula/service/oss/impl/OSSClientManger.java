package com.xula.service.oss.impl;


import com.aliyun.oss.OSSClient;
import com.xula.base.constant.UploadConfig;


/**
 * oss manager
 * @author xla
 */
public class OSSClientManger {

    private static OSSClient ossClient;

    private static volatile OSSClientManger ossClientManger;

    /**
     * 
    * 创建一个新的实例 OSSClientManger.
    * 初始化
     */
    private OSSClientManger (){
    	init();
    }
    
    /**
     * 初始化
     */
    public void init() {
       ossClient = new OSSClient(UploadConfig.endpoint, UploadConfig.accessKeyId, UploadConfig.accessKeySecret);
    }
   
    /**
     * 销毁
     */
    public void destory() {
      ossClient.shutdown();
    }
    
    /**
     * 
    * @Title: getOSSClient
    * @Description: TODO(获取一个实例)
    * @param @return    参数
    * @return OSSClient    返回类型
    * @throws
     */
    public OSSClient getOSSClient(){
    	return ossClient;
    }



    public static OSSClientManger getInstance(){
        if (ossClientManger == null) {
             synchronized (OSSClientManger.class) {
                 if (ossClientManger == null) {
                     ossClientManger = new OSSClientManger();
                 }
             }
        }
    	return ossClientManger;
    }
}
