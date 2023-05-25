package blog.dongguabai.atomikos.service3;

import blog.dongguabai.atomikos.entity.TClass;
import blog.dongguabai.atomikos.entity.Teacher;
import blog.dongguabai.atomikos.service.TService;
import blog.dongguabai.atomikos.service2.T2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Dongguabai
 * @description jtaService
 * @date 2021-09-22 17:25
 */
@Service
public class JtaService {

    @Autowired
    private TService tService;

    @Autowired
    private T2Service t2Service;

   @Transactional(propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
   //@Transactional(transactionManager = "xatx", propagation = Propagation.REQUIRED, rollbackFor = { RuntimeException.class })
    public Object test01() {
        tService.insertId(1);
       t2Service.insertId2("abc");
       Object o = tService.selectById(1);
       System.out.println(o);
       // t2Service.insertId2(1);
        //int i = 0/0;
        return "OK";
    }
}
