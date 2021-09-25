package blog.dongguabai.springbootmultidatasource.mapper.mapper1;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 02:47
 */
public interface Demo1T1Mapper {

    @Insert("insert into demo1_t1 (id) values(#{id})")
    Integer insertId(@Param("id")Object id);
}
