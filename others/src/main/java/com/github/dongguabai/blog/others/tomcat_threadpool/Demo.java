package blog.others.tomcat_threadpool;

import org.apache.tomcat.util.threads.TaskQueue;
import org.apache.tomcat.util.threads.TaskThreadFactory;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author dongguabai
 * @date 2023-11-18 22:04
 */
public class Demo {

    public static void main(String[] args) {
        //无界队列
        TaskQueue taskqueue = new TaskQueue();
        TaskThreadFactory tf = new TaskThreadFactory("dongguabai_blog" + "-exec-", false, 2);
        final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 2, 60, TimeUnit.SECONDS, taskqueue, tf);
        taskqueue.setParent(executor);
        observe(executor);
        while (true) {
            executor.execute(new Runnable() {
                public void run() {
                    excuteForever();
                }
            });
        }

    }

    private static void observe(final ThreadPoolExecutor executor) {
        Runnable task = new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(new Date().toLocaleString() + "->" + executor.getQueue().size());
                }
            }
        };
        new Thread(task).start();
    }

    public static void excuteForever() {
        while (true) {
        }
    }
}