package com.github.dongguabai.blog.others.dubbopipeline.api;

import java.io.Serializable;

/**
 * @author dongguabai
 * @date 2024-01-17 20:02
 */
public class Operation implements Serializable {

    private Class<?> serviceClass;

    private String methodName;

    private Class<?>[] parameterTypes;

    private Object[] args;

    public Operation setServiceClass(Class<?> serviceClass) {
        if (!serviceClass.isInterface()) {
            throw new IllegalArgumentException("serviceClass must be an interface");
        }
        this.serviceClass = serviceClass;
        return this;
    }

    public Operation setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public Operation setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public Operation setArgs(Object[] args) {
        this.args = args;
        return this;
    }

    public Class<?> getServiceClass() {
        return serviceClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }
}