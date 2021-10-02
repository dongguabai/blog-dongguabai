package blog.dongguabai.bytetcc.sample.provider.main;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * 按请求粒度负载均衡(使用MongoDB存储事务日志):需引入SpringCloudConfiguration; <br />
 * 按事务粒度负载均衡(使用文件系统存储事务日志):需引入SpringCloudSecondaryConfiguration;
 */
@ImportResource({ "classpath:bytetcc-supports-springcloud.xml" })
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "blog.dongguabai.bytetcc.sample.provider")
public class SampleProviderMain {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SampleProviderMain.class).bannerMode(Banner.Mode.OFF).web(true).run(args);
		System.out.println("springcloud-sample-provider started!");
	}

}
