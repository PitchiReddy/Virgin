package com.vv.hbase.starter.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;


@Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
@ConditionalOnProperty(prefix="spring.data.hbase", value="quorum")
public class HbaseTemplateAutoConfiguration {

    private static final String ZK_QUORUM = "hbase.zookeeper.quorum";
   //private static final String ZK_PORT = "hbase.zookeeper.property.clientPort";
    
    @Autowired
    private HbaseProperties hbaseProperties;

    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
    	org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set(ZK_QUORUM, this.hbaseProperties.getQuorum());
       // configuration.set(ZK_PORT, this.hbaseProperties.getPort());
        return new HbaseTemplate(configuration);
    }
    @SuppressWarnings("deprecation")
	@Bean
    @ConditionalOnMissingBean(HBaseAdmin.class)
	public HBaseAdmin hbaseAdmin() throws Exception {
	 	org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", this.hbaseProperties.getQuorum());
		return new HBaseAdmin(configuration);
	}
}
