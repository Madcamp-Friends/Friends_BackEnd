package madcamp24w.friends.service;

import lombok.RequiredArgsConstructor;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberInfoService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void nicknameEdit(String nickname, String newNickname){
        Member member=memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
        member.setNickname(newNickname);
        memberRepository.save(member);
    }
    public void emailEdit(String nickname, String newEmail){
        Member member=memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
        member.setEmail(newEmail);
        memberRepository.save(member);
    }
    public boolean passwordEdit(String nickname, String password, String newPassword){
        Member member= memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String oldpassword=member.getPassword();
        if(bCryptPasswordEncoder.matches(password, oldpassword)){
            String setpassword=bCryptPasswordEncoder.encode(newPassword);
            member.setPassword(setpassword);
            memberRepository.save(member);
            return true;
        }
        return false;
    }

}
