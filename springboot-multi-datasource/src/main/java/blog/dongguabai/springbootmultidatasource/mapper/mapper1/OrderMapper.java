package blog.dongguabai.springbootmultidatasource.mapper.mapper1;

import blog.dongguabai.springbootmultidatasource.entity.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-27 09:00
 */
public interface OrderMapper {

    @Insert("insert into demo1_order_info(order_state) values (#{orderInfo.orderState})")
    @Options(useGeneratedKeys = true, keyProperty = "orderInfo.id", keyColumn = "id")
    int insertOrder(@Param("orderInfo") OrderInfo orderInfo);

    @Insert("update demo1_order_info set order_state = #{orderState} where id = #{orderId}")
    int updateOrderById(@Param("orderId") Integer orderId, @Param("orderState") String orderState);

    @Select("select * from demo1_order_info")
    List<OrderInfo> searchAll();
}
