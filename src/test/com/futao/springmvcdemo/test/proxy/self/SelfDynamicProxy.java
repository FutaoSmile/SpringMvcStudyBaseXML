package com.futao.springmvcdemo.test.proxy.self;

import com.futao.springmvcdemo.test.proxy.StaticProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author futao
 * Created on 2019-04-18.
 */
public class SelfDynamicProxy implements InvocationHandler {

    private Service service;

    public SelfDynamicProxy(ServiceImpl service) {
        this.service = service;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("执行前");
        Object o = method.invoke(service, args);
        System.out.println("执行后");
        return o;
    }

    public static void main(String args) {
        ServiceImpl s = new ServiceImpl();
        Service o = (Service) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Service.class}, new SelfDynamicProxy(s));
        o.sayHi();
    }


}


interface Service {
    String sayHi();
}

class ServiceImpl implements Service {
    @Override
    public String sayHi() {
        System.out.println("目标方法被调用了");
        return "I am serviceImpl";
    }
}

class business {
    public static void main(String[] args) {
        Service service = new ServiceImpl();
        StaticProxy staticProxy = new StaticProxy();
        System.out.println(staticProxy.sayHi());
    }
}