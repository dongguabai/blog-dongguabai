package blog.dongguabai.lb.core.registry.loadbalance;

import blog.dongguabai.lb.core.registry.Invoker;

import java.util.List;

/**
 * 负载顶层接口
 * @author Dongguabai
 * @date 2018/11/2 10:11
 */
public interface LoadBalance {

    Invoker selectHost(List<Invoker> repos);
}
