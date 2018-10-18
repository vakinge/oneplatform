/**
 * 
 */
package com.oneplatform.base.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;

import com.google.common.io.CharStreams;
import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.springweb.utils.WebUtils;
import com.oneplatform.base.util.ApiInfoHolder;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月19日
 */
@WebServlet(urlPatterns = "/metadata/*", description = "获取api列表")
public class ModuleMetadataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static Map<String, String> metadatas = new HashMap<>();
	
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			Resource[] resources = ResourcePatternUtils.getResourcePatternResolver(new DefaultResourceLoader()).getResources("classpath*:metadata/*.json");
			for (Resource resource : resources) {
				String name = resource.getFilename().replace(".json", "");
				String content = CharStreams.toString(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
				metadatas.put(name, content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String type = req.getPathInfo().substring(1);
		if("api".equals(type)){			
			WebUtils.responseOutJson(resp, JsonUtils.toJson(ApiInfoHolder.getApiInfos()));
		}else{	
			String json = metadatas.get(type);
			if(StringUtils.isBlank(json)){
				json = "{}";
			}
			WebUtils.responseOutJson(resp, json);
		}
	}
	
	

	@Override
	public void destroy() {
		super.destroy();
	}
}
