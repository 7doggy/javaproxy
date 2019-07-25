package com.lichun.demo;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {

    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }

    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object result = methodProxy.invokeSuper(o, objects);
        after();
        return result;
    }

    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    public static void main(String[] args) {
        CglibProxy cp = new CglibProxy();
        Hello hello = cp.getProxy(HelloImpl.class);
        hello.sayHello("cglib");
    }
}
