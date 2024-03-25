package io.github.dongguabai.blog.others.completablefutureprocess;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author dongguabai
 * @date 2024-03-13 20:17
 */
public class Task<T> {
    private CompletableFuture<T> future;
    private Function<T, T> function;
    private Task<T> dependency;
    private int executionCount = 1;

    public Task(Function<T, T> function) {
        this.function = function;
    }

    public Task(Function<T, T> function, int executionCount) {
        this.function = function;
        this.executionCount = executionCount;
    }

    public Task(Task<T> dependency, Function<T, T> function) {
        this.dependency = dependency;
        this.function = function;
    }

    public Task(Task<T> dependency, Function<T, T> function, int executionCount) {
        this.dependency = dependency;
        this.function = function;
        this.executionCount = executionCount;
    }

    public void execute() {
        for (int i = 0; i < executionCount; i++) {
            if (dependency != null) {
                future = dependency.getFuture().thenApplyAsync(function);
            } else {
                future = CompletableFuture.supplyAsync(() -> function.apply(null));
            }
        }
    }

    public CompletableFuture<T> getFuture() {
        return future;
    }
}