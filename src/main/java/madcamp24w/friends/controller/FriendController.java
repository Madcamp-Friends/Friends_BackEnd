package madcamp24w.friends.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.ErrorResponseDTO;
import madcamp24w.friends.DTO.FriendRequestDTO;
import madcamp24w.friends.repository.FriendRepository;
import madcamp24w.friends.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendRepository friendRepository;
    private final FriendService friendService;

    @PostMapping("/create")
    public ResponseEntity<?> createFriendship(@RequestBody FriendRequestDTO dto, HttpSession session){
        try{
            friendService.createFriendship(dto, session);
            return ResponseEntity.ok().body("친구관게 신청 완료");
        } catch (Exception e){
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    "Failed",
                    e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/toFriend")
    public ResponseEntity<?> updateFriendship(@RequestBody FriendRequestDTO dto, HttpSession session){
        try{
            friendService.becomeFriend(dto, session);
            return ResponseEntity.ok().body("친구관게 성립완료");
        } catch (Exception e){
            return null;
        }
    }

    @PostMapping("/noFriend")
    public ResponseEntity<?> rejectFriendship(@RequestBody FriendRequestDTO dto, HttpSession session){
        try{
            friendService.becomeNoFriend(dto, session);
            return ResponseEntity.ok().body("친구관게 정리 끝냄 완료");
        } catch (Exception e){
            return null;
        }
    }


}
