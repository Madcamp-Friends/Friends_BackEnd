package madcamp24w.friends.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String nickname;
    @Column
    private String email;
    @Column
    private String password;

    // 해당 사용자가 친구 관계의 주체인 경우의 목록
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friends> friendsList = new ArrayList<>();

    // 해당 사용자가 친구 관계의 대상인 경우의 목록
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friends> friendOfList = new ArrayList<>();

    public Member(String nickname, String email, String password){
        this.email=email;
        this.nickname=nickname;
        this.password=password;
    }

}
