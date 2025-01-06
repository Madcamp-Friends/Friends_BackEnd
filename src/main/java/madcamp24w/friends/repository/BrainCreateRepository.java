package madcamp24w.friends.repository;

import madcamp24w.friends.entity.BrainCreate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrainCreateRepository extends JpaRepository<BrainCreate, Long> {

    Optional<BrainCreate> findByNickname(String nickname);

    boolean existsByNickname(String nickname);
}
