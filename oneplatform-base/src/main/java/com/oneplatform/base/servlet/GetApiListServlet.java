/**
 * 
 */
package com.oneplatform.base.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesuite.common.json.JsonUtils;
import com.jeesuite.springweb.utils.WebUtils;
import com.oneplatform.base.util.ApiInfoHolder;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2017年3月19日
 */
@WebServlet(urlPatterns = "/getApis", description = "获取api列表")
public class GetApiListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebUtils.responseOutJson(resp, JsonUtils.toJson(ApiInfoHolder.getApiInfos()));
	}
	
	

	@Override
	public void destroy() {
		super.destroy();
	}

}
