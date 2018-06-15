package com.example.rpc.myRpc;

import com.example.rpc.myRpc.zk.IServiceDiscovery;
import com.example.rpc.myRpc.zk.ServiceDiscoveryImpl;
import com.example.rpc.myRpc.zk.ZkConfig;

/**
 * @author lhf
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/6/511:23
 */
public class RpcClientDemo {

    public static void main(String[] args) {

        IServiceDiscovery serviceDiscovery=new ServiceDiscoveryImpl(ZkConfig.CONNNECTION_STR);

        RpcClientProxy proxy=new RpcClientProxy(serviceDiscovery);

        IHello iHello=proxy.clientProxy(IHello.class,"1.0");

        System.out.println(iHello.sayHello("xxxx"));
    }
}
