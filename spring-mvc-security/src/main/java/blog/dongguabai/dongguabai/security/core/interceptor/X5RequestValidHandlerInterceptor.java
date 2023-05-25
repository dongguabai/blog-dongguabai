package blog.dongguabai.dongguabai.security.core.interceptor;

import blog.dongguabai.dongguabai.security.core.exception.X5RequestException;
import blog.dongguabai.dongguabai.security.core.exception.X5RequestHeadMissException;
import org.springframework.core.Ordered;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class X5RequestValidHandlerInterceptor extends HandlerInterceptorAdapter implements Ordered {

    private static final Logger LOGGER=LoggerFactory.getLogger(X5RequestValidHandlerInterceptor.class);

    private Map<String, X5RequestSecret> x5RequestSecretMap;

    public X5RequestValidHandlerInterceptor(X5RequestMappingProperties x5RequestMappingProperties) {
        Objects.nonNull(x5RequestMappingProperties);
        this.x5RequestSecretMap = x5RequestMappingProperties.getRequestSecrets().stream().collect(Collectors.toMap(X5RequestSecret::getAppId, Function.identity()));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = getRequestAttribute(request, X5REQUEST_ROUTE);
        if (StringUtils.isEmpty(method)) {
            return true;
        }
        String sign = getRequestAttribute(request, X5RequestConstants.X5REQUEST_SIGN);
        if (StringUtils.isEmpty(sign)) {
            throw new X5RequestHeadMissException("sign", "x5request Head miss");
        }
        String body = request.getAttribute(X5RequestConstants.X5REQUEST_ENCODE) == null ? StringUtils.EMPTY : (String) request.getAttribute(X5RequestConstants.X5REQUEST_ENCODE);
        String appId = getRequestAttribute(request, X5RequestConstants.X5REQUEST_APP_ID);
        X5RequestSecret x5RequestSecret = x5RequestSecretMap.get(appId);
        if (x5RequestSecret == null) {
            throw new X5RequestException("appId information not exist");
        }
        if (!x5RequestSecret.getAllowMethods().contains(method)) {
            throw new X5RequestException("appId method not match");
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(appId);
        buffer.append(body);
        buffer.append(x5RequestSecret.getAppKey());
        String content = DigestUtils.md5DigestAsHex(buffer.toString().getBytes()).toUpperCase();
        if (!content.equals(sign)) {
            throw new X5RequestException("x5request valid fail");
        }
        LOGGER.debug("valid x5 requset");
        return true;
    }

    private static String getRequestAttribute(HttpServletRequest request, String key) {
        Object value = request.getAttribute(key);
        return value == null ? StringUtils.EMPTY : (String) value;
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
