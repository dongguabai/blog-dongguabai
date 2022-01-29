package blog.dongguabai.springbootmultidatasource.codetransaction.controller;

import blog.dongguabai.springbootmultidatasource.codetransaction.service.Test事务失效Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dongguabai
 * @description 测试事务注解失效
 * @date 2021-10-25 23:45
 */
@RestController
@RequestMapping("test")
public class Test事务失效Controller {

    @Autowired
    private Test事务失效Service testService;

    @RequestMapping("t1")
    public Object t1(){
        return testService.test("99999");
    }
}
