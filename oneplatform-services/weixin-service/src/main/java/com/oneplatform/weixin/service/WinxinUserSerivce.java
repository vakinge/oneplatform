package com.oneplatform.weixin.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeesuite.common.JeesuiteBaseException;
import com.oneplatform.weixin.component.WeixinAppManager;
import com.oneplatform.weixin.dao.entity.WeixinBindingEntity;
import com.oneplatform.weixin.dao.mapper.WeixinBindingEntityMapper;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import me.chanjar.weixin.common.error.WxErrorException;

@Service
public class WinxinUserSerivce {

	private @Autowired WeixinAppManager weixinAppManager;
	private @Autowired WeixinBindingEntityMapper weixinBindingMapper;

	public Integer findUserIdByOpenId(String openId) {
		WeixinBindingEntity bindingEntity = weixinBindingMapper.findByOpenId(openId);
		return bindingEntity == null ? null : bindingEntity.getUserId();
	}


	public void addWeixinBinding(Integer userId,String openType,String openId,String unionId,String fromApp) {
		WeixinBindingEntity bindingEntity = new WeixinBindingEntity();
		bindingEntity.setUserId(userId);
		bindingEntity.setOpenId(openId);
		bindingEntity.setOpenType(openType);
		bindingEntity.setUnionId(unionId);
		bindingEntity.setSourceApp(fromApp);
		bindingEntity.setCreatedAt(new Date());
		weixinBindingMapper.insertSelective(bindingEntity);
	}
	
	public Integer findUserIdByWeAppCode(String group,String code){
		final WxMaService wxService = weixinAppManager.getMaService(group);
        try {
            WxMaJscode2SessionResult wxsession = wxService.getUserService().getSessionInfo(code);
            Integer userId = findUserIdByOpenId(wxsession.getOpenid());
            return userId;
        } catch (WxErrorException e) {
            throw new JeesuiteBaseException(500, e.getMessage());
        }
	}

}
