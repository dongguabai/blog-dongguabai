package blog.dongguabai.others.dubbopipeline.sample;

import blog.dongguabai.others.dubbopipeline.api.Operation;
import blog.dongguabai.others.dubbopipeline.api.Pipeline;

/**
 * @author dongguabai
 * @date 2024-01-17 20:09
 */
public class PipelineDemo {

    public static void main(String[] args) {
        Operation operation1 = new Operation()
                .setServiceClass(UserService.class)
                .setMethodName("findById")
                .setParameterTypes(new Class<?>[]{int.class})
                .setArgs(new Object[]{1});

        Operation operation2 = new Operation()
                .setServiceClass(UserService.class)
                .setMethodName("findById")
                .setParameterTypes(new Class<?>[]{int.class})
                .setArgs(new Object[]{2});

        Pipeline pipeline = new Pipeline()
                .addOperation(operation1)
                .addOperation(operation2);
        //List<Object> results = pipelineService.executeBatch(pipeline);
    }
}