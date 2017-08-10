package com.zhongqi.filter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by songrenfei on 2016/12/23.
 */

public class GlobalFilter implements Filter {

    Log logger = LogFactory.getLog(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("filter init");
    }

    @Override
    public void destroy() {
        logger.info("filter destroy");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String userId = httpServletRequest.getHeader("Authorization");

        if (userId == null || "".equals(userId.trim())) {
            userId = httpServletRequest.getParameter("testUserId");
        }

        httpServletRequest.setAttribute("userId", userId);

        // 打印URI+参数
        this.printUrlParams(httpServletRequest, userId);

        filterChain.doFilter(servletRequest, servletResponse);
    }


    private void printUrlParams(HttpServletRequest request, String userId) {
        /* ---------- 打印用户访问URI+参数 start ---------- */
        // 用户访问URI（不带参数）
        String uri = request.getRequestURI();
        Enumeration params = request.getParameterNames();
        String paramsStr = "";

        while (params.hasMoreElements()) {
            String paramName = String.valueOf(params.nextElement());
            if (paramName != null && "base64String".equals(paramName.trim())) {

            } else {
                String paramValue = request.getParameter(paramName);
                paramsStr = paramsStr + "&" + paramName + "=" + paramValue;
            }
        }
        paramsStr = paramsStr.replaceFirst("&", "?");
        logger.info("[ userId = " + userId + " ] " + uri + paramsStr);
        /* ---------- 打印用户访问URI+参数 end ---------- */
    }

}
