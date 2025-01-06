package madcamp24w.friends.repository;

import madcamp24w.friends.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}
