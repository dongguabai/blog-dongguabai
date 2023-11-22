package blog.dongguabai.spring.web.mvc.handlerinterceptor.core;

import blog.dongguabai.spring.web.mvc.handlerinterceptor.require.RequiresPermissionsHandlerMethodInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Map;

/**
 * @author dongguabai
 * @date 2023-11-20 01:00
 */
@Configuration
public class WebMvcConfig {

    @Bean
    public CustomInterceptorConfigurer customInterceptorConfigurer(ApplicationContext applicationContext) {
        return new CustomInterceptorConfigurer(applicationContext);
    }
}