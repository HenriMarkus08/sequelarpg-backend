package com.rpg.sequelasbackend.controller;

import com.rpg.sequelasbackend.dto.GameActionMessage;
import com.rpg.sequelasbackend.dto.ParticipantDto;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class GameSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    
    // Armazena participantes por sala: roomCode -> lista de participantes
    private final Map<String, Map<String, ParticipantDto>> roomParticipants = new ConcurrentHashMap<>();

    public GameSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room/{roomCode}/action")
    public void handleAction(@DestinationVariable String roomCode, GameActionMessage message) {
        String type = message.getType();
        String sender = message.getSender();

        // Processa registro ou atualização de participante
        if ("REGISTER".equals(type) || "UPDATE_PARTICIPANT".equals(type)) {
            // Extrai dados do payloadJson
            ParticipantDto participant = parseParticipant(message.getPayloadJson());
            if (participant != null) {
                // Adiciona ou atualiza na lista da sala
                Map<String, ParticipantDto> participants = roomParticipants.computeIfAbsent(roomCode, k -> new ConcurrentHashMap<>());
                participants.put(sender, participant);
                // Envia a lista atualizada para todos na sala
                broadcastParticipantList(roomCode);
            }
        } else {
            // Para outros tipos de mensagem (HP_UPDATE, CHAT_LOG, etc), apenas retransmite
            messagingTemplate.convertAndSend("/topic/room/" + roomCode, message);
        }
    }

    // Envia a lista de participantes para todos inscritos na sala
    private void broadcastParticipantList(String roomCode) {
        Map<String, ParticipantDto> participantsMap = roomParticipants.getOrDefault(roomCode, new ConcurrentHashMap<>());
        List<ParticipantDto> participantList = new ArrayList<>(participantsMap.values());

        GameActionMessage listMessage = new GameActionMessage();
        listMessage.setType("PARTICIPANT_LIST");
        listMessage.setSender("SERVIDOR");
        listMessage.setParticipants(participantList);

        messagingTemplate.convertAndSend("/topic/room/" + roomCode, listMessage);
    }

    // Método auxiliar para converter JSON em ParticipantDto
    private ParticipantDto parseParticipant(String json) {
        try {
            // Usando Jackson para parse (Spring já tem)
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(json, ParticipantDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
