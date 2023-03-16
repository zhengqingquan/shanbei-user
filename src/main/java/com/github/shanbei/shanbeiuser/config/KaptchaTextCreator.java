package com.github.shanbei.shanbeiuser.config;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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


    /**
     * 1. 选取两个随机值用于验证码的运算。
     * 2. 选取某个随机值代表某种运算（加减乘除）
     * 3. 根据运算拼接字符串后返回字符串。
     *
     * @return 验证码字符串
     */
    @Override
    public String getText() {
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);

        // 首先，Math.random()方法返回一个0到1之间的随机小数。
        // 乘以2可以将其扩展到0到2之间的范围。然后，Math.round()方法将其四舍五入为最接近的整数。
        // 最后，类型转换运算符（int）将结果转换为整数类型。
        // int randomoperands = (int) Math.round(Math.random() * 2);

        // 使用Java中的Random类生成随机数。nextInt(3)方法将返回一个在0到2之间（包括0和2）的随机整数。
        // int randomoperands = new Random().nextInt(3);

        // 使用Java中的ThreadLocalRandom类生成随机数。
        // nextInt(0, 3)方法将返回一个在0到2之间（包括0和2）的随机整数。
        // 高并发和多线程环境下，使用ThreadLocalRandom类生成随机数。
        // 因为Random类是线程不安全的，多个线程同时使用同一个Random对象生成随机数时，会出现竞争条件。
        // 导致生成的随机数可能不是真正的随机数，而是伪随机数。这会导致程序出现问题，甚至崩溃。
        // 相比之下，ThreadLocalRandom类是线程安全的，每个线程都有自己的种子状态，因此不会发生竞争条件，可以保证生成真正的随机数。
        // 此外，ThreadLocalRandom类的性能也比Random类更好，在多线程环境下更适合使用。
        int randomoperands = ThreadLocalRandom.current().nextInt(0, 3);

        int result = 0;
        StringBuilder suChinese = new StringBuilder();
        switch (randomoperands) {
            case 0:
                // 乘法
                result = x * y;
                suChinese.append(CNUMBERS[x]);
                suChinese.append("*");
                suChinese.append(CNUMBERS[y]);
                break;
            case 1:
                // 如果x不为0，且可以被整除，则为除法，否则为加法。
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
                break;
            case 2:
                // 减法
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
                break;
            default:
                // 默认为加法。
                result = x + y;
                suChinese.append(CNUMBERS[x]);
                suChinese.append("+");
                suChinese.append(CNUMBERS[y]);
                break;
        }

        suChinese.append("=?@").append(result);
        return suChinese.toString();
    }
}