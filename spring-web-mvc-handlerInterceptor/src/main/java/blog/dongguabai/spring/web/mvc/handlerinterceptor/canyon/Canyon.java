package blog.dongguabai.spring.web.mvc.handlerinterceptor.canyon;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author dongguabai
 * @date 2023-11-20 01:56
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Canyon {

    double value();

    long timeout() default 0;

    TimeUnit timeunit() default TimeUnit.SECONDS;

    String message() default "系统繁忙,请稍后再试.";
}