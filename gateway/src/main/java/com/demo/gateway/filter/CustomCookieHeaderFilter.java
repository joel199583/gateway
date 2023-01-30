package com.demo.gateway.filter;

import java.util.Base64;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomCookieHeaderFilter implements GatewayFilterFactory<CustomCookieHeaderFilter.Config> { 
	
	private static final Logger log = LoggerFactory.getLogger(CustomCookieHeaderFilter.class);
			
	private static final String dtoken = "Q0FLRVBIUD0wYnZjdGMzYjdnNzV0Y2VxbjVrdHNkcmMxMTsgbXRfdXNlcltVc2VySURdPTQ4MTI4Mzc7IEFVVEg9RU1BSUw9aW90X2hlbHBkZXNrQGN1c3RvbXMuZ292LnR3JkNIQUxMRU5HRT1NdjhVTlh4VUVmTDMyZWhEZjRUdg";
	
	@Override
	public Class<Config> getConfigClass() {
		return Config.class;
	}

	@Override
	public Config newConfig() {
		Config c = new Config();
		return c;
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String token = 
					CollectionUtils.isNotEmpty(exchange.getRequest().getQueryParams().get("token")) ?
					exchange.getRequest().getQueryParams().get("token").get(0) : 
					dtoken;
			
			log.info("token : " + token);
			
			byte[] decodedBytes = Base64.getDecoder().decode(token);
			String cookie = new String(decodedBytes);
			
			log.info("cookie content : " + cookie);
			ServerHttpRequest request = exchange.getRequest()
	                .mutate()
	                .header("cookie", cookie)
	                .header("x-requested-with", "XMLHttpRequest")
	                .build();
	        return chain.filter(exchange.mutate().request(request).build());
		};
	}

}
