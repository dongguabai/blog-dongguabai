package blog.dongguabai.springbootmultidatasource.codetransaction.controller;

import blog.dongguabai.springbootmultidatasource.codetransaction.service.CodeTransactionService;
import blog.dongguabai.springbootmultidatasource.codetransaction.service.OrderService;
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
@RequestMapping("order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping("create")
    public Object createOrder(){
        return orderService.createOrder3();
    }

    @RequestMapping("create2")
    public Object createOrder2(){
        return orderService.createOrder2();
    }

    @RequestMapping("all")
    public Object searchAll(){
        return orderService.searchAll();
    }

}
