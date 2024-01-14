package dongguabai.spring.boot.demo.sb1;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Hello world!
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(App.class);
        application.addInitializers(new MyApplicationContextInitializer());
        ApplicationContext context = application.run(args);
    }

    static class MyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            if (applicationContext.getBeanFactory() instanceof DefaultListableBeanFactory) {
                ((DefaultListableBeanFactory) applicationContext.getBeanFactory()).setAllowBeanDefinitionOverriding(false);
            }
        }
    }
}
