package blog.dongguabai.spring.web.mvc.handlerinterceptor.core;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

/**
 * @author dongguabai
 * @date 2023-11-20 01:43
 */
public class CustomInterceptorConfigurer implements WebMvcConfigurer {

    private ApplicationContext applicationContext;

    public CustomInterceptorConfigurer(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Map<String, CustomizedHandlerMethodInterceptor> interceptors = applicationContext.getBeansOfType(CustomizedHandlerMethodInterceptor.class);
        interceptors.values().forEach(registry::addInterceptor);
    }
}