package blog.dongguabai.atomikos.service;

import blog.dongguabai.atomikos.mapper.TMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-22 17:25
 */
@Service
public class TService {

    @Autowired
    private TMapper tMapper;

    public Object insertId(Object id){
        return tMapper.insertId(id);
    }

    public Object selectById(Object id){
        return tMapper.selectById(id);
    }
}
