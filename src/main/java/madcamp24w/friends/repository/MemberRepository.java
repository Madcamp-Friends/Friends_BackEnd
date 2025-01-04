package madcamp24w.friends.repository;

import madcamp24w.friends.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {


}
