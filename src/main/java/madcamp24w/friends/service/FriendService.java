package madcamp24w.friends.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.FriendRequestDTO;
import madcamp24w.friends.DTO.MemberInfoResponseDTO;
import madcamp24w.friends.entity.FriendStatus;
import madcamp24w.friends.entity.Friends;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.FriendRepository;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    public void createFriendship(FriendRequestDTO dto, HttpSession session){
        String nickname = (String) session.getAttribute("nickname");
        Member fromMember = memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("Does not exist"));
        Member toMember = memberRepository.findById(dto.getToId()).orElseThrow(()->new IllegalArgumentException("Does not exist"));

        if (friendRepository.existsByMemberAndFriend(fromMember, toMember)){
            throw new IllegalArgumentException("Already send Friend Request");
        }

        Friends friends = Friends.builder()
                .member(fromMember)
                .friend(toMember)
                .status(FriendStatus.PENDING)
                .build();
        friendRepository.save(friends);
    }

    public void becomeFriend(FriendRequestDTO dto, HttpSession session){
        String nickname = (String) session.getAttribute("nickname");
        Member fromMember = memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("Does not exist"));
        Member toMember = memberRepository.findById(dto.getToId()).orElseThrow(()->new IllegalArgumentException("Does not exist"));

        Friends friend = friendRepository.findFriendshipBetweenMembers(fromMember.getId(), toMember.getId()).orElseThrow(() -> new IllegalArgumentException("No pending friendship exists"));
        friend.setStatus(FriendStatus.FRIEND);
        friendRepository.save(friend);

    }

    public void becomeNoFriend(FriendRequestDTO dto, HttpSession session){
        String nickname = (String) session.getAttribute("nickname");
        Member fromMember = memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("Does not exist"));
        Member toMember = memberRepository.findById(dto.getToId()).orElseThrow(()->new IllegalArgumentException("Does not exist"));

        Friends friend = friendRepository.findFriendshipBetweenMembersAndEnd(fromMember.getId(), toMember.getId()).orElseThrow(() -> new IllegalArgumentException("No friendship exists"));
        friend.setStatus(FriendStatus.REJECTED);
        friendRepository.save(friend);
    }

    // 로그인한 멤버인지 체크 - session으로 get한게 memberId인지 체크하고 if로 상황에 따라서 리턴하는 Response다르게 하기
    public List<MemberInfoResponseDTO> getAllFriends(HttpSession session) {
        String nickname = (String) session.getAttribute("nickname");
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("No member found"));

        List<Friends> friends = friendRepository.findFriendsWithFriendState(member.getId());

        // 친구 정보를 매핑
        List<MemberInfoResponseDTO> friendList = friends.stream()
                .map(f -> {
                    if (f.getMember().getId().equals(member.getId())) {
                        // 현재 사용자가 memberId에 있는 경우 friendId의 정보를 반환
                       return MemberInfoResponseDTO.FriendTOInfo(f);
                    } else {
                        // 현재 사용자가 friendId에 있는 경우 memberId의 정보를 반환
                        return MemberInfoResponseDTO.FriendWhoGiveMePendingInfo(f);
                    }
                })
                .collect(Collectors.toList());

        return friendList;
    }

}
