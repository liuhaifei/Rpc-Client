package com.example.rpc.myRpc;

import com.example.rpc.myRpc.zk.IServiceDiscovery;

import java.lang.reflect.Proxy;

/**
 * @author lhf
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/6/510:56
 */
public class RpcClientProxy {

    private  IServiceDiscovery serviceDiscovery;
    public RpcClientProxy(IServiceDiscovery serviceDiscovery){
        this.serviceDiscovery=serviceDiscovery;
    }

    public <T> T clientProxy(final Class<T> interfceClass,String version){

        return (T) Proxy.newProxyInstance(interfceClass.getClassLoader(),
                new Class[]{interfceClass},new RemoteInvocationHandler(serviceDiscovery,version));
    }
}
