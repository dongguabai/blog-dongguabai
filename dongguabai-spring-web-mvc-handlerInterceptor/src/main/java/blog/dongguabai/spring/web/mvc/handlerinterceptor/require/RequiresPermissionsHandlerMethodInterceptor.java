package blog.dongguabai.spring.web.mvc.handlerinterceptor.require;

import blog.dongguabai.spring.web.mvc.handlerinterceptor.RequestContext;
import blog.dongguabai.spring.web.mvc.handlerinterceptor.core.CustomizedHandlerMethodInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author dongguabai
 * @date 2023-11-19 23:34
 */
@Component
public class RequiresPermissionsHandlerMethodInterceptor extends CustomizedHandlerMethodInterceptor<RequiresPermissions> {

    @Override
    protected boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, RequiresPermissions annotation) throws Exception {
        List<String> permissons = Arrays.asList(annotation.value());
        if (RequestContext.getCurrentUser().getPermissions().stream().anyMatch(permissons::contains)){
            return true;
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\":\"无权限...\"}");
        return false;
    }

    @Override
    protected void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, RequiresPermissions annotation, Exception ex) throws Exception {

    }

    @Override
    protected void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, ModelAndView modelAndView, RequiresPermissions annotation) throws Exception {

    }
}