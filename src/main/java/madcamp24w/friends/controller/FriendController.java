package madcamp24w.friends.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.ErrorResponseDTO;
import madcamp24w.friends.DTO.FriendRequestDTO;
import madcamp24w.friends.DTO.MemberInfoResponseDTO;
import madcamp24w.friends.entity.Friends;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.FriendRepository;
import madcamp24w.friends.repository.MemberRepository;
import madcamp24w.friends.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendRepository friendRepository;
    private final FriendService friendService;
    private final MemberRepository memberRepository;

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

    @PostMapping("/endFriend")
    public ResponseEntity<?> rejectFriendship(@RequestBody FriendRequestDTO dto, HttpSession session){
        try{
            friendService.becomeNoFriend(dto, session);
            return ResponseEntity.ok().body("친구관게 정리 끝냄 완료");
        } catch (Exception e){
            return null;
        }
    }

    @GetMapping("/getPendingFriend")
    public ResponseEntity<?> getAllPendingFriend(HttpSession session){
        try{
            String nickname = (String) session.getAttribute("nickname");
            Member member = memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("No member"));
            List<Friends> friends = friendRepository.findFriendsWithPendingState(member.getId());
            List<MemberInfoResponseDTO> result = MemberInfoResponseDTO.friendInfoResponseDTOList(friends);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return null;
        }
    }


    @GetMapping("/getFriendList")
    public ResponseEntity<?> getMyAllFriend(HttpSession session){
        try{
            String nickname = (String) session.getAttribute("nickname");
            Member member = memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("No member"));
            List<Friends> friends = friendRepository.findFriendsWithFriendState(member.getId());
            for(Friends f : friends){
                System.out.println("heeju Test "+ f.getFriend().getId()+" "+f.getFriend().getNickname()+" "+f.getMember().getId()+" "+f.getMember().getNickname());
            }
            List<MemberInfoResponseDTO> result = MemberInfoResponseDTO.friendInfoResponseDTOList(friends);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return null;
        }
    }

    @GetMapping("/getMyPendingFriend")
    public ResponseEntity<?> getAllMyPendingFriend(HttpSession session){
        try{
            String nickname = (String) session.getAttribute("nickname");
            Member member = memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("No member"));
            List<Friends> friends = friendRepository.findFriendsWhoGiveMePending(member.getId());
            List<MemberInfoResponseDTO> result = MemberInfoResponseDTO.FriendWhoGiveMePendingInfoList(friends);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return null;
        }
    }

}
