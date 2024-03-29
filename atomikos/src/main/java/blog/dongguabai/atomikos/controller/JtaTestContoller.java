package blog.dongguabai.atomikos.controller;


import blog.dongguabai.atomikos.service3.JtaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/jtaTest")
public class JtaTestContoller {

    @Autowired
    private JtaService jtaService;



    @ResponseBody
    @RequestMapping("/test01")
    public Object test01(){
        LinkedHashMap<String,Object> resultMap=new LinkedHashMap<String,Object>();
        try {
            return jtaService.test01();
        }catch (Exception e){
            e.printStackTrace();
            resultMap.put("state","fail");
            resultMap.put("message","分布式事务同步失败");
            return resultMap;
        }
    }






}
