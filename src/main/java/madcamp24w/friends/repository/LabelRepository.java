package madcamp24w.friends.repository;

import madcamp24w.friends.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findAllByBrain_Id(Long id);
}
