package cn.yog.core.config;

import com.google.common.collect.Lists;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterConfig {

    private int          soTimeout         = 1500;
    private int          connectionTimeout = 1000;
    private int          maxAttempts       = 3;
    private List<String> nodes             = Lists.newArrayList();


//    @Bean
//    public JedisCluster jedisCluster() {
//        Set<HostAndPort> hostAndPortSet = new HashSet<>();
//        for (String node : nodes) {
//            hostAndPortSet.add(HostAndPort.parseString(node));
//        }
//        return new JedisCluster(hostAndPortSet, connectionTimeout, soTimeout, new GenericObjectPoolConfig());
//    }

    @Bean
    public Jedis jedis(){
        HostAndPort hostAndPort = new HostAndPort("127.0.0.1",6379);
        if(!CollectionUtils.isEmpty(nodes)){
            hostAndPort = HostAndPort.parseString(nodes.get(0));
        }
        return new Jedis(hostAndPort.getHost(),hostAndPort.getPort(), connectionTimeout, soTimeout);
    }


    public Integer getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(Integer soTimeout) {
        this.soTimeout = soTimeout;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }
}
