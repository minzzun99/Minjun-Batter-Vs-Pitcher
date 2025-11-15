package com.woowaprecourse.minjun_baseball_game.repository;

import com.woowaprecourse.minjun_baseball_game.domain.Player;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByName(String name);
}
