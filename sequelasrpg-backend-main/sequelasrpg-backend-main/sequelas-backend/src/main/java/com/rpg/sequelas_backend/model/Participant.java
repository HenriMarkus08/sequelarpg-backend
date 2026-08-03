package com.rpg.sequelas_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String roomCode;

    @Column(nullable = false)
    private String username;

    private String characterName;

    private int hpCurrent;

    private int hpMax;

    private int ca;

    private long lastUpdate;
}
