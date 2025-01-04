package madcamp24w.friends.controller;

import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.ErrorResponseDTO;
import madcamp24w.friends.DTO.MemberInfoResponseDTO;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

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
