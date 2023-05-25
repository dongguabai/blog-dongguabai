package blog.dongguabai.dongguabai.security.core;

import blog.dongguabai.dongguabai.security.core.annotation.X5RequestMapping;
import blog.dongguabai.dongguabai.security.core.interceptor.X5RequestValidHandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.List;

public class X5RequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    private X5RequestMappingProperties x5RequestMappingProperties;
    private ObjectMapper objectMapper;
    public static final int TYPE_CONDITION = 1;
    public static final int METHOD_CONDITION = 2;

    public X5RequestMappingHandlerMapping(X5RequestMappingProperties x5RequestMappingProperties, ObjectMapper objectMapper) {
        this.x5RequestMappingProperties = x5RequestMappingProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        X5RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, X5RequestMapping.class);
        if (requestMapping == null) {
            return null;
        }
        X5RequestMethodRequestCondition x5Condition = new X5RequestMethodRequestCondition(METHOD_CONDITION, requestMapping.x5method()
                , requestMapping.bodyClass(), objectMapper);
        return x5Condition;
    }

    @Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        X5RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(handlerType, X5RequestMapping.class);
        if (requestMapping == null) {
            return null;
        }
        X5RequestMethodRequestCondition x5Condition = new X5RequestMethodRequestCondition(TYPE_CONDITION, requestMapping.x5method()
                , requestMapping.bodyClass(), objectMapper);
        return x5Condition;
    }

    @Override
    protected void extendInterceptors(List<Object> interceptors) {
        interceptors.add(new X5RequestValidHandlerInterceptor(x5RequestMappingProperties));
    }
}
