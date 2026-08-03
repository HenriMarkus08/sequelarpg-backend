package com.rpg.sequelas_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantDto {
    private String username;
    private String characterName;
    private int hpCurrent;
    private int hpMax;
    private int ca;
}
