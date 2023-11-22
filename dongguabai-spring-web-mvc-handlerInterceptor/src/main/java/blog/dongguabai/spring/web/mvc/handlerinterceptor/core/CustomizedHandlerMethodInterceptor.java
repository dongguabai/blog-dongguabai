package blog.dongguabai.spring.web.mvc.handlerinterceptor.core;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * @author dongguabai
 * @date 2023-11-19 23:43
 */
public abstract class CustomizedHandlerMethodInterceptor<A extends Annotation> implements HandlerInterceptor {

    private final Class<A> annotationType;

    protected CustomizedHandlerMethodInterceptor() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.annotationType = (Class<A>) superclass.getActualTypeArguments()[0];
    }

    protected abstract boolean preHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, A annotation) throws Exception;

    protected abstract void afterCompletion(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, A annotation, Exception ex) throws Exception;

    protected abstract void postHandle(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, ModelAndView modelAndView, A annotation) throws Exception;

    @Override
    public final boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            A annotation = getAnnotation((HandlerMethod) handler);
            if (match(annotation)) {
                return preHandle(request, response, (HandlerMethod) handler, annotation);
            }
        }
        return true;
    }

    @Override
    public final void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            A annotation = getAnnotation((HandlerMethod) handler);
            if (match(annotation)) {
                postHandle(request, response, (HandlerMethod) handler, modelAndView, annotation);
            }
        }
    }

    @Override
    public final void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            A annotation = getAnnotation((HandlerMethod) handler);
            if (match(annotation)) {
                afterCompletion(request, response, (HandlerMethod) handler, annotation, ex);
            }
        }
    }

    protected A getAnnotation(HandlerMethod handlerMethod) {
        return handlerMethod.getMethodAnnotation(annotationType);
    }

    protected boolean match(A annotation) {
        return Objects.nonNull(annotation);
    }

}