package com.rpg.sequelasbackend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roomCode; // Ex: "STRADH-9821"

    private String campaignName;

    @ElementCollection
    private List<String> historyLog = new ArrayList<>();
}