package blog.dongguabai.springbootmultidatasource.controller;

import blog.dongguabai.springbootmultidatasource.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 02:50
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("t1/{id}")
    public Object t1(@PathVariable("id") String id){
        return testService.test1(id);
    }
}
