package blog.dongguabai.others.dubbopipeline.sample;

import blog.dongguabai.others.dubbopipeline.api.Operation;
import blog.dongguabai.others.dubbopipeline.api.OperationResult;
import blog.dongguabai.others.dubbopipeline.api.Pipeline;
import blog.dongguabai.others.dubbopipeline.api.PipelineService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author dongguabai
 * @date 2024-01-17 20:09
 */
public class PipelineDemo {

    @Reference
    private PipelineService pipelineService;

    public void execute() {
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
        List<OperationResult> operationResults = pipelineService.executeBatch(pipeline);
    }
}