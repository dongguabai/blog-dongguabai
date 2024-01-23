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
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

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
        List<CompletableFuture<OperationResult>> futures = pipeline.getOperations().stream()
                .map(operation -> CompletableFuture.supplyAsync(() -> {
                    Object service = applicationContext.getBean(operation.getServiceClass());
                    try {
                        Method method = getMethod(service.getClass(), operation.getMethodName(), operation.getParameterTypes());
                        Object result = method.invoke(service, operation.getArgs());
                        return new OperationResult(result, null);
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return new OperationResult(null, e);
                    }
                }))
                .collect(Collectors.toList());
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    private Method getMethod(Class<?> serviceClass, String methodName, Class<?>[] parameterTypes) throws NoSuchMethodException {
        return serviceClass.getMethod(methodName, parameterTypes);
    }
}