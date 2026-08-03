package com.rpg.sequelas_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class GameActionMessage {
    private String type;
    private String sender;
    private String payloadJson;
    private List<ParticipantDto> participants;
}
