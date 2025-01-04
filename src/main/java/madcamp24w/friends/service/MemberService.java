package madcamp24w.friends.service;

import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.MemberRegisterDTO;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean login(String nickname, String password) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return bCryptPasswordEncoder.matches(password, member.getPassword());
    }

    public void createAccount(MemberRegisterDTO dto) {
        Optional<Member> existingMember=memberRepository.findByNickname(dto.getNickname());
        Optional<Member> existingMember2=memberRepository.findByEmail(dto.getEmail());
        String pw1 = dto.getPassword();
        String pw2 = dto.getPasswordCheck();

        if(existingMember.isPresent()){
            throw new IllegalArgumentException("Nickname already registered");
        }
        if(existingMember2.isPresent()){
            throw new IllegalArgumentException("Email already registered");
        }

        else if (pw1.equals(pw2)){
            pw1 = bCryptPasswordEncoder.encode(pw1);
            Member member = Member.builder()
                    .email(dto.getEmail())
                    .nickname(dto.getNickname())
                    .password(pw1)
                    .build();
            memberRepository.save(member);
        }
    }
}
