package blog.dongguabai.springbootmultidatasource.service;

import blog.dongguabai.springbootmultidatasource.mapper.mapper1.Demo1T1Mapper;
import blog.dongguabai.springbootmultidatasource.mapper.mapper2.Spv1T1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 02:51
 */
@Service
public class TestService {
    @Autowired
    private Demo1T1Mapper demo1T1Mapper;

    @Autowired
    private Spv1T1Mapper spv1T1Mapper;

    /**
     * 验证一个@Transactional下，两个数据源操作
     *
     * spvi commit
     * demo1 rollback
     */
    @Transactional
    public Object test1(String i) {
        spv1T1Mapper.insertId2(Integer.valueOf(i));
        demo1T1Mapper.insertId(Integer.valueOf(i));
        int a = 1/0;
        return "OK";
    }

    @Transactional
    public String test2(String id) {
        System.out.println("11111111111");
        System.out.println("22222222222");
        spv1T1Mapper.insertId2(Integer.valueOf(id));
        return "OK";
    }
}
