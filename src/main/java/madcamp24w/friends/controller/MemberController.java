package madcamp24w.friends.controller;
import lombok.RequiredArgsConstructor;

import madcamp24w.friends.DTO.ErrorResponseDTO;
import madcamp24w.friends.DTO.MemberInfoResponseDTO;
import madcamp24w.friends.DTO.MemberRegisterDTO;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.MemberRepository;
import madcamp24w.friends.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String nickname, @RequestParam String password) {
        boolean success = memberService.login(nickname, password);
        if (success) {
            return ResponseEntity.ok("Welcome");
        } else {
            return ResponseEntity.status(401).body("Invalid name or password");
        }
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(
            @RequestBody MemberRegisterDTO dto) {
        try {
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
}