package com.xula.base.utils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;import java.util.HashMap;
import java.util.Map;import java.util.Random;

/**
 * Description: 验证码生成器
 * Date: 2017/8/29
 * @author xla
 */
public class Captcha {

    private static final Random random = new Random(System.nanoTime());

    /**
     * 默认的验证码大小
     */
    private static final int WIDTH = 108, HEIGHT = 38;

    /**
     * 验证码随机字符数组
     */
    private static final char[] charArray = "3456789ABCDEFGHJKMNPQRSTUVWXY".toCharArray();

    private static Captcha captcha = new Captcha();

    public static Captcha getInstance() {
        return captcha;
    }

    /**
     * 验证码字体
     */
    protected static final Font[] RANDOM_FONT = new Font[] {
            new Font(Font.DIALOG, Font.BOLD, 33),
            new Font(Font.DIALOG_INPUT, Font.BOLD, 34),
            new Font(Font.SERIF, Font.BOLD, 33),
            new Font(Font.SANS_SERIF, Font.BOLD, 34),
            new Font(Font.MONOSPACED, Font.BOLD, 34)
    };


    /**
     * 验证码 存放 1：存放在session中
     *
     *
     *
     *
     *
     */

    /**
     * 生成验证码
     */
    public void render(HttpServletResponse response,String vcode) {
        response.setHeader("Pragma","no-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream sos = null;
        try {
            BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
            drawGraphic(vcode, image);
            sos = response.getOutputStream();
            ImageIO.write(image, "jpeg", sos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (sos != null) {
                try {sos.close();} catch (IOException e) {

                }
            }
        }
    }

    /**
     * 获取验证码的值
     * @return
     */
    public String getRandomString() {
        char[] randomChars = new char[4];
        for (int i = 0; i < randomChars.length; i++) {
            randomChars[i] = charArray[random.nextInt(charArray.length)];
        }
        return String.valueOf(randomChars);
    }

    /**
     * 构造验证码
     * @param randomString
     * @param image
     */
    private void drawGraphic(String randomString, BufferedImage image) {

        /**
         * 获取图形上下文
         */
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        // 图形抗锯齿
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 字体抗锯齿
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 设定背景色
        g.setColor(getRandColor(210, 250));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        //绘制小字符背景
        Color color = null;
        for(int i = 0; i < 20; i++){
            color = getRandColor(120, 200);
            g.setColor(color);
            String rand = String.valueOf(charArray[random.nextInt(charArray.length)]);
            g.drawString(rand, random.nextInt(WIDTH), random.nextInt(HEIGHT));
            color = null;
        }

        //设定字体
        g.setFont(RANDOM_FONT[random.nextInt(RANDOM_FONT.length)]);
        // 绘制验证码
        for (int i = 0; i < randomString.length(); i++){
            //旋转度数 最好小于45度
            int degree = random.nextInt(28);
            if (i % 2 == 0) {
                degree = degree * (-1);
            }
            //定义坐标
            int x = 22 * i, y = 21;
            //旋转区域
            g.rotate(Math.toRadians(degree), x, y);
            //设定字体颜色
            color = getRandColor(20, 130);
            g.setColor(color);
            //将认证码显示到图象中
            g.drawString(String.valueOf(randomString.charAt(i)), x + 8, y + 10);
            //旋转之后，必须旋转回来
            g.rotate(-Math.toRadians(degree), x, y);
        }
        //图片中间曲线，使用上面缓存的color
        g.setColor(color);
        //width是线宽,float型
        BasicStroke bs = new BasicStroke(3);
        g.setStroke(bs);
        //画出曲线
        QuadCurve2D.Double curve = new QuadCurve2D.Double(0d, random.nextInt(HEIGHT - 8) + 4, WIDTH / 2, HEIGHT / 2, WIDTH, random.nextInt(HEIGHT - 8) + 4);
        g.draw(curve);
        // 销毁图像
        g.dispose();
    }

    /**
     * 给定范围获得随机颜色
     * @param fc
     * @param bc
     * @return
     */
    protected Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}