package madcamp24w.friends.service;

import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

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
        Member newMember=new Member({nickname, email, password});
        await newMember.save();
        memberRepository.save(newMember);
    }
}
