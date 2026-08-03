package com.rpg.sequelas_backend.repository;

import com.rpg.sequelas_backend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
    Optional<Campaign> findByRoomCode(String roomCode);
}
