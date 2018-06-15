package com.example.rpc.myRpc.zk;

import com.example.rpc.myRpc.loadbalance.LoadBalance;
import com.example.rpc.myRpc.loadbalance.RandomLoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lhf
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/6/1416:49
 */
public class ServiceDiscoveryImpl implements IServiceDiscovery {
    List<String> repos=new ArrayList<>();

    private String address;

    private CuratorFramework curatorFramework;

    public ServiceDiscoveryImpl(String address){
        this.address=address;
        curatorFramework= CuratorFrameworkFactory
                          .builder().connectString(address)
                          .sessionTimeoutMs(4000)
                          .retryPolicy(new ExponentialBackoffRetry(1000,3))
                           .build();
        curatorFramework.start();

    }



    @Override
    public String discovery(String serviceName) {
        String path=ZkConfig.ZK_REGISTER_PATH+"/"+serviceName;

        try {
            repos=curatorFramework.getChildren().forPath(path);
        }catch (Exception e){
            throw new RuntimeException("获取子节点异常:",e);
        }
        //动态发现服务节点变化
        registerWatcher(path);

        //随机获取节点
        LoadBalance loadBalance=new RandomLoadBalance();

        //返回调用的服务地址
        return loadBalance.selectHost(repos);
    }

    public void registerWatcher(final String path)  {

        PathChildrenCache pathChildrenCache=new PathChildrenCache(curatorFramework,path,true);
        PathChildrenCacheListener pathChildrenCacheListener=
                            new PathChildrenCacheListener() {
                                @Override
                                public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                                    repos=curatorFramework.getChildren().forPath(path);
                                }
                            };
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);

        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
