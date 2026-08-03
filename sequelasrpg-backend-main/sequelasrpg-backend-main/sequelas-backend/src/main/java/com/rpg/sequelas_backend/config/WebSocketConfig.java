package com.rpg.sequelas_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Prefixo onde os clientes se inscrevem para escutar as atualizações
        config.enableSimpleBroker("/topic"); 
        
        // Prefixo para onde os clientes enviam mensagens/ações para o servidor
        config.setApplicationDestinationPrefixes("/app"); 
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Ponto de entrada de conexão WebSocket do frontend
        registry.addEndpoint("/ws-rpg")
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Mantém a conexão estável mesmo em redes restritas
    }
}
