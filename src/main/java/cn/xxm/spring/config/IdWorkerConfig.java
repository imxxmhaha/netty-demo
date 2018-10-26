package cn.xxm.spring.config;

import cn.xxm.utils.IdWorker;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 将IdWorker  交给Spring管理
 */
@Configuration
@ConfigurationProperties(prefix = "idworker")
@Data
public class IdWorkerConfig {

    private Long datacenterId;

    private Long workerId;

    @Bean
    public IdWorker idWorker() {
        IdWorker idWorker = new IdWorker(workerId, datacenterId);

        return idWorker;
    }
}
