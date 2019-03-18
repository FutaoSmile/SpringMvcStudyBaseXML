package com.futao.springmvcdemo.foundation;

import com.futao.springmvcdemo.model.system.Constant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author futao
 * Created on 2019-03-18.
 */
@Slf4j
@WebFilter(filterName = "appFilter", urlPatterns = "/*")
public class AppFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding(Constant.UTF8_ENCODE);

        response.setCharacterEncoding(Constant.UTF8_ENCODE);

        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);


        log.info(StringUtils.repeat("-", 30) + "AppFilter拦截");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
