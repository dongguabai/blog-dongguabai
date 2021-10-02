package blog.dongguabai.bytetcc.sample.consumer.consumer.main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * 按请求粒度负载均衡(使用MongoDB存储事务日志):需引入SpringCloudConfiguration; <br />
 * 按事务粒度负载均衡(使用文件系统存储事务日志):需引入SpringCloudSecondaryConfiguration;
 */
@ImportResource({ "classpath:bytetcc-supports-springcloud.xml", "classpath:spring-mybatis.xml" })
@EnableDiscoveryClient
@EnableEurekaClient
@EnableFeignClients("blog.dongguabai.bytetcc.sample.consumer.feign")
@SpringBootApplication(scanBasePackages = "blog.dongguabai.bytetcc.sample.consumer")
@EnableCircuitBreaker
@EnableHystrix
@EnableHystrixDashboard
public class SampleConsumerMain {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SampleConsumerMain.class).bannerMode(Banner.Mode.OFF).web(true).run(args);
		System.out.println("springcloud-sample-consumer started!");
	}

}
