package com.rpg.sequelas_backend.controller;

import com.rpg.sequelas_backend.dto.GameActionMessage;
import com.rpg.sequelas_backend.dto.ParticipantDto;
import com.rpg.sequelas_backend.model.Participant;
import com.rpg.sequelas_backend.repository.ParticipantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class GameSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ParticipantRepository participantRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public GameSocketController(SimpMessagingTemplate messagingTemplate,
                                ParticipantRepository participantRepository,
                                ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.participantRepository = participantRepository;
        this.objectMapper = objectMapper;
    }

    @MessageMapping("/room/{roomCode}/action")
    public void handleAction(@DestinationVariable String roomCode, GameActionMessage message) throws Exception {
        String type = message.getType();

        if ("REGISTER".equals(type) || "UPDATE_PARTICIPANT".equals(type)) {
            ParticipantDto dto = objectMapper.readValue(message.getPayloadJson(), ParticipantDto.class);

            Participant participant = participantRepository
                    .findByRoomCodeAndUsername(roomCode, dto.getUsername())
                    .orElse(new Participant());

            participant.setRoomCode(roomCode);
            participant.setUsername(dto.getUsername());
            participant.setCharacterName(dto.getCharacterName());
            participant.setHpCurrent(dto.getHpCurrent());
            participant.setHpMax(dto.getHpMax());
            participant.setCa(dto.getCa());
            participant.setLastUpdate(System.currentTimeMillis());

            participantRepository.save(participant);

            broadcastParticipantList(roomCode);
        } else {
            messagingTemplate.convertAndSend("/topic/room/" + roomCode, message);
        }
    }

    private void broadcastParticipantList(String roomCode) {
        List<Participant> participants = participantRepository.findByRoomCode(roomCode);
        List<ParticipantDto> dtos = participants.stream()
                .map(p -> new ParticipantDto(
                        p.getUsername(),
                        p.getCharacterName(),
                        p.getHpCurrent(),
                        p.getHpMax(),
                        p.getCa()
                ))
                .collect(Collectors.toList());

        GameActionMessage listMessage = new GameActionMessage();
        listMessage.setType("PARTICIPANT_LIST");
        listMessage.setSender("SERVIDOR");
        listMessage.setParticipants(dtos);

        messagingTemplate.convertAndSend("/topic/room/" + roomCode, listMessage);
    }
}
