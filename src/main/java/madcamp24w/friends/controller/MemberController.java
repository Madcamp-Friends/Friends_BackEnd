package madcamp24w.friends.controller;

import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.MemberRegisterDTO;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String nickname, @RequestParam String password) {
        boolean success=memberService.login(nickname, password);
        if(success) {
            return ResponseEntity.ok("Welcome");
        }
        else{
            return ResponseEntity.status(401).body("Invalid name or password");
        }
    }

    @PostMapping("/create-account")
    public ResponseEntity<String> createAccount(
            @RequestParam String nickname,
            @RequestParam String email,
            @RequestParam String password) {
        try{
            memberService.createAccount(nickname, email, password);
            return ResponseEntity.ok("Account created");
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
