package blog.dongguabai.lb.core.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dongguabai
 * @date 2018/11/2 8:54
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcAnnotation {

    /**
     * 对外发布服务的接口
     *
     * @return
     */
    Class<?> value();

    /**
     * 版本，用来区分不同版本
     * @return
     */
    String version() default "";
}
