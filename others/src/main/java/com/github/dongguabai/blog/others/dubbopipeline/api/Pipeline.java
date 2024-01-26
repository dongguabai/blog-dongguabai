package com.github.dongguabai.blog.others.dubbopipeline.api;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dongguabai
 * @date 2024-01-17 20:05
 */
public class Pipeline {
    private List<Operation> operations = new ArrayList<>();

    public Pipeline addOperation(Operation operation) {
        operations.add(operation);
        return this;
    }

    public List<Operation> getOperations() {
        return operations;
    }
}