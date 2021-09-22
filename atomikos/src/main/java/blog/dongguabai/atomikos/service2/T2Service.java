package blog.dongguabai.atomikos.service2;

import blog.dongguabai.atomikos.mapper2.T2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-22 17:25
 */
@Service
public class T2Service {

    @Autowired
    private T2Mapper t2Mapper;


    public Object insertId2(Object id){
        return t2Mapper.insertId2(id);
    }
}
