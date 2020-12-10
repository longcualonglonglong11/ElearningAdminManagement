package com.myclass.config.socket;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.type.SimpleType;

@Configuration
//@ComponentScan(basePackages = "com.myclass.config.socket")
//@Order(1)
public class WebSocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	@Override
	protected boolean sameOriginDisabled() {
		return true;
	}

	@Override

	protected void configureInbound(MessageSecurityMetadataSourceRegistry message) {

		message
		.simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.DISCONNECT, SimpMessageType.OTHER)
		.permitAll()
		.simpDestMatchers("/app/**")
		.hasRole("ADMIN")
		.simpSubscribeDestMatchers("/topic/**")
		.hasRole("ADMIN")
		.anyMessage()
		.denyAll();

	}

}
