package blog.dongguabai.others.dubbopipeline.provider;

import blog.dongguabai.others.dubbopipeline.api.Operation;
import blog.dongguabai.others.dubbopipeline.api.OperationResult;
import blog.dongguabai.others.dubbopipeline.api.Pipeline;
import blog.dongguabai.others.dubbopipeline.api.PipelineService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author dongguabai
 * @date 2024-01-17 20:01
 */
@Service
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public List<OperationResult> executeBatch(Pipeline pipeline) {
        List<OperationResult> results = new ArrayList<>();
        for (Operation operation : pipeline.getOperations()) {
            Object service = applicationContext.getBean(operation.getServiceClass());
            try {
                Method method = getMethod(service.getClass(), operation.getMethodName(), operation.getParameterTypes());
                Object result = method.invoke(service, operation.getArgs());
                results.add(new OperationResult(result, null));
            } catch (Throwable e) {
                e.printStackTrace();
                results.add(new OperationResult(null, e));
            }
        }
        return results;
    }

    private Method getMethod(Class<?> serviceClass, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
        return serviceClass.getMethod(methodName, parameterTypes);
    }
}