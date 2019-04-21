package com.futao.springmvcdemo.test;

import com.futao.springmvcdemo.model.entity.User;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * @author futao
 * Created on 2019-04-12.
 */
public class NormalTest {
    @Test
    public void test() {
        ClassPathXmlApplicationContext applicationContext =
                //通过xml加载容器
                new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = (User) applicationContext.getBean("user");
        System.out.println(user);
    }

    @Test
    public void test1() {
//        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        //1.创建一个applicationContext
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
//        2.设置需要激活的环境
        applicationContext.getEnvironment().setActiveProfiles("dev");
        //3.注册主配置类
        applicationContext.register(SpringConfig.class);
        //4.刷新容器
        applicationContext.refresh();

        for (String name : applicationContext.getBeanDefinitionNames()) {
//            System.out.println(name);
        }
        System.out.println("----" + applicationContext.getEnvironment().getProperty("result"));
        System.out.println("----" + applicationContext.getEnvironment().getProperty("dataSource.username"));
        System.out.println("----" + applicationContext.getEnvironment().getProperty("a"));
        applicationContext.close();
    }
}

//标记这是一个配置类，里面配置了很多Bean
@Import({UserFactoryBean.class})//快速导入一个组件
@Configuration
@ComponentScan(value = "com.futao.springmvcdemo.test")
@PropertySource("classpath:config/application-dev.properties")
class SpringConfig {

    /**
     * 如果存在多个UserBean，优先使用这个Bean实例
     *
     * @return
     */
    @Primary
    @Bean
    public User user() {
        return new User();
    }

    //    @Bean
    public UserFactoryBean userFactoryBean() {
        return new UserFactoryBean();
    }
}

class UserFactoryBean implements FactoryBean<User> {
    @Override
    public User getObject() throws Exception {
        return new User();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}


class MyImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {
    /**
     * 将需要添加到容器中的bean手动注册进来
     *
     * @param importingClassMetadata 当前类的注解信息
     * @param registry               spring注册的类
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition("user")) {
            //beanId  beanClass
            System.out.println("registerBeanDefinitions执行了....");
            registry.registerBeanDefinition("user", new RootBeanDefinition(User.class));
        }
    }
}


class MyImportSelector implements ImportSelector {
    /**
     * 返回需要注册的bean的全类名
     *
     * @param importingClassMetadata 当前标注@Import注解类的所有注解信息
     * @return 需要注册的bean的全类名
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{"com.futao.springmvcdemo.model.entity.Article", "com.futao.springmvcdemo.model.entity.Order"};
    }
}


@Component
class MyBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    /**
     * 在所有初始化方法执行之前执行
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization:" + beanName + "=====" + bean);
        return bean;
    }

    /**
     * 在所有初始化方法执行完成之后
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization:" + beanName + "=====" + bean);
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("获取SpringIOC容器");
    }

    User user;

    public User getUser() {
        return user;
    }

    @Autowired
    public void setUser(User user) {
        this.user = user;
    }

    public MyBeanPostProcessor() {
    }

    @Autowired
    public MyBeanPostProcessor(User user) {
        this.user = user;
    }
}