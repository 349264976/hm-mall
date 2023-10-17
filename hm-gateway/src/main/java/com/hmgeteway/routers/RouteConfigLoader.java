package com.hmgeteway.routers;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class RouteConfigLoader {
    private final NacosConfigManager configManager;
    private final static String  DATA_ID= "gateway-routes.json";
    private final static String GROUP= "DEFAULT_GROUP";

    private final RouteDefinitionWriter writer;

    private final Set<String> routids=new HashSet<>();
    String configInfo="";
    @PostConstruct
    public void initRouterConfiguration() throws NacosException {

         configInfo = configManager.getConfigService()
                .getConfigAndSignListener(DATA_ID, GROUP, 1000, new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return Executors.newSingleThreadExecutor();
                    }
                    @Override
                    public void receiveConfigInfo(String s) {
                        /**
                         * TODO
                         * 监听配置变更 ，更新一次配置
                         */
                        updateRouteConfigInfo(configInfo);
                    }
                });
        /**
         * 第一次启动拉去nacos的路由表
         */
        updateRouteConfigInfo(configInfo);
    }
    /**
     * 写入路由表
     */
  private void updateRouteConfigInfo(String configAndSignListener) {
    //1.解析路由信息
      List<RouteDefinition> routeDefinitions = JSONUtil.toList(configAndSignListener, RouteDefinition.class);
        //删除旧的路由
      routids.stream().forEach(routeDefinition ->{
          writer.delete(Mono.just(routeDefinition)).subscribe();
      });

      if (routeDefinitions==null||routeDefinitions.isEmpty()){
          //没有新的路由
          return;
      }
      //2.更新路由表
      for (int i = 0; i < routeDefinitions.size(); i++) {
          writer.save(Mono.just(routeDefinitions.get(i))).subscribe();
          routids.add(routeDefinitions.get(i).getId());
      }
    }

}
