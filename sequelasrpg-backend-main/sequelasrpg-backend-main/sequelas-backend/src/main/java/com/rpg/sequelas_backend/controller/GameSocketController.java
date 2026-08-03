package com.rpg.sequelas_backend.controller;

import com.rpg.sequelas_backend.dto.GameActionMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameSocketController {

    @MessageMapping("/game/{roomCode}")
    @SendTo("/topic/room/{roomCode}")
    public GameActionMessage handleGameAction(@DestinationVariable String roomCode, @Payload GameActionMessage message) {
        message.setRoomCode(roomCode);
        return message;
    }
}
