package blog.dongguabai.springbootmultidatasource.mapper.mapper2;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 02:50
 */
public interface Spv1T1Mapper {
    @Insert("insert into spvi_t1 (id) values(#{id})")
    Integer insertId2(@Param("id")Object id);

    @Select("select * from spvi_t1")
    List<Integer> searchAll();
}
