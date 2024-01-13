package demo.sb1;

import lombok.extern.log4j.Log4j;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Log4j
class ProcessorChainTest {

    public static void main(String[] args) {
        Executor executor = Executors.newCachedThreadPool();
        ProcessorChain processorChain = new ProcessorChain(Arrays.asList(new Processor1(), new Processor2(), new Processor3(), new Processor4()));
        Task task = new Task("1");
        CompletableFuture<Void> exceptionally = processorChain.process(task, executor).exceptionally(ex -> {
            System.out.println("处理过程中出现错误：" + ex.getMessage());
            return null;
        });
        log.info("处理完成");
    }

}