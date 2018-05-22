package com.etiansoft.ole.index.interceptor;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.etiansoft.ole.constants.Constants;
import com.etiansoft.ole.po.SysUser;
import com.etiansoft.ole.util.DateUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	private List<String> excludeUrls;

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		UrlPathHelper helper = new UrlPathHelper();
		String relativePath = helper.getOriginatingRequestUri(request);
		String contextPath = helper.getOriginatingContextPath(request);

		if (!StringUtils.isEmpty(contextPath) && !"/".equals(contextPath)) {//$NON-NLS-1$
			relativePath = relativePath.substring(contextPath.length());
		}

		if (relativePath.startsWith("/static/")) {
			return true;
		}

		List<String> excludeUrls = getExcludeUrls();
		if (excludeUrls.contains(relativePath)) {
			return true;
		}

		HttpSession session = request.getSession();
		String currentDate = DateUtil.format(new Date(), "yyyy-MM-dd");
		String[] str = currentDate.split("-");
		session.setAttribute("dateStr", str);
		session.setAttribute("currentDate", currentDate);
		SysUser user = (SysUser) session.getAttribute(Constants.LOGIN_USER);
		if (user != null) {
			List<String> permissions = (List<String>) session.getAttribute(Constants.PERMISSIONS);
			if (permissions == null) {
				return false;
			}
			boolean contains = permissions.contains(relativePath);
			if (contains) {
				return true;
			}
			response.sendRedirect(contextPath + "/403.html");
			return false;
		}

		if (!Constants.LOGIN_URL.equals(relativePath)) {
			response.sendRedirect(contextPath + Constants.LOGIN_URL);
			return false;
		}

		return false;
	}

	public List<String> getExcludeUrls() {
		if (excludeUrls == null) {
			excludeUrls = Collections.emptyList();
		}
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}
}
