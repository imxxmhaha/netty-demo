package cn.xxm.netty.websocket.server;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.WsContainerProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author xxm
 * @create 2018-10-09 2:24
 *
 * 监听springboot 容器  启动完毕,执行操作  启动netty
 */
@Configuration
@Slf4j
public class NettyBooterConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private WSServer wsServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            try {
                log.info("监听springboot容器启动完毕,  正在启动netty Socket....");
                wsServer.start();
            } catch (Exception e) {
                log.info("netty Socket  启动异常....");
                e.printStackTrace();
            }
        }
    }
}
