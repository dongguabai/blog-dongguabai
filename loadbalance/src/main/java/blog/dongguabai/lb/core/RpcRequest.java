package blog.dongguabai.lb.core;

import java.io.Serializable;

/**
 * 统一传输对象（让服务端知道当前要做什么）
 *
 * @author Dongguabai
 * @date 2018/11/1 16:16
 */
public class RpcRequest implements Serializable {

    private String className;
    private String methodName;
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
