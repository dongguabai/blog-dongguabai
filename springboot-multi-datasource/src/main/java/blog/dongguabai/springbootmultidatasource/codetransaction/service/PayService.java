package blog.dongguabai.springbootmultidatasource.codetransaction.service;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Dongguabai
 * @description 支付Service
 * @date 2021-09-27 09:10
 */
@Service
public class PayService {

    /**
     * 远程支付
     *
     */
    public void rpcPay(Integer orderId) {
        System.out.println(new Date().toLocaleString() + "->PayService.rpcPay【" + orderId + "】 start...");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new Date().toLocaleString() + "->PayService.rpcPay【" + orderId + "】 end...");
    }
}
