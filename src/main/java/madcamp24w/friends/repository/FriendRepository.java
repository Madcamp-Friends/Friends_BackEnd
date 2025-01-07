package madcamp24w.friends.repository;

import madcamp24w.friends.entity.Friends;
import madcamp24w.friends.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friends, Long> {
    boolean existsByMemberAndFriend(Member member, Member friend);
    Friends findFriendsByMemberAndFriend(Member member, Member friend);


    // 내가 친구로 신청한 친구
    @Query("Select f from Friends f where f.member.id=:mid and f.status='PENDING'")
    List<Friends> findFriendsWithPendingState(@Param("mid") Long mid);

    // 내가 받은 요청
    @Query("Select f from Friends f where f.friend.id=:mid and f.status='PENDING'")
    List<Friends> findFriendsWhoGiveMePending(@Param("mid") Long mid);

    @Query("Select f from Friends f where (f.member.id=:mid and f.status='FRIEND' OR f.friend.id=:mid and f.status='FRIEND')")
    List<Friends> findFriendsWithFriendState(@Param("mid") Long mid);

    // 친구로 되기 위한 조건 필터링 : 존재하면 친구 ??
    @Query("SELECT f FROM Friends f WHERE " +
            "(f.member.id = :memberId AND f.friend.id = :friendId AND f.status = 'PENDING') " +
            "OR (f.member.id = :friendId AND f.friend.id = :memberId AND f.status = 'PENDING')")
    Optional<Friends> findFriendshipBetweenMembers(@Param("memberId") Long memberId, @Param("friendId") Long friendId);


    @Query("SELECT f FROM Friends f WHERE " +
            "(f.member.id = :memberId AND f.friend.id = :friendId AND f.status = 'FRIEND') " +
            "OR (f.member.id = :friendId AND f.friend.id = :memberId AND f.status = 'FRIEND')")
    Optional<Friends> findFriendshipBetweenMembersAndEnd(@Param("memberId") Long memberId, @Param("friendId") Long friendId);

}
