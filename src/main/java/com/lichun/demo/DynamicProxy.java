package com.lichun.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxy implements InvocationHandler {
    private Object object;

    public DynamicProxy(Object object) {
        this.object = object;
    }
    private void before() {
        System.out.println("before");
    }

    private void after() {
        System.out.println("after");
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(object, args);
        after();
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(),
                this);
    }

    public static void main(String[] args) {
        Hello h = new HelloImpl();
        DynamicProxy dp = new DynamicProxy(h);
        Hello hello = dp.getProxy();
        hello.sayHello("12345");
    }
}
