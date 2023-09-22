package blog.dongguabai.lb.core.registry.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * 随机负载算法
 * @author Dongguabai
 * @date 2018/11/2 10:17
 */
public class RandomLoadBanalce extends AbstractLoadBanance{

    @Override
    protected String doSelect(List<String> repos) {
        return repos.get(new Random().nextInt(repos.size()));
    }
}

