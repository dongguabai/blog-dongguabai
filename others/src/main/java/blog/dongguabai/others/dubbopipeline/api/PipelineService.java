package blog.dongguabai.others.dubbopipeline.api;

import java.util.List;

/**
 * @author dongguabai
 * @date 2024-01-17 20:01
 */
public interface PipelineService {

    List<OperationResult> executeBatch(Pipeline pipeline);
}