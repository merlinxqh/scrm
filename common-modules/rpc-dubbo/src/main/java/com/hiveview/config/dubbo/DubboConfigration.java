package com.hiveview.config.dubbo;

import com.alibaba.dubbo.config.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leo on 2018/2/5.
 * dubbo 配置
 */
@Configuration
public class DubboConfigration {

    @Value("${dubbo.application.name}")
    private String appName;

    @Value("${dubbo.registry.port}")
    private int registryPort;

    @Value("${dubbo.registry.address.client}")
    private String clientAddress;

    @Value("${dubbo.registry.address}")
    private String address;

    @Value("${dubbo.consumer.check}")
    private boolean consumerCheck;

    @Value("${dubbo.provider.retries}")
    private int retries;

    @Value("${dubbo.provider.timeout}")
    private int timeout;

    @Value("${dubbo.provider.delay}")
    private int delay;

    private static final String PROTOCOL_NAME = "dubbo";

    private static final String PROTOCOL_ZOOKEEPER = "zookeeper";

    private static final String ZOOKEEPER_SERVICE = "zookeeperService";

    private static final String ZOOKEEPER_CLIENT = "zookeeperClient";

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig config = new ApplicationConfig();
        config.setName(appName);
        return config;
    }

    @Bean
    public ProtocolConfig protocolConfig(){
        ProtocolConfig config = new ProtocolConfig();
        config.setName(PROTOCOL_NAME);
        config.setPort(registryPort);
        return config;
    }

    @Bean
    public RegistryConfig zookeeperService(){
        RegistryConfig config = new RegistryConfig();
        config.setProtocol(PROTOCOL_ZOOKEEPER);
        config.setId(ZOOKEEPER_SERVICE);
        config.setAddress(address);
        return config;
    }

    @Bean
    public RegistryConfig zookeeperClient(){
        RegistryConfig config = new RegistryConfig();
        config.setProtocol(PROTOCOL_ZOOKEEPER);
        config.setId(ZOOKEEPER_CLIENT);
        config.setAddress(clientAddress);
        config.setDefault(false);
        config.setRegister(true);//是否注册
        return config;
    }

    /**
     * 官方推荐 Provider 上尽量多配置 Consumer 端属性
     *  1. timeout 方法调用超时
     *  2. retries 失败重试次数，缺省是 2
     *  3. loadbalance 负载均衡算法 缺省是随机 random。还可以有轮询 roundrobin、最不活跃优先 leastactive
     *  4. actives 消费者端，最大并发调用限制，即当 Consumer 对一个服务的并发调用到上限后，
     *     新调用会 Wait 直到超时 在方法上配置 dubbo:method 则并发限制针对方法，
     *     在接口上配置 dubbo:service，则并发限制针对服务
     * @return
     */
    @Bean
    public ProviderConfig providerConfig(){
        ProviderConfig config = new ProviderConfig();
        config.setRetries(retries);
        config.setDelay(delay);
        config.setTimeout(timeout);
        config.setVersion("1.0.0");
        return config;
    }


    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
        /**
         * 这个参数为false
         */
        consumerConfig.setVersion("1.0.0");
        consumerConfig.setCheck(consumerCheck);
        return consumerConfig;
    }
}
