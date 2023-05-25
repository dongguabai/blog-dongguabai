package blog.dongguabai.dongguabai.security.core;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@Setter
@Getter
@ConfigurationProperties(prefix = "comb" + ".x5")
public class X5RequestMappingProperties {
    /**
     * 开启x5RequestMapping，异常处理
     */
    private Boolean enableExceptionHandler = true;
    private Set<X5RequestSecret> requestSecrets;

}