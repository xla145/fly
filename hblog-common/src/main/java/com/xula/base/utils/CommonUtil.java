package com.xula.base.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 公共方法
 * @author xla
 */
public class CommonUtil {

    public static String getRandomNumber() {
        Random rand = new Random();
        char[] letters = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
                'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'r', '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9'};
        String str = "";
        int index;
        boolean[] flags = new boolean[letters.length];// 默认为false
        for (int i = 0; i < 5; i++) {
            do {
                index = rand.nextInt(letters.length);
            } while (flags[index] == true);
            char c = letters[index];
            str += c;
            flags[index] = true;
        }
        return str;
    }

    /**
     * sql like时防止注入
     *
     * @param srcStr
     * @return
     */
    public static String queryLikeLeft(String srcStr) {
        //适用于mysql
        srcStr = StringUtils.replace(srcStr, "\\", "\\\\");
        srcStr = StringUtils.replace(srcStr, "'", "\\'");
        srcStr = StringUtils.replace(srcStr, "_", "\\_");
        srcStr = StringUtils.replace(srcStr, "%", "\\%");
        return "%" + srcStr;
    }

    /**
     * sql like时防止注入
     *
     * @param srcStr
     * @return
     */
    public static String queryLikeRight(String srcStr) {
        //适用于mysql
        srcStr = StringUtils.replace(srcStr, "\\", "\\\\");
        srcStr = StringUtils.replace(srcStr, "'", "\\'");
        srcStr = StringUtils.replace(srcStr, "_", "\\_");
        srcStr = StringUtils.replace(srcStr, "%", "\\%");
        return srcStr + "%";
    }

    /**
     * sql like时防止注入
     *
     * @param srcStr
     * @return
     */
    public static String queryLike(String srcStr) {
        //适用于mysql
        srcStr = StringUtils.replace(srcStr, "\\", "\\\\");
        srcStr = StringUtils.replace(srcStr, "'", "\\'");
        srcStr = StringUtils.replace(srcStr, "_", "\\_");
        srcStr = StringUtils.replace(srcStr, "%", "\\%");
        return "%" + srcStr + "%";
    }

    /**
     * 将数组转换成sql的in查询字符串
     *
     * @return '1','2',''		没有数据时返回''
     */
    public static String arrayToSqlIn(Object[] array) {
        StringBuffer sqlIn = new StringBuffer("'");
        for (int i = 0; i < array.length; i++) {
            sqlIn.append(array[i] + "','");
        }
        sqlIn.append("'");
        return sqlIn.toString();
    }

    /**
     * 将时间数组，转时间戳数组，然后返回以，分割的字符串
     *
     * @return '444444','444444' 没有数据时返回''
     */
    public static String arrayTimeToString(String[] times) {
        times = delEmptyArrays(times);
        StringBuffer stamp = new StringBuffer("");
        for (int i = 0; i < times.length; i++) {
            long time = DateUtil.stringToDate(times[i], "yyyy-MM-dd").getTime();//获取时间戳 转为秒
            stamp.append(time + ",");//获取时间
        }
        return stamp.deleteCharAt(stamp.length() - 1).toString();
    }


    /**
     * 将时间数组，转时间戳数组，然后返回以，分割的字符串
     *
     * @return '444444','444444' 没有数据时返回''
     */
    public static String arrayTimeToString(String times) {
        if (times == null) {
            return "";
        }
        String[] array = times.split(",");
        StringBuffer stamp = new StringBuffer("");
        for (int i = 0; i < array.length; i++) {
            long time = DateUtil.stringToDate(array[i], "yyyy-MM-dd").getTime();//获取时间戳 转为秒
            stamp.append(time + ",");//获取时间
        }
        return stamp.deleteCharAt(stamp.length() - 1).toString();
    }

    /**
     * 将时间戳数组，转日期数组，然后返回以，分割的字符串
     *
     * @return '444444','444444' 没有数据时返回''
     */
    public static String arrayStampToString(String stamp) {
        if (StringUtils.isBlank(stamp)) {
            return "";
        }
        String[] array = stamp.split(",");
        StringBuffer times = new StringBuffer("");
        for (int i = 0; i < array.length; i++) {
            String time = DateUtil.stampToDate(array[i], "yyyy-MM-dd");//获取时间戳 转为秒
            times.append(time + ",");//获取时间
        }
        return times.deleteCharAt(times.length() - 1).toString();
    }

    /**
     * 生成id
     *
     * @param code 业务码 会加在id最前面
     * @return
     */
    public static String getId(String code) {
        String extra = getStringRandom(6);
        String time = getNumberRandom(6);
        return code + "-" + time + "-" + extra;
    }

    /**
     * 生成随机数字和字母
     *
     * @param length
     * @return
     */
    public static String getStringRandom(int length) {
        String base = UUID.randomUUID().toString().replaceAll("-", "");
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }


    /**
     * 随机生成数字字符串
     *
     * @param length
     * @return
     */
    public static String getNumberRandom(int length) {
        char[] chars = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(chars[new Random().nextInt(chars.length)]);
        }
        return sb.toString();
    }

    /**
     * 随机生成字符串
     *
     * @param length
     * @return
     */
    public static String getCharsRandom(int length) {
        char[] chars = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(chars[new Random().nextInt(chars.length)]);
        }
        return sb.toString();
    }



    /**
     * 判断是否为手机号
     *
     * @param number
     * @return
     */
    public static boolean isPhoneNumber(String number) {
        String regExp = "^(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(number);
        return m.find();//boolean
    }


    /**
     * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
     * 例如：HelloWorld->HELLO_WORLD
     *
     * @param name 转换前的驼峰式命名的字符串
     * @return 转换后下划线大写方式命名的字符串
     */
    public static String underscoreName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            // 将第一个字符处理成大写
            result.append(name.substring(0, 1).toLowerCase());
            // 循环处理其余字符
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                // 在大写字母前添加下划线
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                // 其他字符直接转成大写
                result.append(s.toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
     * 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String camelName(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 判断是否为图片
     *
     * @param contentType
     * @return
     */
    public static boolean isImg(String contentType) {
        MimetypesFileTypeMap mtftp = new MimetypesFileTypeMap();
        mtftp.addMimeTypes("image png tif jpg jpeg bmp");
        String type = contentType.split("/")[0];
        return "image".equals(type);
    }

    /**
     * 删除数组中空字符串
     *
     * @param ts
     */
    public static String[] delEmptyArrays(String[] ts) {
        List<String> list = new ArrayList<String>();
        if (ts == null) {
            return list.toArray(new String[]{});
        }
        for (String t : ts) {
            if (StringUtils.isNotBlank(t) && StringUtils.isNotEmpty(t)) {
                list.add(t);
            }
        }
        return list.toArray(new String[]{});
    }

    /**
     * 判断数组中是否存在相同的值
     *
     * @param ts
     */
    public static boolean isExisRepeatItem(Object[] ts) {
        Set<Object> objectSet = new HashSet<Object>();
        if (ts == null) {
            return false;
        }
        for (Object t : ts) {
            objectSet.add(t);
        }
        return objectSet.size() != ts.length;
    }


    /**
     * 去除数组相同的值
     *
     * @param items
     * @return
     */
    public static String[] removeSameItem(String[] items) {
        Set<Object> objectSet = new HashSet<Object>();
        if (items == null) {
            return null;
        }
        for (String t : items) {
            if (!objectSet.contains(t) && StringUtils.isNotBlank(t) && StringUtils.isNotEmpty(t)) {
                objectSet.add(t);
            }
        }
        return objectSet.toArray(new String[]{});
    }


    /**
     * 删除数组中某字符
     *
     * @param ts
     */
    public static Integer[] delItemIntegerArrays(Integer[] ts, Integer[] items) {
        List<Integer> list = new ArrayList<Integer>();
        if (ts == null) {
            return list.toArray(new Integer[]{});
        }
        for (Integer t : ts) {
            for (Integer item : items) {
                if (!t.equals(item)) {
                    list.add(t);
                }
            }
        }
        return list.toArray(new Integer[]{});
    }

    /**
     * 删除数组中某字符
     *
     * @param ts
     */
    public static String[] delItemArrays(String[] ts, String[] items) {
        List<String> list = new ArrayList<String>();
        if (ts == null) {
            return list.toArray(new String[]{});
        }
        for (String t : ts) {
            for (String item : items) {
                if (!t.equalsIgnoreCase(item)) {
                    list.add(t);
                }
            }
        }
        return list.toArray(new String[]{});
    }


    /**
     * 删除数组中某字符
     *
     * @param ts
     */
    public static String[] delItemArrays(String[] ts, String content) {
        List<String> list = new ArrayList<String>();
        if (ts == null) {
            return list.toArray(new String[]{});
        }
        for (String t : ts) {
            if (!t.equalsIgnoreCase(content)) {
                list.add(t);
            }
        }
        return list.toArray(new String[]{});
    }


    /**
     * 获取 linux 和 window 下不同的路径
     * @return
     */
    public static String getRealPath(String path) {
        String line = File.separator;
        //windows下
        if("\\".equals(line)){
            path = path.replace("/", "\\");  // 将/换成\\
        }
        //linux下
        if("/".equals(line)){
            path = path.replace("\\", "/");
        }
        return path;
    }


    /**
     * 数组转化，字符串数组转整型集合
     * @return
     */
    public static List<Integer> arraysToList(String[] arrays) {
        List<Integer> integerList = new ArrayList<Integer>(arrays.length);
        for (String a:arrays) {
            integerList.add(Integer.valueOf(a));
        }
        return integerList;
    }

    /**
     * MD5加密
     * @param content
     * @return
     */
    public static String md5(String content) {
        return DigestUtils.md5Hex(content).toUpperCase();
    }

    /**
     * 获取集合的长度
     * @return
     */
    public static Integer getSize(List<Object> objects) {
        if (objects == null) {
            return 0;
        }
        return objects.size();
    }
}
