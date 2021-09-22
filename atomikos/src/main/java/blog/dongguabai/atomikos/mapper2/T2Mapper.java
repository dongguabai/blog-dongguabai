package blog.dongguabai.atomikos.mapper2;

import blog.dongguabai.atomikos.entity.TClass;
import blog.dongguabai.atomikos.util.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-22 17:27
 */
public interface T2Mapper extends MyMapper<TClass> {

    @Insert("insert into demo1_t1 (id) values(#{id})")
    Integer insertId2(@Param("id")Object id);
}
