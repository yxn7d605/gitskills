package jndi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JNDISpringMain {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-jndi.xml");
        Config config = (Config) context.getBean("zookeeperConfig");
        System.out.println(config.getName() + "<>" + config.getSources());
    }
}
