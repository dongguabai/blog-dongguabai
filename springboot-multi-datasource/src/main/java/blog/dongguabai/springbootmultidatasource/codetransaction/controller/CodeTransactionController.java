package blog.dongguabai.springbootmultidatasource.codetransaction.controller;

import blog.dongguabai.springbootmultidatasource.codetransaction.service.CodeTransactionService;
import blog.dongguabai.springbootmultidatasource.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 03:19
 */
@RestController
@RequestMapping("code")
public class CodeTransactionController {

    @Autowired
    private CodeTransactionService codeTransactionService;

    @RequestMapping("t1/{id}")
    public Object t1(@PathVariable("id") String id){
        return codeTransactionService.test1(id);
    }

    @RequestMapping("t1_1/{id}")
    public Object test1_1(@PathVariable("id") String id){
        return codeTransactionService.test1_1(id);
    }

    @RequestMapping("search/spviall")
    public Object searchSpviAll(){
        return codeTransactionService.searchSpviAll();
    }
}
