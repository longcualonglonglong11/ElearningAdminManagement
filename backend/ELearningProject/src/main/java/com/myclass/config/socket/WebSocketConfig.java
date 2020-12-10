package com.myclass.config.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.myclass.constant.ObjectConstants;

import io.jsonwebtoken.Jwts;

@Configuration
@EnableWebSocketMessageBroker

public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/socket").setAllowedOrigins("*").withSockJS();

	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// Requested break point
		registry.setApplicationDestinationPrefixes("/app");
		// Received break point
		registry.enableSimpleBroker("/topic");
	}

	@Autowired
	protected WebSocketAuthenticatorService webSocketAuthenticatorService;

//
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(new ChannelInterceptor() {
			@Override
			public Message<?> preSend(Message<?> message, MessageChannel channel) {
				final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message,
						StompHeaderAccessor.class);
				if (StompCommand.CONNECT.equals(accessor.getCommand())) {
					String token = accessor.getFirstNativeHeader("Authorization").replace("Bearer ", "");
					String email = Jwts
							.parser()
							.setSigningKey(ObjectConstants.API_KEY)
							.parseClaimsJws(token)
							.getBody()
							.getSubject();
					Authentication authentication = webSocketAuthenticatorService.getAuthenticatedOrFail(email);

					SecurityContextHolder.getContext().setAuthentication(authentication);

					accessor.setUser(authentication);
					System.out.println("User " + accessor.getUser());

				}
				return message;
			}
		});
	}

}
