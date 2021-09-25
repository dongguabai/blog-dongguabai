package blog.dongguabai.bytetcc.sample.eurekaserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author Dongguabai
 * @description
 * @date 2021-09-26 00:41
 */
@EnableEurekaServer
@SpringBootApplication
public class SampleEurekaServerMain {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SampleEurekaServerMain.class).run(args);
    }

}
