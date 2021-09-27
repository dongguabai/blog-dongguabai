package blog.dongguabai.springbootmultidatasource.codetransaction.service;

import blog.dongguabai.springbootmultidatasource.entity.OrderInfo;
import blog.dongguabai.springbootmultidatasource.mapper.mapper1.OrderMapper;
import blog.dongguabai.springbootmultidatasource.mapper.mapper1.VipMapper;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Dongguabai
 * @description 订单Service
 * @date 2021-09-27 08:59
 */
@Service
@Slf4j
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
        orderMapper.insertOrder(orderInfo);        //本地事务1
        vipMapper.insertOrderId(orderInfo.getId());    //本地事务2

        payService.rpcPay(orderInfo.getId());   //rpc
        return ImmutableMap.of("订单号:", orderInfo.getId());
    }

    @Autowired
    private TransactionTemplate transactionTemplate;

    public Object createOrder2() {
        OrderInfo orderInfo = transactionTemplate.execute(status -> {
            OrderInfo insert = new OrderInfo();
            insert.setOrderState("创建中");
            orderMapper.insertOrder(insert);        //本地事务1
            vipMapper.insertOrderId(insert.getId());    //本地事务2
            return insert;
        });
        try {
            payService.rpcPay(orderInfo.getId());   //rpc
        } catch (Exception e) {
            log.error("rpc异常...", e);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    orderMapper.updateOrderById(orderInfo.getId(), "失效");
                    vipMapper.deleteByOrderId(orderInfo.getId());
                }
            });
            return ImmutableMap.of("success", false);
        }
        //confirm
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                orderMapper.updateOrderById(orderInfo.getId(), "有效");
            }
        });
        return ImmutableMap.of("success", true, "订单号:", orderInfo.getId());
    }

    public Object searchAll() {
        return orderMapper.searchAll();
    }
}
