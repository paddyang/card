package com.card.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.card.utils.BaseResponse;
import com.card.utils.GsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;


/**
 * 用户登录拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//获取当前登录用户
		String username = (String) request.getSession().getAttribute("username");
		if (StringUtils.isBlank(username)) {
			//判断是否是ajax请求
			boolean isAjax = "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
			if (isAjax){
				PrintWriter out=response.getWriter();
				out.append(GsonUtils.GsonString(BaseResponse.build(400,"请重新登录！")));
			}else{
				//跳转到登录页面
				response.sendRedirect(request.getContextPath()+"/html/login.html");
			}
			return false;
		}
		//放行
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	

}
