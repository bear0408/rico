package com.rico.comm.constant;

import lombok.experimental.UtilityClass;

/**
 * Feign常量类
 * @author rico
 * @Date 2020-7-1
 */
@UtilityClass
public class FeignConstant {

    /**
     * 网关
     */
    public final String MATE_CLOUD_GATEWAY = "CloudGatewayServer";

    /**
     * 系统服务
     */
    public final String MATE_CLOUD_SYSTEM = "CloudSysServer";

    /**
     * 认证服务
     */
    public final String MATE_CLOUD_UAA = "CloudUaaServer";

    /**
     * 消息生产者
     */
    public final String MATE_CLOUD_LOG_PRODUCER = "mate-log-producer";

    public final String DISCOVER_ECHO_SERVER = "DiscoverEchoServer2";
}
