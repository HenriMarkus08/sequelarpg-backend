package com.rpg.sequelasbackend.dto;

import lombok.Data;
import java.util.List;

@Data
public class GameActionMessage {
    private String type;        // Ex: "REGISTER", "UPDATE_PARTICIPANT", "PARTICIPANT_LIST", "HP_UPDATE", "CHAT_LOG"
    private String sender;      // Nome do jogador
    private String payloadJson; // Dados da ação em JSON
    private List<ParticipantDto> participants; // Usado para enviar a lista completa
}
