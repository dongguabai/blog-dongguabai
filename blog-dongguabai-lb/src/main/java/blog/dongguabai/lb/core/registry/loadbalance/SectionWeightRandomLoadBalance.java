package blog.dongguabai.lb.core.registry.loadbalance;

import blog.dongguabai.lb.core.registry.Invoker;

import java.util.List;
import java.util.Random;

/**
 * @author dongguabai
 * @date 2023-09-22 14:20
 */
public class SectionWeightRandomLoadBalance extends RandomLoadBanalce {

    @Override
    protected Invoker doSelect(List<Invoker> invokers) {
        boolean averageWeight = true;
        int totalWeight = 0;
        for (int i = 0; i < invokers.size(); i++) {
            Invoker invoker = invokers.get(i);
            if (averageWeight && i > 0 && invoker.getWeight() != invokers.get(i - 1).getWeight()) {
                averageWeight = false;
            }
            totalWeight += invoker.getWeight();
        }
        if (averageWeight || totalWeight < 1) {
            return super.doSelect(invokers);
        }
        int index = new Random().nextInt(totalWeight);
        for (Invoker invoker : invokers) {
            if (index < invoker.getWeight()) {
                return invoker;
            }
            index -= invoker.getWeight();
        }
        return super.doSelect(invokers);
    }
}