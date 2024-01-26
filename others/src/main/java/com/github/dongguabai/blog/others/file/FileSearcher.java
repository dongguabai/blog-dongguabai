package com.github.dongguabai.blog.others.file;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dongguabai
 * @date 2023-11-24 13:59
 */
public class FileSearcher {
    public static void main(String[] args) {
        Path startingDir = Paths.get("/");
        String pattern = "it";

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(startingDir)) {
            AtomicInteger count = new AtomicInteger();
            stream.forEach(path -> {
                try {
                    Files.walk(path)
                            .parallel() // 使用并行流
                            .filter(p -> p.getFileName().toString().contains(pattern))
                            .forEach(p -> {
                                System.out.println(p.toAbsolutePath());
                                count.incrementAndGet();
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            System.out.println("Found " + count.get() + " files.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}