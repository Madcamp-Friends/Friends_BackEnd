package madcamp24w.friends.controller;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import madcamp24w.friends.DTO.ErrorResponseDTO;
import madcamp24w.friends.DTO.MemberInfoRequestPassDTO;
import madcamp24w.friends.DTO.MemberInfoRequestMailDTO;
import madcamp24w.friends.DTO.MemberInfoRequestNickDTO;
import madcamp24w.friends.DTO.MemberInfoResponseDTO;
import madcamp24w.friends.DTO.MemberRegisterDTO;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.MemberRepository;
import madcamp24w.friends.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import madcamp24w.friends.service.BrainCreateService;
import madcamp24w.friends.service.MemberInfoService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BrainCreateService brainCreateService;
    private final MemberInfoService memberInfoService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String nickname, @RequestParam String password, HttpSession session) {
        boolean success = memberService.login(nickname, password);
        if (success) {
            Member member=memberService.bring(nickname);
            session.setAttribute("nickname",member.getNickname());
            memberService.loginChecked(nickname);
            return ResponseEntity.ok("Welcome");
        } else {
            return ResponseEntity.status(401).body("Invalid name or password");
        }
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(
            @RequestBody MemberRegisterDTO dto, HttpSession session) {
        try {
            String nickname = dto.getNickname();
            session.setAttribute("nickname",nickname);
            memberService.createAccount(dto);
            return ResponseEntity.ok("Account created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/members")
    public ResponseEntity<?> getAllMembers() {
        try {
            List<Member> members = memberRepository.findAll();
            List<MemberInfoResponseDTO> result = MemberInfoResponseDTO.memberInfoResponseDTOList(members);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    "Failed to fetch members",
                    e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorResponse);


        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        String nickname = (String) session.getAttribute("nickname");
        try{
            memberService.Logout(nickname);
            session.invalidate();
            return ResponseEntity.ok("안녕~");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/me")
    public ResponseEntity<MemberInfoResponseDTO> getCurrentUser(HttpSession session) {
        try {
            String nickname = (String) session.getAttribute("nickname");
            System.out.println("HEEJU get user" + nickname);
            Member member = memberRepository.findByNickname(nickname).orElseThrow(()->new IllegalArgumentException("no member"));
            MemberInfoResponseDTO result = MemberInfoResponseDTO.memberTOInfo(member);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping("/change/nickname")
    public ResponseEntity<?> changeUsername(@RequestBody MemberInfoRequestNickDTO dto, HttpSession session) {
        String oldNickname = (String) session.getAttribute("nickname");
        try{
            memberInfoService.nicknameEdit(oldNickname,dto.getNickname());
            session.setAttribute("nickname",dto.getNickname());
            return ResponseEntity.ok("Nickname changed successfully");
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/change/email")
    public ResponseEntity<?> changeEmail(@RequestBody MemberInfoRequestMailDTO dto, HttpSession session) {
        String oldNickname=(String) session.getAttribute("nickname");
        try{
            memberInfoService.emailEdit(oldNickname,dto.getEmail());
            return ResponseEntity.ok("Email changed successfully");
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/change/password")
    public ResponseEntity<?> changePassword(@RequestBody MemberInfoRequestPassDTO dto, HttpSession session) {
        String oldNickname=(String) session.getAttribute("nickname");
        try{
            boolean truth=memberInfoService.passwordEdit(oldNickname,dto.getPassword(), dto.getNewpassword());
            if(truth){
                return ResponseEntity.ok("Password changed successfully");
            }else{
                return ResponseEntity.badRequest().body("Wrong password");
            }
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}