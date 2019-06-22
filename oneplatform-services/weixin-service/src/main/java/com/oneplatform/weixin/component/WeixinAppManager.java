package com.oneplatform.weixin.component;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.common.util.SimpleCryptUtils;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaKefuMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import cn.binarywang.wx.miniapp.message.WxMaMessageHandler;
import cn.binarywang.wx.miniapp.message.WxMaMessageRouter;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;

@Component
public class WeixinAppManager implements InitializingBean {

	private static Map<String, WxMaMessageRouter> routers = Maps.newHashMap();
	private static Map<String, WxMaService> maServices = Maps.newHashMap();

	private final WxMaMessageHandler templateMsgHandler = (wxMessage, context, service, sessionManager) -> service
			.getMsgService()
			.sendTemplateMsg(WxMaTemplateMessage.builder().templateId("此处更换为自己的模板id").formId("自己替换可用的formid")
					.data(Lists.newArrayList(new WxMaTemplateData("keyword1", "339208499", "#173177")))
					.toUser(wxMessage.getFromUser()).build());

	private final WxMaMessageHandler logHandler = (wxMessage, context, service, sessionManager) -> {
		System.out.println("收到消息：" + wxMessage.toString());
		service.getMsgService().sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("收到信息为：" + wxMessage.toJson())
				.toUser(wxMessage.getFromUser()).build());
	};

	private final WxMaMessageHandler textHandler = (wxMessage, context, service, sessionManager) -> service
			.getMsgService()
			.sendKefuMsg(WxMaKefuMessage.newTextBuilder().content("回复文本消息").toUser(wxMessage.getFromUser()).build());

	private final WxMaMessageHandler picHandler = (wxMessage, context, service, sessionManager) -> {
		try {
			WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", "png",
					ClassLoader.getSystemResourceAsStream("tmp.png"));
			service.getMsgService().sendKefuMsg(WxMaKefuMessage.newImageBuilder().mediaId(uploadResult.getMediaId())
					.toUser(wxMessage.getFromUser()).build());
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	};

	private final WxMaMessageHandler qrcodeHandler = (wxMessage, context, service, sessionManager) -> {
		try {
			final File file = service.getQrcodeService().createQrcode("123", 430);
			WxMediaUploadResult uploadResult = service.getMediaService().uploadMedia("image", file);
			service.getMsgService().sendKefuMsg(WxMaKefuMessage.newImageBuilder().mediaId(uploadResult.getMediaId())
					.toUser(wxMessage.getFromUser()).build());
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
	};
	
	 public Map<String, WxMaMessageRouter> getRouters() {
	        return routers;
	    }

	    public WxMaService getMaService(String appAlias) {
	        WxMaService wxService = maServices.get(appAlias);
	        if (wxService == null) {
	            throw new IllegalArgumentException(String.format("未找到对应appid=[%s]的配置，请核实！", appAlias));
	        }

	        return wxService;
	    }

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, WxMaInMemoryConfig> appConfigs = new HashMap<>();
		Properties properties = ResourceUtils.getAllProperties("weixin.weapp");
		properties.forEach((k, v) -> {
			String group = k.toString().split("\\[|\\]")[1];
			WxMaInMemoryConfig appConfig = appConfigs.get(group);
			if (appConfig == null) {
				appConfig = new WxMaInMemoryConfig();
				appConfigs.put(group, appConfig);
			}
			if (k.toString().endsWith("appKey")) {
				appConfig.setAppid(v.toString());
			} else if (k.toString().endsWith("appSecret")) {
				appConfig.setSecret(v.toString());
			}
		});

		appConfigs.forEach((k, v) -> {
			v.setSecret(SimpleCryptUtils.decrypt(v.getAppid(), v.getSecret()));
			WxMaService service = new WxMaServiceImpl();
			service.setWxMaConfig(v);
			routers.put(k, this.newRouter(service));
			maServices.put(k, service);
		});

	}
	
	private WxMaMessageRouter newRouter(WxMaService service) {
        final WxMaMessageRouter router = new WxMaMessageRouter(service);
        router
            .rule().handler(logHandler).next()
            .rule().async(false).content("模板").handler(templateMsgHandler).end()
            .rule().async(false).content("文本").handler(textHandler).end()
            .rule().async(false).content("图片").handler(picHandler).end()
            .rule().async(false).content("二维码").handler(qrcodeHandler).end();
        return router;
    }

}
