/**
 * 
 */
package com.oneplatform.base.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.jeesuite.springweb.utils.WebUtils;
import com.oneplatform.base.GlobalContants.ModuleType;
import com.oneplatform.base.model.ModuleMetadata;
import com.oneplatform.base.util.ModuleMetadataHolder;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月19日
 */
@WebServlet(urlPatterns = "/metadata", description = "获取模块元数据信息")
public class ModuleMetadataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String metadataJSON;
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		ModuleMetadata moduleMetadata = new ModuleMetadata();
		List<ModuleMetadata> metadatas = ModuleMetadataHolder.getMetadatas();
		for (ModuleMetadata md : metadatas) {
			moduleMetadata.setType(md.getType());
			moduleMetadata.setIdentifier(md.getIdentifier());
			if(md.getType().equals(ModuleType.service.name())){
				moduleMetadata.setApis(md.getApis());
				moduleMetadata.setName(md.getName());
			}else{
				if(StringUtils.isBlank(moduleMetadata.getName())){
					moduleMetadata.setName(md.getName());
				}
			}
			if(!md.getMenus().isEmpty()){
				moduleMetadata.getMenus().addAll(md.getMenus());
			}
		}
		metadataJSON = JSON.toJSONString(moduleMetadata);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebUtils.responseOutJson(resp, metadataJSON);
	}
	
	

	@Override
	public void destroy() {
		super.destroy();
	}
}
