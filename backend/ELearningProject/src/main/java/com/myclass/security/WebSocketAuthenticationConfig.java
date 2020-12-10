//package com.myclass.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketAuthenticationConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//	private static String[] authorities = new String[] { "VIEW_SCRIPT_TAB", "VIEW_CREDS_TAB" };
//
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry config) {
//		config.enableSimpleBroker("/topic");
//		config.setApplicationDestinationPrefixes("/app");
//	}
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		registry.addEndpoint("/stomp").setAllowedOrigins("*").withSockJS();
//	}
//
//	@Override
//	protected void configureInbound(MessageSecurityMetadataSourceRegistry message) {
//		message.nullDestMatcher()
//		.authenticated()
//		.simpDestMatchers("/app/**")
//		.hasAnyRole("NEVER")
//				.simpSubscribeDestMatchers("/topic/**")
//				.permitAll()
//				.anyMessage()
//				.denyAll();
//	}
//
//	@Override
//	protected boolean sameOriginDisabled() {
//		return true;
//	}
//}