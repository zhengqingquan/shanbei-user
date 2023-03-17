package com.github.shanbei.shanbeiuser.controller.common;

import com.github.shanbei.shanbeiuser.constant.CacheConstants;
import com.github.shanbei.shanbeiuser.constant.Constants;
import com.github.shanbei.shanbeiuser.constant.SessionAttribute;
import com.github.shanbei.shanbeiuser.model.domain.redis.RedisCache;
import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author zhengqingquan
 */
@Slf4j
@RestController
public class CaptchaController {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private RedisCache redisCache;

    // @Autowired
    // private ISysConfigService configService;

    /**
     * 生成验证码
     * HttpServletResponse对象代表着HTTP响应，控制器方法可以使用它来设置响应头和响应体等信息，最终将响应发送给客户端。
     * HttpSession对象代表着当前用户的会话，它允许控制器方法在不同的HTTP请求之间存储和获取数据。
     */
    @GetMapping("/captchaImage")
    public void getCode(HttpServletResponse response, HttpSession session) {
        // 使用前缀+uuid的方式生成用于Redis缓存的验证码的key。
        String captchaKey = CacheConstants.CAPTCHA_CODE_KEY + UUID.randomUUID();
        // 生成验证码
        String captchaValue = captchaProducer.createText();
        // 生成验证码图片
        BufferedImage captchaImage = captchaProducer.createImage(captchaValue);
        // 将验证码放入Redis并设置过期时间。
        redisCache.setCacheObject(captchaKey, captchaValue, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 将验证码存入session
        session.setAttribute(SessionAttribute.captcha, captchaValue);
        // 是设置HTTP响应的Content-Type头字段的值为"image/png"，告诉浏览器客户端接收到的数据是一张PNG格式的图片。
        response.setContentType("image/png");
        try {
            // 通过response对象获取一个ServletOutputStream输出流对象，用于将验证码图片数据写入响应体中。
            ServletOutputStream outputStream = response.getOutputStream();
            // 使用ImageIO.write()方法将生成的验证码图片数据以PNG格式写入到输出流中。
            ImageIO.write(captchaImage, "png", outputStream);
        } catch (Exception e) {
            log.error("验证码获取失败:" + e.getMessage());
        }
    }
}
