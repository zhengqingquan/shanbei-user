package com.github.shanbei.shanbeiuser.controller.common;

import com.google.code.kaptcha.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 * 
 * @author zhengqingquan
 */
@Slf4j
@RestController
public class CaptchaController
{
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    // @Autowired
    // private RedisCache redisCache;
    //
    // @Autowired
    // private ISysConfigService configService;
    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public void getCode(HttpServletResponse response, HttpSession session)
    {
        //生成验证码
        String text = captchaProducer.createText();
        //生成图片
        BufferedImage image = captchaProducer.createImage(text);
        //将验证码存入session
        session.setAttribute("kaptcha",text);
        //将图片输出给浏览器
        response.setContentType("image/png");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            ImageIO.write(image,"png",outputStream);
        }catch (Exception e){
            log.error("验证码获取失败:"+e.getMessage());
        }
    }

    public void text() {
        String text = captchaProducer.createText();
        System.out.println(text);
    }

    public static void main(String[] args) {
        new CaptchaController().text();
    }
}
