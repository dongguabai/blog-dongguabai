package blog.dongguabai.spring.web.mvc.handlerinterceptor.canyon;

import blog.dongguabai.spring.web.mvc.handlerinterceptor.RequestContext;
import blog.dongguabai.spring.web.mvc.handlerinterceptor.core.CustomizedHandlerMethodInterceptor;
import blog.dongguabai.spring.web.mvc.handlerinterceptor.require.RequiresPermissions;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author dongguabai
 * @date 2023-11-19 23:34
 */
@Component
public class CanyonHandlerMethodInterceptor extends CustomizedHandlerMethodInterceptor<Canyon> {

    @Override
    protected boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Canyon annotation) throws Exception {
        if (tryAcquire()) {
            return true;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(String.format("{\"message\":\"%s\"}", annotation.message()));
        return false;
    }

    @Override
    protected void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Canyon annotation, Exception ex) throws Exception {

    }

    @Override
    protected void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, ModelAndView modelAndView, Canyon annotation) throws Exception {

    }

    /**
     * todo:流量控制逻辑
     */
    private boolean tryAcquire() {
        return false;
    }
}