package blog.dongguabai.springbootmultidatasource.entity;

import java.io.Serializable;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-27 09:05
 */
public class OrderInfo implements Serializable {

    private Integer id;

    private String orderState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }
}
