package blog.dongguabai.atomikos.mapper;

import blog.dongguabai.atomikos.entity.TClass;
import blog.dongguabai.atomikos.util.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-22 17:27
 */
public interface TMapper extends MyMapper<TClass> {

    @Insert("insert into t2 (id) values(#{id})")
    Integer insertId(@Param("id")Object id);

    @Select("select * from t2 where  id  = #{id}")
    Object selectById(Object id);
}
