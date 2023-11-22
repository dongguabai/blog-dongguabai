package blog.dongguabai.spring.web.mvc.handlerinterceptor;

import java.util.List;

/**
 * @author dongguabai
 * @date 2023-11-20 01:21
 */
public class User {

    private String username;

    private List<String> permissions;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}