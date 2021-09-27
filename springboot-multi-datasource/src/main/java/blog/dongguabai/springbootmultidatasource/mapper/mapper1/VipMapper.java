package blog.dongguabai.springbootmultidatasource.mapper.mapper1;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-27 09:15
 */
public interface VipMapper {

    @Insert("insert into demo1_t1 (id) values(#{id})")
    Integer insertOrderId(@Param("id")Object id);

    @Insert("delete fro  demo1_t1 where id = #{id}")
    Integer deleteByOrderId(@Param("id")Object id);
}
