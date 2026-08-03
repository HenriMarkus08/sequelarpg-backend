package com.rpg.sequelas_backend.controller;

import com.rpg.sequelasbackend.dto.GameActionMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameSocketController {

    // Recebe chamadas enviadas para: /app/room/{roomCode}/action
    // Retransmite a mensagem para todos inscritos em: /topic/room/{roomCode}
    @MessageMapping("/room/{roomCode}/action")
    @SendTo("/topic/room/{roomCode}")
    public GameActionMessage broadcastAction(
            @DestinationVariable String roomCode, 
            GameActionMessage message) {
        
        return message; // Retransmissão em tempo real
    }
}
