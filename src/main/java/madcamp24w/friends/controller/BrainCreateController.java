package madcamp24w.friends.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.*;
import madcamp24w.friends.entity.BrainCreate;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.service.BrainCreateService;
import org.apache.commons.lang3.concurrent.EventCountCircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/brain")
public class BrainCreateController {

    private final BrainCreateService brainCreateService;

    // Create a new brain
    @PostMapping("/create")
    public ResponseEntity<BrainCreateResponseDTO> createBrain(@RequestBody BrainCreateDTO request, HttpSession session) {
        String nickname=(String) session.getAttribute("nickname");
        if(nickname!=null){
            BrainCreate brain = brainCreateService.createBrain(nickname, request);
            BrainCreateResponseDTO result = BrainCreateResponseDTO.toBrainResponse(brain);
            return ResponseEntity.ok(result);
        }else{
            return null;
            //return ResponseEntity.badRequest().body("nickname error");
        }

    }

    @GetMapping("/getMyBrain")
    public ResponseEntity<List<LabelDTO>> getIdMemberBrain(HttpSession session){
        try{
            String nickname = (String) session.getAttribute("nickname");
            List<LabelDTO> result = brainCreateService.getBrain(nickname);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return null;
        }

    }

    // 친구 리스트에서 친구 id 가져오면 id 연결할 수 있을 듯?
    @GetMapping("/getFriendBrain/{id}")
    public ResponseEntity<List<LabelDTO>> getIFriendBrain(@PathVariable(name="id") Long id){
        try{
            List<LabelDTO> result = brainCreateService.getFriendBrain(id);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return null;
        }

    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<LabelDTO> editBrainLabel(@PathVariable(name="id") Long id, @RequestBody BrainEditLabelDTO edit){
        try{
            LabelDTO result = brainCreateService.editBrainLabel(id, edit);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            return null;
        }

    }


    // Add a label to a brain
//    @PostMapping("/{brainnickname}/labels_add")
//    public void addlabel(@PathVariable String nickname, @RequestParam String label) {
//        brainCreateService.addLabel(nickname, label);
//    }

    // Remove a label from a brain
//    @DeleteMapping("/{brainnickname}/labels_delete")
//    public void removelabel(@PathVariable String nickname, @RequestParam String label) {
//        brainCreateService.removeLabel(nickname, label);
//    }
//    @GetMapping("/{nickname}/labels")
//    public BrainCreate getlabel(HttpSession session) {
//        String nickname = (String) session.getAttribute("nickname");
//        if(nickname!=null){
//            return brainCreateService.getBrain(nickname);
//        }else{
//            throw new IllegalArgumentException("nickname error");
//        }
//    }
}

