package com.fingerchar.api.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fingerchar.api.annotation.support.LoginUserHandlerMethodArgumentResolver;
import com.fingerchar.api.constant.SysConfConstant;


@Configuration
public class FcWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private OptionalTokenInterceptor optionalTokenInterceptor;

    private static final String SWAGGER_PATH_PATTERN = "/swagger*/**";

    private static final String[] NOT_NEED_LOGIN_PATH = {
		 SysConfConstant.URL_PREFIX + "/user/login",
		 SysConfConstant.URL_PREFIX + "/user/listbyaddr",
		 SysConfConstant.URL_PREFIX + "/user/follows",
		 SysConfConstant.URL_PREFIX + "/user/collections",
		 SysConfConstant.URL_PREFIX + "/user/stat",
		 SysConfConstant.URL_PREFIX + "/user/info",
		 SysConfConstant.URL_PREFIX + "/user/onsales",
		 SysConfConstant.URL_PREFIX + "/user/like",
		 SysConfConstant.URL_PREFIX + "/user/created",
		 SysConfConstant.URL_PREFIX + "/user/following",
 		 SysConfConstant.URL_PREFIX + "/user/follows",
 		 SysConfConstant.URL_PREFIX + "/user/listcontract",
 		 SysConfConstant.URL_PREFIX + "//follow/match",
         SysConfConstant.URL_PREFIX + "/category/list",
         SysConfConstant.URL_PREFIX + "/contract/info",
         SysConfConstant.URL_PREFIX + "/contract/getinfo",
         SysConfConstant.URL_PREFIX + "/contract/stat",
         SysConfConstant.URL_PREFIX + "/contract/list",
         SysConfConstant.URL_PREFIX + "/contract/listbyaddr",
         SysConfConstant.URL_PREFIX + "/contract/onsales",
         SysConfConstant.URL_PREFIX + "/contract/collections",
         SysConfConstant.URL_PREFIX + "/contract/listitems",
         SysConfConstant.URL_PREFIX + "/paytoken/list",
         SysConfConstant.URL_PREFIX + "/home/list",
         SysConfConstant.URL_PREFIX + "/like/listuserlike",
         SysConfConstant.URL_PREFIX + "/config/fetch",
         SysConfConstant.URL_PREFIX + "/home/search",
         SysConfConstant.URL_PREFIX + "/home/searchuser",
         SysConfConstant.URL_PREFIX + "/nft/owners",
         SysConfConstant.URL_PREFIX + "/nft/bids",
         SysConfConstant.URL_PREFIX + "/nft/history",
         SysConfConstant.URL_PREFIX + "/nft/detail",
         SysConfConstant.URL_PREFIX + "/nft/activebids",
         SysConfConstant.URL_PREFIX + "/nft/getmedia",
         SysConfConstant.URL_PREFIX + "/nft/getroyalties",
         SysConfConstant.URL_PREFIX + "/order/get",
         SysConfConstant.URL_PREFIX + "/dapp/node/{env}",
         SysConfConstant.URL_PREFIX + "/dapp/wallet",
         SysConfConstant.URL_PREFIX + "/notices/countunread",
         SysConfConstant.URL_PREFIX + "/notices/list",
         SysConfConstant.URL_PREFIX + "/notices/count",
    };

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginUserHandlerMethodArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //指定必选token的接口（需要登录）
        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns(NOT_NEED_LOGIN_PATH)
                .excludePathPatterns(SWAGGER_PATH_PATTERN);
        //可选token接口（可不登录）
        registry.addInterceptor(optionalTokenInterceptor)
                .excludePathPatterns(SWAGGER_PATH_PATTERN);
    }
}
