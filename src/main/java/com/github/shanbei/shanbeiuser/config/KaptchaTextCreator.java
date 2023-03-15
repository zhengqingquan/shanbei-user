package com.github.shanbei.shanbeiuser.config;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 验证码文本生成器
 *
 * @author zhengqingquan
 */
public class KaptchaTextCreator extends DefaultTextCreator {

    /**
     * 有一下几种方法来创建数组。
     * 下面这些方法都在编译时候只执行一次，在运行时不会产生显著的性能差异。
     * 如果代码需要在运行时频繁使用这些数字，那么使用基本类型（如int）而不是字符串可能会更高效。
     * 因为基本类型可以比字符串更快速进行计算和比较。
     */
    private static final int[] CNUMBERS = IntStream.rangeClosed(0, 10).toArray();
    // private static final int[] CNUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    // private static final String[] CNUMBERS = "0,1,2,3,4,5,6,7,8,9,10".split(",");
    // private static final String[] CNUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    // private static final String[] CNUMBERS = IntStream.rangeClosed(0, 10).mapToObj(String::valueOf).toArray(String[]::new);


    @Override
    public String getText() {
        Integer result = 0;
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        StringBuilder suChinese = new StringBuilder();
        int randomoperands = (int) Math.round(Math.random() * 2);
        if (randomoperands == 0) {
            result = x * y;
            suChinese.append(CNUMBERS[x]);
            suChinese.append("*");
            suChinese.append(CNUMBERS[y]);
        } else if (randomoperands == 1) {
            if (!(x == 0) && y % x == 0) {
                result = y / x;
                suChinese.append(CNUMBERS[y]);
                suChinese.append("/");
                suChinese.append(CNUMBERS[x]);
            } else {
                result = x + y;
                suChinese.append(CNUMBERS[x]);
                suChinese.append("+");
                suChinese.append(CNUMBERS[y]);
            }
        } else if (randomoperands == 2) {
            if (x >= y) {
                result = x - y;
                suChinese.append(CNUMBERS[x]);
                suChinese.append("-");
                suChinese.append(CNUMBERS[y]);
            } else {
                result = y - x;
                suChinese.append(CNUMBERS[y]);
                suChinese.append("-");
                suChinese.append(CNUMBERS[x]);
            }
        } else {
            result = x + y;
            suChinese.append(CNUMBERS[x]);
            suChinese.append("+");
            suChinese.append(CNUMBERS[y]);
        }
        suChinese.append("=?@" + result);
        return suChinese.toString();
    }

    public static void main(String[] args) {
        System.out.println(new KaptchaTextCreator().getText());
    }
}