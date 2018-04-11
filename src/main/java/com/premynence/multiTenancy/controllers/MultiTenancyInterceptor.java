package com.premynence.multiTenancy.controllers;

import com.premynence.multiTenancy.config.TenantContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MultiTenancyInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
			throws Exception {
		String tenantIdentifier = req.getHeader("x-tenant-id");
		TenantContext.setCurrentTenant(tenantIdentifier);
		return true;
	}
}