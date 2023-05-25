package blog.dongguabai.dongguabai.security.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.condition.AbstractRequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class X5RequestMethodRequestCondition extends AbstractRequestCondition<X5RequestMethodRequestCondition> {

    private int condition;
    private Set<String> methods;
    private ObjectMapper objectMapper;
    private Class<? extends X5Request> bodyClass;

    public X5RequestMethodRequestCondition(int condition, String method, Class<? extends X5Request> bodyClass, ObjectMapper objectMapper) {
        this.condition = condition;
        this.methods = new HashSet<>();
        this.methods.add(method);
        this.bodyClass = bodyClass;
        this.objectMapper = objectMapper;
    }

    public X5RequestMethodRequestCondition(int condition, Collection<String> methods, Class<? extends X5Request> bodyClass, ObjectMapper objectMapper) {
        this.condition = condition;
        this.methods = new HashSet<>(methods);
        this.bodyClass = bodyClass;
        this.objectMapper = objectMapper;
    }

    @Override
    protected Collection<?> getContent() {
        return this.methods;
    }

    @Override
    protected String getToStringInfix() {
        return "||";
    }

    @Override
    public X5RequestMethodRequestCondition combine(X5RequestMethodRequestCondition other) {
        Set<String> methods = other.methods.stream().filter(f -> StringUtils.isNoneEmpty(f)).collect(Collectors.toSet());
        if (!methods.isEmpty()) {
            this.methods.clear();
            this.methods.addAll(methods);
        }
        Class bodyClassTemp;
        int conditionTemp;
        if (other.condition == X5RequestMappingHandlerMapping.METHOD_CONDITION) {
            bodyClassTemp = other.bodyClass;
            conditionTemp = other.condition;
        } else {
            bodyClassTemp = this.bodyClass;
            conditionTemp = this.condition;
        }
        return new X5RequestMethodRequestCondition(conditionTemp, this.methods, bodyClassTemp, this.objectMapper);
    }

    @Override
    public X5RequestMethodRequestCondition getMatchingCondition(HttpServletRequest request) {
        Object decodeAttribute = request.getAttribute(X5REQUEST_DECODE);
        Boolean decode = decodeAttribute == null ? null : (Boolean) decodeAttribute;
        if (decode == null || !decode) {
            request.setAttribute(X5REQUEST_DECODE, Boolean.TRUE);
            String content = request.getParameter(X5REQUEST_PARAM);
            if (StringUtils.isEmpty(content)) {
                //兼容notify
                try {
                    content = new String(FileCopyUtils.copyToByteArray(request.getInputStream()), StandardCharsets.UTF_8);
                    if (StringUtils.isEmpty(content)) {
                        return null;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            LOG.debug("X5 request body:{}", content);
            content = new String(Base64.decodeBase64(content), StandardCharsets.UTF_8);
            X5Request x5Request;
            try {
                LOG.debug("X5 request body encode:{}", content);
                x5Request = objectMapper.readValue(content, bodyClass);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (x5Request != null && x5Request.getHeader() != null) {
                request.setAttribute(X5REQUEST, x5Request);
                request.setAttribute(X5REQUEST_HEADER, x5Request.getHeader());
                request.setAttribute(X5REQUEST_APP_ID, x5Request.getHeader().getAppId());
                request.setAttribute(X5REQUEST_ENCODE, x5Request.getBody());
                String routeMethod = x5Request.getHeader().getMethod();
                request.setAttribute(X5REQUEST_SIGN, x5Request.getHeader().getSign());
                request.setAttribute(X5REQUEST_ROUTE, StringUtils.defaultString(routeMethod, StringUtils.EMPTY));
            }
        }
        Object route = request.getAttribute(X5REQUEST_ROUTE);
        if (route == null) {
            return null;
        }
        if (methods.contains(route)) {
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(X5RequestMethodRequestCondition other, HttpServletRequest request) {
        return this.methods.size() - other.methods.size();
    }
}
