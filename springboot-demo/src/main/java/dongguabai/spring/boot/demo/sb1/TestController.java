package dongguabai.spring.boot.demo.sb1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author dongguabai
 * @date 2024-01-14 23:22
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(@RequestParam("id") String id) {
        return new Date().toLocaleString() + "->" + id;
    }
}