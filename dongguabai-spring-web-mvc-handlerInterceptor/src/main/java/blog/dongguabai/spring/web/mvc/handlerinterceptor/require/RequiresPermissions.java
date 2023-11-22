package blog.dongguabai.spring.web.mvc.handlerinterceptor.require;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dongguabai
 * @description
 * @date 2023-11-19 23:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface RequiresPermissions {

    // Permissions
    String[] value();
}
