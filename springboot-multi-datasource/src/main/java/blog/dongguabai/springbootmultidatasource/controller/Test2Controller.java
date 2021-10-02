package blog.dongguabai.springbootmultidatasource.controller;

import blog.dongguabai.springbootmultidatasource.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dongguabai
 * @description
 * @date 2021-10-02 00:35
 */
@RestController
public class Test2Controller {

    @Autowired
    private TestService testService;

    @RequestMapping("t2/{id}")
    public Object t1(@PathVariable("id") String id){
        return testService.test2(id);
    }
}
