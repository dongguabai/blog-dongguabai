package com.github.dongguabai.blog.others.dubbopipeline.sample;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @author dongguabai
 * @date 2024-01-17 19:59
 */
public class JedisExample {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        Pipeline pipeline = jedis.pipelined();
        pipeline.set("name", "John");
        pipeline.set("age", "25");
        pipeline.incr("visits");
        //执行 pipeline 中的所有命令
        pipeline.sync();
    }
}