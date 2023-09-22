package blog.dongguabai.lb.core.registry;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dongguabai
 * @date 2023-09-22 13:59
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "address")
@AllArgsConstructor
public class Invoker {

    private String address;

    /**
     * 权重
     */
    private int weight = 10;

}