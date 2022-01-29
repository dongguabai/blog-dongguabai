package blog.dongguabai.springbootmultidatasource.codetransaction.service;

import blog.dongguabai.springbootmultidatasource.mapper.mapper1.Demo1T1Mapper;
import blog.dongguabai.springbootmultidatasource.mapper.mapper2.Spv1T1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dongguabai
 * @description
 * @date 2021-10-25 23:45
 */
@Service
public class Test事务失效Service {

    @Autowired
    private Demo1T1Mapper demo1T1Mapper;

    @Autowired
    private Spv1T1Mapper spv1T1Mapper;

    @Transactional
    public Object test1(String i) {
        spv1T1Mapper.insertId2(Integer.valueOf(i));     //本地事务1

        int j = 1/0;

        return "OK";
    }

    public Object test(String i){
        System.out.println("test.....");
        return test1(i);
    }
}
