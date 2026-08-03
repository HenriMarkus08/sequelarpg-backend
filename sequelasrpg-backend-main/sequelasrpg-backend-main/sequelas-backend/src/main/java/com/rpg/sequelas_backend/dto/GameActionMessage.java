package com.rpg.sequelas_backend.dto;

import lombok.Data;

@Data
public class GameActionMessage {
    private String type;        // Ex: "HP_UPDATE", "DICE_ROLL", "CHAT_LOG"
    private String sender;      // Nome do jogador ou "MESTRE"
    private String payloadJson; // Dados da ação em formato JSON genérico
}
