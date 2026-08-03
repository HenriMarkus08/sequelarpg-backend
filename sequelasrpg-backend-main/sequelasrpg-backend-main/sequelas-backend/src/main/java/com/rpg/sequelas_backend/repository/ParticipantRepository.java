package com.rpg.sequelas_backend.repository;

import com.rpg.sequelas_backend.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findByRoomCode(String roomCode);
    Optional<Participant> findByRoomCodeAndUsername(String roomCode, String username);
}
