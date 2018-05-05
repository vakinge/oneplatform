/**
 * 
 */
package com.oneplatform.base.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.common.util.DateUtils;
import com.jeesuite.common.util.ResourceUtils;
import com.jeesuite.springweb.utils.WebUtils;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月19日
 */
@WebServlet(urlPatterns = "/service/*", description = "服务状态检查")
public class ServiceCheckServlet extends HttpServlet {

	private static final String INFO = "info";

	private static final String STATUS = "status";

	private static final String HEALTH = "health";

	private static final long serialVersionUID = 1L;
	
	private static String startupTime = DateUtils.format(new Date());
	
	private volatile Date lastHeartbeatTime = new Date();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String act = req.getPathInfo().substring(1);
       
		Map<String, String> map = new LinkedHashMap<>();
		if(HEALTH.equals(act)){
			map.put(STATUS, "UP");
		}else if(INFO.equals(act)){
			map.put("app", ResourceUtils.getProperty("jeesuite.configcenter.appName"));
		}
		
		map.put("startupTime", startupTime);
		map.put("lastHeartbeatTime", DateUtils.format(lastHeartbeatTime));
		lastHeartbeatTime = new Date();
		WebUtils.responseOutJson(resp, JsonUtils.toJson(map));
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
