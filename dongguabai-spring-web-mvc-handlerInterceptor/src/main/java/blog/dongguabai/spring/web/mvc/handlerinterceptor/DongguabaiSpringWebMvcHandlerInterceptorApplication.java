package blog.dongguabai.spring.web.mvc.handlerinterceptor;

import blog.dongguabai.spring.web.mvc.handlerinterceptor.require.RequiresPermissionsHandlerMethodInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class DongguabaiSpringWebMvcHandlerInterceptorApplication {

    public static void main(String[] args) {
        SpringApplication.run(DongguabaiSpringWebMvcHandlerInterceptorApplication.class, args);
    }

}
