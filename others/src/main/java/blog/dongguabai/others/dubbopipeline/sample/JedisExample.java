package blog.dongguabai.others.dubbopipeline.sample;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * @author dongguabai
 * @date 2024-01-17 19:59
 */
public class JedisExample {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        Pipeline pipelined = jedis.pipelined();

        for (int i = 0; i < 10; i++) {
            pipelined.set("key" + i, "value" + i);
        }

        pipelined.sync();
    }
}