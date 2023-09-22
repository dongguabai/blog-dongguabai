package blog.dongguabai.lb.core.registry.loadbalance;

import blog.dongguabai.lb.core.registry.Invoker;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author Dongguabai
 * @date 2018/11/2 10:15
 */
public abstract class AbstractLoadBanance implements LoadBalance {

    /**
     * 通过模板方法，做一些牵制操作
     *
     * @param repos
     * @return
     */
    @Override
    public Invoker selectHost(List<Invoker> repos) {
        if (CollectionUtils.isEmpty(repos)) {
            return null;
        }
        if (repos.size() == 1) {
            return repos.get(0);
        }
        return doSelect(repos);
    }

    /**
     * 实现具体的实现负载算法
     *
     * @param repos
     * @return
     */
    protected abstract Invoker doSelect(List<Invoker> repos);

}
