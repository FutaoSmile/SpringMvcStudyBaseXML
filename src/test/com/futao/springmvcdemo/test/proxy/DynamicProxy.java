package com.futao.springmvcdemo.test.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author futao
 * Created on 2019-04-17.
 */
@Slf4j
public class DynamicProxy implements InvocationHandler {

    public <T> DynamicProxy(T target) {
        this.target = target;
    }

    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("调用目标方法之前");
        Object invoke = method.invoke(target, args);
        log.info("调用目标方法之后");
        return invoke;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    public static void main(String[] args) {
        Service service = new ServiceImpl();
        DynamicProxy dynamicProxy = new DynamicProxy(service);
        Service o = (Service) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), dynamicProxy);
        System.out.println(o.sayHi());


        DynamicProxy dynamicProxy1 = new DynamicProxy(new ServiceImpl());
//        dynamicProxy1.getProxy()


    }
}


