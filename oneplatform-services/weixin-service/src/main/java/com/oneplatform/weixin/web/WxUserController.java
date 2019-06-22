package com.oneplatform.weixin.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeesuite.common.JeesuiteBaseException;
import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.security.model.BaseUserInfo;
import com.jeesuite.springweb.model.WrapperResponse;
import com.oneplatform.base.model.IdParam;
import com.oneplatform.weixin.component.WeixinAppManager;
import com.oneplatform.weixin.dto.param.WxappLoginParam;
import com.oneplatform.weixin.service.WinxinUserSerivce;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;

/**
 * 微信小程序用户接口
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RestController
@RequestMapping("/weixin/user")
public class WxUserController {
	
    private final Logger logger = LoggerFactory.getLogger("com.oneplatform.weixin.web");

    private @Autowired WeixinAppManager weixinAppManager;
    private @Autowired WinxinUserSerivce winxinUserSerivce;
    

    @PostMapping("/code2userid")
    public WrapperResponse<IdParam> codeToUser(HttpServletRequest request,@RequestBody WxappLoginParam param) {
        if (StringUtils.isBlank(param.getGroup())) {
            throw new JeesuiteBaseException(4001, "参数group必填");
        }
        Integer userId = winxinUserSerivce.findUserIdByWeAppCode(param.getGroup(), param.getCode());
        return new WrapperResponse<>(new IdParam(userId));
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    @GetMapping("/info")
    public String info(@PathVariable String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        final WxMaService wxService = weixinAppManager.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wxService.getUserService().getUserInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/phone")
    public String phone(@PathVariable String appid, String sessionKey, String signature,
                        String rawData, String encryptedData, String iv) {
        final WxMaService wxService = weixinAppManager.getMaService(appid);

        // 用户信息校验
        if (!wxService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            return "user check failed";
        }

        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = wxService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

        return JsonUtils.toJson(phoneNoInfo);
    }

}
