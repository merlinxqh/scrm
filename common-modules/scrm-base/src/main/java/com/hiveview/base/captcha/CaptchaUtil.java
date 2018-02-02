package com.hiveview.base.captcha;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.Random;


public class CaptchaUtil {
    //获取随机颜色
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 随机生成验证码字符
     *
     * @return 验证码字符
     */
    public static char[] generateCaptcha() {
        Random random = new Random();
        //输出随机的验证文字
        int itmp = 0;
        char[] nums = new char[5];
        for (int i = 0; i < 5; i++) {
            if (random.nextInt(2) == 1) {
                itmp = random.nextInt(26) + 65;    //生成A~Z的字母
            } else {
                itmp = random.nextInt(10) + 48;    //生成0~9的数字
            }
            nums[i] = (char) itmp;
        }
        return nums;
    }

    /**
     * 根据字符绘制验证码图片
     *
     * @param captchaChars
     * @return
     */
    public static BufferedImage generateImage(char[] captchaChars) {
        int width = 98;
        int height = 32;
        // 创建BufferedImage类的对象
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        //  创建Graphics类的对象
        Graphics g = image.getGraphics();
        // 通过Font构造字体
        //改变图形的当前颜色为随机生成的颜色
        g.setColor(getRandColor(200, 250));
        // 绘制一个填色矩形
        g.fillRect(0, 0, width, height);
        Font mFont = new Font("微软黑体", Font.BOLD, 20);
        g.setFont(mFont);
        Random random = new Random();
        // 通过Graphics类的对象创建一个Graphics2D类的对象
        Graphics2D g2d = (Graphics2D) g;
        /*
         随机干扰线
        */
        g.setColor(getRandColor(180, 200));
        for (int i = 0; i < 130; i++) {
            int x = random.nextInt(width - 1);
            int y = random.nextInt(height - 1);
            int x1 = random.nextInt(6) + 1;
            int y1 = random.nextInt(12) + 1;
            // 创建一个供画笔选择线条粗细的对象
            BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
            Line2D line = new Line2D.Double(x, y, x + x1, y + y1);
            g2d.setStroke(bs);
            g2d.draw(line);
        }
        for (int i = 0; i < 5; i++) {
            Color color = new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110));
            g.setColor(color);
            /****随机缩放文字并将文字旋转指定角度**/
            //将文字旋转指定角度
            Graphics2D g2d_word = (Graphics2D) g;
            AffineTransform trans = new AffineTransform();
            trans.rotate(random.nextInt(45) * 3.14 / 180, 15 * i + 10, 6);
            //缩放文字
            float scaleSize = random.nextFloat() + 0.5f;
            if (scaleSize < 0.8 || scaleSize > 1.1f) scaleSize = 1f;
            trans.scale(scaleSize, scaleSize);
            g2d_word.setTransform(trans);
            g.drawString(String.valueOf(captchaChars[i]), 15 * i + 10, 14);
        }
        g.dispose();
        return image;
    }

}
