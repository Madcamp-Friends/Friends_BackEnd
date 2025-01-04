package madcamp24w.friends.controller;

import lombok.RequiredArgsConstructor;
import madcamp24w.friends.repository.FriendRepository;
import madcamp24w.friends.service.FriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendRepository friendRepository;
    private final FriendService friendService;


}
