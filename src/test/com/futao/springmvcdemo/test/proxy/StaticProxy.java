package com.futao.springmvcdemo.test.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author futao
 * Created on 2019-04-17.
 */
@Slf4j
public class StaticProxy implements Service {

    @Override
    public String sayHi() {
        log.info("调用目标方法之前");
        String sayHi = service.sayHi();
        log.info("调用目标方法之后");
        return sayHi;
    }

    private ServiceImpl service;

    public StaticProxy() {
        service = new ServiceImpl();
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