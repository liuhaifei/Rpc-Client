package com.example.rpc.myRpc.loadbalance;

import java.util.List;
import java.util.Random;

/**
 * @author lhf
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/6/1417:09
 */
public class RandomLoadBalance extends AbstaractLoadBalance {
    @Override
    public String doSelect(List<String> repos) {
        int size=repos.size();
        Random random=new Random();
        return repos.get(random.nextInt(size));
    }
}
