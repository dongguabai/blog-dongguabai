package io.github.dongguabai.blog.others.completablefutureprocess;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author dongguabai
 * @date 2024-03-13 20:17
 */
public class Job<T> {
    private List<Task<T>> tasks;

    public Job(List<Task<T>> tasks) {
        this.tasks = tasks;
    }

    public void execute() {
        tasks.forEach(Task::execute);
        CompletableFuture.allOf(tasks.stream().map(Task::getFuture).toArray(CompletableFuture[]::new)).join();
    }
}