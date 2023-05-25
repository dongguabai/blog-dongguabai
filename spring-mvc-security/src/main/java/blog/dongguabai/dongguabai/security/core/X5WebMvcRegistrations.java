package blog.dongguabai.dongguabai.security.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class X5WebMvcRegistrations implements WebMvcRegistrations {

    private X5RequestMappingProperties x5RequestMappingProperties;
    private ObjectMapper objectMapper;

    public X5WebMvcRegistrations(X5RequestMappingProperties x5RequestMappingProperties, ObjectMapper objectMapper) {
        this.x5RequestMappingProperties = x5RequestMappingProperties;
        this.objectMapper = objectMapper;
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new X5RequestMappingHandlerMapping(x5RequestMappingProperties, objectMapper);
    }

}