package madcamp24w.friends.repository;

import madcamp24w.friends.entity.Friends;
import madcamp24w.friends.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {
    boolean existsByMemberAndFriend(Member member, Member friend);
    Friends findFriendsByMemberAndFriend(Member member, Member friend);
}
