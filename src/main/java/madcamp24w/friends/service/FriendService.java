package madcamp24w.friends.service;

import lombok.RequiredArgsConstructor;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private MemberRepository memberRepository;
    public void getAllMemberExceptMe(){
        List<Member>
    }
}
