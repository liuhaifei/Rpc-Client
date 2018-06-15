package com.example.rpc.myRpc;

import com.example.rpc.myRpc.zk.IServiceDiscovery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lhf
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/6/511:02
 */
public class RemoteInvocationHandler implements InvocationHandler {
//    private String host;
//    private int port;
//
//    public RemoteInvocationHandler(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }

    private IServiceDiscovery serviceDiscovery;

    private String version;

    public RemoteInvocationHandler(IServiceDiscovery serviceDiscovery,String version) {
        this.serviceDiscovery=serviceDiscovery;
        this.version=version;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //组装请求
        RpcRequest request=new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);


        //通过注册中心发现服务地址
        String serverAddress=serviceDiscovery.discovery(request.getClassName());
        //通过TCP协议进行传输
        TcpTransport tcpTransport=new TcpTransport(serverAddress);
        Object result=tcpTransport.send(request);

        return result;
    }
}
