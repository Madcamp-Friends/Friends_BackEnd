package madcamp24w.friends.repository;

import madcamp24w.friends.entity.Friends;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Long, Friends> {
}
