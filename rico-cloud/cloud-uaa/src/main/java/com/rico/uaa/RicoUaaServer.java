/*
 * Copyright 2019-2028 Beijing Daotiandi Technology Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Author: xuzhanfu (7333791@qq.com)
 */
package com.rico.uaa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * 认证启动类
 *
 * @author rico
 * @date 2019-10-09 15:06
 **/

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.rico.uaa.*","com.rico.comm.**","com.rico.api.**"})
public class RicoUaaServer {
    private static final Logger log = LoggerFactory.getLogger(RicoUaaServer.class);
    public static void main(String[] args) {
        SpringApplication.run(RicoUaaServer.class, args);
        log.info("+++++++++++++RicoUaaServer授权服务动成功！++++++++++");
    }
}
