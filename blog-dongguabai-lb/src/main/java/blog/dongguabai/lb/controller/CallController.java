package blog.dongguabai.lb.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dongguabai
 * @date 2023-09-08 17:03
 */
@RestController
public class CallController {

    @Value("${server.port}")
    private int port;

    @RequestMapping("call")
    public Object call() {
        return String.format("【%d call】", port);
    }
}