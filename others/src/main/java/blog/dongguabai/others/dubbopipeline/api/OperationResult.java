package blog.dongguabai.others.dubbopipeline.api;

import java.io.Serializable;

/**
 * @author dongguabai
 * @date 2024-01-17 20:20
 */
public class OperationResult implements Serializable {

    private Object result;

    private Throwable exception;

    public OperationResult(Object result) {
        this.result = result;
    }

    public OperationResult(Exception exception) {
        this.exception = exception;
    }

    public boolean hasException() {
        return exception != null;
    }

    public OperationResult(Object result, Throwable exception) {
        this.result = result;
        this.exception = exception;
    }
}