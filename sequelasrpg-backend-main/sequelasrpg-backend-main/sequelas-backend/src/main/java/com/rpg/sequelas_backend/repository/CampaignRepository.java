package com.rpg.sequelas_backend.repository;

import com.rpg.sequelas_backend.model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CampaignRepository extends JpaRepository {
    
    // O Spring Data JPA cria a consulta SQL automaticamente baseada no nome do método!
    Optional findByRoomCode(String roomCode);
}
