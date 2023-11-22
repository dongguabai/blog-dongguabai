package blog.dongguabai.spring.web.mvc.handlerinterceptor;

import java.util.Arrays;

/**
 * @author dongguabai
 * @date 2023-11-20 01:21
 */
public final class RequestContext {

    public static User getCurrentUser() {
        User user = new User();
        user.setUsername("tom");
        user.setPermissions(Arrays.asList("ADMIN", "STUDENT"));
        return user;
    }
}