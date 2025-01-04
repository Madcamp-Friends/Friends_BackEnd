package madcamp24w.friends.DTO;

import lombok.Getter;

@Getter
public class MemberRegisterDTO {
    private String nickname;
    private String email;
    private String password;
    private String passwordCheck;
}
