package com.xula.base.utils;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImgUtil {


    private static final  String  IMG_URL = "img\\[([^\\s]+?)\\]";


    private static final  String  IMG_MATCHER = "(^img\\[)|(\\]$)";

    /**
     * 输入流转字节数组
     *
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] inputStreamToByteArray(InputStream input) throws IOException {
        byte[] byt = new byte[input.available()];
        input.read(byt);
        return byt;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getContentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase("bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase("gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase("jpeg") ||
                FilenameExtension.equalsIgnoreCase("jpg") ||
                FilenameExtension.equalsIgnoreCase("png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase("pdf")) {
            return "application/pdf";
        }
        if (FilenameExtension.equalsIgnoreCase("html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase("txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase("vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase("pptx") ||
                FilenameExtension.equalsIgnoreCase("ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase("docx") ||
                FilenameExtension.equalsIgnoreCase("doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase("xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase("xls") ||
                FilenameExtension.equalsIgnoreCase("xlsx")) {
            return "application/vnd.ms-excel";
        }
        return null;
    }

    /**
     * 获取文件的类型
     *
     * @param name
     * @return
     */
    public static String getFileType(String name) {
        return name.substring(name.lastIndexOf(".") + 1);
    }

    /**
     * 获取文件名字
     *
     * @param name
     * @return
     */
    public static String getFileName(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }


    /**
     * 内容正则替换 标签
     * @param content
     * @return
     */
    public static String replaceContent(String content) {
        // 如果上传图片 img[http://img.xulian.net.cn/test/img/593208709349.jpg] 转成 <img src="http://img.xulian.net.cn/test/img/593208709349.jpg">
        if (!containsImgLabel(content)) {
            return content;
        }
        Pattern p = Pattern.compile(IMG_URL);
        Pattern p_1 = Pattern.compile(IMG_MATCHER);
        Matcher m = p.matcher(content);
        StringBuffer sb = new StringBuffer();
        while(m.find()) {
            String s1 = m.group();
            m.appendReplacement(sb, "<img src='" + p_1.matcher(s1).replaceAll("") + "'/>");
        }
        return sb.toString();
    }


    public static boolean containsImgLabel(String content) {
        Pattern p = Pattern.compile(IMG_URL);
        return p.matcher(content).find();
    }

    public static void main(String[] args) {
        System.out.println(replaceContent("tttttttttttttttttttimg[http://img.xulian.net.cn/test/img/593208709349.jpg]"));
    }
}
