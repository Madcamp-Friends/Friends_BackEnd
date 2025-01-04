package madcamp24w.friends.service;

import lombok.RequiredArgsConstructor;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;



    public boolean login(String nickname, String password) {
        Optional<Member> member=memberRepository.findByNickname(nickname);
        return member.isPresent() && member.get().getPassword().equals(password);
    }

    public void createAccount(String nickname, String email, String password) {
        Optional<Member> existingMember=memberRepository.findByNickname(nickname);
        Optional<Member> existingMember2=memberRepository.findByEmail(email);
        if(existingMember.isPresent()){
            throw new IllegalArgumentException("Nickname already registered");
        }
        if(existingMember2.isPresent()){
            throw new IllegalArgumentException("Email already registered");
        }
        Member newMember=Member.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .build();

        memberRepository.save(newMember);
    }
}
