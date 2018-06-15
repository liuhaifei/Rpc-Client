package com.example.rpc.myRpc.loadbalance;

import java.util.List;

/**
 * @author lhf
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/6/1417:03
 */
public abstract class AbstaractLoadBalance implements LoadBalance{
    @Override
    public String selectHost(List<String> repos) {
        if(repos==null || repos.size()==0){
            return null;
        }
        if(repos.size()==1){
            return repos.get(0);
        }
        return doSelect(repos);
    }

    public abstract String doSelect(List<String> repos);
}
