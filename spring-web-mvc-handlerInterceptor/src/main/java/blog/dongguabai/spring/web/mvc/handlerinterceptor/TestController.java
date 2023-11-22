package blog.dongguabai.spring.web.mvc.handlerinterceptor;

import blog.dongguabai.spring.web.mvc.handlerinterceptor.canyon.Canyon;
import blog.dongguabai.spring.web.mvc.handlerinterceptor.require.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongguabai
 * @date 2023-11-20 00:17
 */
@RestController
public class TestController {

    @GetMapping("/get-reports")
    @RequiresPermissions("BOSS")
    public String getReports() {
        return "ALL...";
    }

    @GetMapping("/search")
    @RequiresPermissions("ADMIN")
    @Canyon(1)
    public String search() {
        return "search...";
    }
}