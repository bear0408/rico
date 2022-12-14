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
package com.rico.uaa.config;

import com.rico.api.entity.MateUser;
import com.rico.comm.RedisService;
import com.rico.comm.constant.Oauth2Constant;
import com.rico.uaa.granter.CaptchaTokenGranter;
import com.rico.uaa.granter.SmsCodeTokenGranter;
import com.rico.uaa.granter.SocialTokenGranter;
import com.rico.uaa.service.impl.ClientDetailsServiceImpl;
import com.rico.uaa.service.impl.SingleLoginTokenServices;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;


import java.util.*;

/**
 * ???????????????????????????
 *
 * @author rico
 * @date 2019-10-11 23:21
 **/

@Order(2)
@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private  ClientDetailsServiceImpl clientService;
    @Autowired
    private  RedisConnectionFactory redisConnectionFactory;
    @Autowired
    protected   AuthenticationManager authenticationManager;
    @Autowired
    private  UserDetailsService userDetailsService;
    @Autowired
    private  RedisService redisService;
    @Autowired
    private  AuthRequestFactory factory;

    @Value("${mate.uaa.isSingleLogin:false}")
    private boolean isSingleLogin = false;


    /**
     * ??????token?????????redis???
     */
    @Bean
    public RedisTokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        DefaultTokenServices tokenServices = createDefaultTokenServices();
        // token?????????
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        // ???jwt????????????????????????????????????????????????
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        tokenServices.setTokenEnhancer(tokenEnhancerChain);
        // ??????tokenServices??????
        addUserDetailsService(tokenServices);
        endpoints
                .tokenGranter(tokenGranter(endpoints, tokenServices))
                .tokenServices(tokenServices)
                .accessTokenConverter(jwtAccessTokenConverter());

    }


    private void addUserDetailsService(DefaultTokenServices tokenServices) {
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
            tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        }
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // ????????????????????????
                .allowFormAuthenticationForClients()
                // spel????????? ?????????????????????/auth/token_key???????????????
                .tokenKeyAccess("isAuthenticated()")
                // spel????????? ???????????????????????????/auth/check_token???????????????
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clientService.setSelectClientDetailsSql(Oauth2Constant.SELECT_CLIENT_DETAIL_SQL);
        clientService.setFindClientDetailsSql(Oauth2Constant.FIND_CLIENT_DETAIL_SQL);
        clients.withClientDetails(clientService);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(Oauth2Constant.SIGN_KEY);
        return jwtAccessTokenConverter;
    }

    /**
     * ??????
     * ?????????????????????????????????????????????????????????????????????
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @return TokenGranter
     */
    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints, DefaultTokenServices tokenServices) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        // ?????????????????????
        granters.add(new SmsCodeTokenGranter(authenticationManager, tokenServices, endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), redisService));
        // ???????????????
        granters.add(new CaptchaTokenGranter(authenticationManager, tokenServices, endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), redisService));
        // ??????????????????
        granters.add(new SocialTokenGranter(authenticationManager, tokenServices, endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), redisService, factory));
        // ??????????????????
        granters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory()));
        return new CompositeTokenGranter(granters);
    }

    /**
     * ???????????????tokenServices
     *
     * @return DefaultTokenServices
     */
    private DefaultTokenServices createDefaultTokenServices() {
        DefaultTokenServices tokenServices = new SingleLoginTokenServices(isSingleLogin);
        tokenServices.setTokenStore(redisTokenStore());
        // ????????????Token
        tokenServices.setSupportRefreshToken(Boolean.TRUE);
        tokenServices.setReuseRefreshToken(Boolean.FALSE);
        tokenServices.setClientDetailsService(clientService);
        addUserDetailsService(tokenServices);
        return tokenServices;
    }

    /**
     * jwt token???????????????????????????
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new TokenEnhancer() {
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {

                // ?????????????????????map
                final Map<String, Object> additionMessage = new HashMap<>(2);
                // ??????????????????????????????????????????token
                if (oAuth2Authentication.getUserAuthentication() == null) {
                    return oAuth2AccessToken;
                }
                // ???????????????????????????
                MateUser user = (MateUser) oAuth2Authentication.getUserAuthentication().getPrincipal();

                // ????????????????????? ??????id??????jwt token???
                if (user != null) {
                    additionMessage.put(Oauth2Constant.MATE_USER_ID, String.valueOf(user.getId()));
                    additionMessage.put(Oauth2Constant.MATE_USER_NAME, user.getUsername());
                    additionMessage.put(Oauth2Constant.MATE_AVATAR, user.getAvatar());
                    additionMessage.put(Oauth2Constant.MATE_ROLE_ID, String.valueOf(user.getRoleId()));
                    additionMessage.put(Oauth2Constant.MATE_TYPE, user.getType());
                    additionMessage.put(Oauth2Constant.MATE_TENANT_ID, user.getTenantId());
                }
                ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionMessage);
                return oAuth2AccessToken;
            }
        };
    }
}
