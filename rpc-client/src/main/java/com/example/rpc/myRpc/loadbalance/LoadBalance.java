package com.example.rpc.myRpc.loadbalance;

import java.util.List;

/**
 * @author lhf
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/6/1417:01
 */
public interface LoadBalance {
    String selectHost(List<String> repos);
}
