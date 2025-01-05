package madcamp24w.friends.service;

import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.FriendRequestDTO;
import madcamp24w.friends.entity.FriendStatus;
import madcamp24w.friends.entity.Friends;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.FriendRepository;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;
    public void createFriendship(FriendRequestDTO dto){
        Member fromMember = memberRepository.findById(dto.getFromId()).orElseThrow(()->new IllegalArgumentException("Does not exist"));
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

    public void becomeFriend(FriendRequestDTO dto){
        Member fromMember = memberRepository.findById(dto.getFromId()).orElseThrow(()->new IllegalArgumentException("Does not exist"));
        Member toMember = memberRepository.findById(dto.getToId()).orElseThrow(()->new IllegalArgumentException("Does not exist"));

        if (friendRepository.existsByMemberAndFriend(fromMember, toMember)){
            Friends friend = friendRepository.findFriendsByMemberAndFriend(fromMember, toMember);
            friend.setStatus(FriendStatus.FRIEND);
            friendRepository.save(friend);
        }
    }

    public void becomeNoFriend(FriendRequestDTO dto){
        Member fromMember = memberRepository.findById(dto.getFromId()).orElseThrow(()->new IllegalArgumentException("Does not exist"));
        Member toMember = memberRepository.findById(dto.getToId()).orElseThrow(()->new IllegalArgumentException("Does not exist"));

        if (friendRepository.existsByMemberAndFriend(fromMember, toMember)){
            Friends friend = friendRepository.findFriendsByMemberAndFriend(fromMember, toMember);
            friend.setStatus(FriendStatus.REJECTED);
            friendRepository.save(friend);
        }

    }

}
