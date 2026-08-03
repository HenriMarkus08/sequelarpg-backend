package com.rpg.sequelas_backend.controller;

import com.rpg.sequelas_backend.model.Campaign;
import com.rpg.sequelas_backend.repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/campaigns")
@CrossOrigin(origins = "*")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping
    public ResponseEntity<List<Campaign>> getAllCampaigns() {
        List<Campaign> campaigns = campaignRepository.findAll();
        return ResponseEntity.ok(campaigns);
    }

    @PostMapping
    public ResponseEntity<Campaign> createCampaign(@RequestBody Campaign campaign) {
        Campaign savedCampaign = campaignRepository.save(campaign);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCampaign);
    }

    @GetMapping("/{roomCode}")
    public ResponseEntity<Campaign> getCampaignByRoomCode(@PathVariable String roomCode) {
        Optional<Campaign> optionalCampaign = campaignRepository.findByRoomCode(roomCode);
        
        if (optionalCampaign.isPresent()) {
            Campaign campaign = optionalCampaign.get();
            return ResponseEntity.ok(campaign);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
