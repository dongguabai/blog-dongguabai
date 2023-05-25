package blog.dongguabai.dongguabai.security.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class, WebMvcConfigurer.class})
@AutoConfigureOrder
@AutoConfigureAfter({WebMvcAutoConfiguration.class})
@EnableConfigurationProperties(X5RequestMappingProperties.class)
public class X5RequestMappingAutoConfiguration implements WebMvcConfigurer {

    @Autowired
    private List<HttpMessageConverter<?>> httpMessageConverters;

    @Autowired
    private X5RequestMappingProperties x5RequestMappingProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new X5requestBodyMethodArgumentResolver(httpMessageConverters));
    }

    @Bean
    public WebMvcRegistrations webMvcRegistrations() {
        return new X5WebMvcRegistrations(x5RequestMappingProperties, objectMapper);
    }


    @Bean
    @ConditionalOnProperty(prefix =  CombUtils.COMB_PREFIX + ".x5",name = "enable-exception-handler",havingValue = "true",matchIfMissing = true)
    public X5RequestMethodHandlerExceptionResolver exceptionResolver() {
        X5RequestMethodHandlerExceptionResolver exceptionResolver = new X5RequestMethodHandlerExceptionResolver(objectMapper);
        return exceptionResolver;
    }

}
