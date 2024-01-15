package dongguabai.spring.boot.demo.sb1;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Log4j
public class TaskController {

    private final Executor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadFactory() {
                private final AtomicInteger counter = new AtomicInteger();

                @Override
                public Thread newThread(Runnable r) {
                    return new Thread(r, "TaskProcessor-" + counter.incrementAndGet());
                }
            });

    // 创建处理器链
    private final ProcessorChain processorChain = new ProcessorChain(
            Arrays.asList(new Processor1(), new Processor2(), new Processor3(), new Processor4())
    );

    @PostMapping("/task")
    public void handleTask(@RequestBody Task task) {
        // 使用处理器链来处理任务
        processorChain.process(task, executor);
    }
}
@Log4j
class Task {
    // 你的任务类
    private final String id;  // 唯一的 ID
    private final AtomicBoolean cancelled = new AtomicBoolean(false);  // 是否被取消

    public Task(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public boolean isCancelled() {
        return cancelled.get();
    }

    public void cancel() {
        cancelled.set(true);
    }
}
@Log4j
abstract class Processor {

    public CompletableFuture<Task> process(Task task, Executor executor) {
        return CompletableFuture.supplyAsync(() -> doProcess(task), executor);
    }

    protected abstract Task doProcess(Task task);
}
@Log4j
class Processor1 extends Processor {
    @Override
    protected Task doProcess(Task task) {
        // 你的处理逻辑
        log.info("Processor1 is processing");
        ProcessorChain.sleep(6);
        log.info("Processor1 end");
        if (task.isCancelled()) {
            throw new CancellationException("Task was cancelled");
        }
        return task;
    }
}
@Log4j
class Processor2 extends Processor {
    @Override
    protected Task doProcess(Task task) {
        // 你的处理逻辑
        log.info("Processor2 is processing");
        ProcessorChain.sleep(2);
        log.info("Processor2 end");
        if (task.isCancelled()) {
            throw new CancellationException("Task was cancelled");
        }
        return task;
    }
}
@Log4j
class Processor3 extends Processor {
    @Override
    protected Task doProcess(Task task) {
        // 你的处理逻辑
        log.info("Processor3 is processing");
        ProcessorChain.sleep(3);
        log.info("Processor3 end");
        if (task.isCancelled()) {
            throw new CancellationException("Task was cancelled");
        }
        return task;
    }
}
@Log4j
class Processor4 extends Processor {
    @Override
    protected Task doProcess(Task task) {
        // 你的处理逻辑
        log.info("Processor4 is processing");
        ProcessorChain.sleep(4);
        log.info("Processor4 end");
        if (task.isCancelled()) {
            throw new CancellationException("Task was cancelled");
        }
        return task;
    }
}
@Log4j
class ProcessorChain {
    private final List<Processor> processors;

    public ProcessorChain(List<Processor> processors) {
        this.processors = processors;
    }

    public CompletableFuture<Void> process(Task task, Executor executor) {
        CompletableFuture<Task> future = CompletableFuture.completedFuture(task);
        for (Processor processor : processors) {
            log.info(future);
            future = future.thenComposeAsync(t -> processor.process(t, executor), executor);
            log.info(future);
          // future.thenComposeAsync(t -> processor.process(t, executor), executor);
        }
        return future.thenAccept(result ->log.info("处理完成，结果是：" + result));
    }

    public static void sleep(Integer i){
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}