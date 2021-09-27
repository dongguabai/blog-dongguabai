package blog.dongguabai.springbootmultidatasource.codetransaction.service;

import blog.dongguabai.springbootmultidatasource.entity.OrderInfo;
import blog.dongguabai.springbootmultidatasource.mapper.mapper1.OrderMapper;
import blog.dongguabai.springbootmultidatasource.mapper.mapper1.VipMapper;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Dongguabai
 * @description 订单Service
 * @date 2021-09-27 08:59
 */
@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PayService payService;

    @Autowired
    private VipMapper vipMapper;

    @Transactional
    public Object createOrder() {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderState("有效");
        //orderMapper.insertOrder(orderInfo);        //本地事务1
        vipMapper.insertOrderId(orderInfo.getId());    //本地事务2

       // payService.rpcPay();   //rpc
        //return ImmutableMap.of("订单号:", orderInfo.getId());
        return "OK";
    }

    public Object searchAll() {
        return orderMapper.searchAll();
    }
}
