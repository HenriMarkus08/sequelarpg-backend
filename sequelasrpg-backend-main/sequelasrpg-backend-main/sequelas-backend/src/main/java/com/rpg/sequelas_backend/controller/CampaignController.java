package com.rpg.sequelas_backend.controller;

import com.rpg.sequelasbackend.model.Campaign;
import com.rpg.sequelasbackend.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "*") // Permite chamadas de qualquer frontend (como Netlify ou localhost)
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    // Criar uma nova sala com código único
    @PostMapping("/create")
    public ResponseEntity createCampaign(@RequestParam String name) {
        Campaign campaign = new Campaign();
        campaign.setCampaignName(name);
        
        // Gera um código único simples de 6 caracteres em maiúsculas (ex: A1B2C3)
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        campaign.setRoomCode(code);
        
        Campaign saved = campaignRepository.save(campaign);
        return ResponseEntity.ok(saved);
    }

    // Buscar dados de uma sala existente pelo código
    @GetMapping("/{code}")
    public ResponseEntity getCampaign(@PathVariable String code) {
        return campaignRepository.findByRoomCode(code.toUpperCase())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
