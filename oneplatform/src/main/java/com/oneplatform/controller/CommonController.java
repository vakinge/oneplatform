package com.oneplatform.controller;

import java.awt.Font;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jeesuite.security.SecurityDelegating;
import com.jeesuite.zuul.SessionCookieUtil;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

@Controller
@RequestMapping("/common")
public class CommonController {

    
    @RequestMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 5);
        // 设置字体
        specCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        String sessionId = SessionCookieUtil.getSessionId(request, response);
        SecurityDelegating.setTemporaryCacheValue(sessionId, specCaptcha.text(), 60);
        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }
}
