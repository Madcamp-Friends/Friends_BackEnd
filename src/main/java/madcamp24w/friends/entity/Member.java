package madcamp24w.friends.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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
    @Column
    private Integer log;

    // 해당 사용자가 친구 관계의 주체인 경우의 목록
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friends> friendsList = new ArrayList<>();

    // 해당 사용자가 친구 관계의 대상인 경우의 목록, mappedby는 반대편 엔티티에 있는 필드명 ManyToOne의 필드 명
    @OneToMany(mappedBy = "friend", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Friends> friendOfList = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private BrainCreate brain;

    public Member(String nickname, String email, String password) {
        this.email=email;
        this.nickname=nickname;
        this.password=password;
        this.log=0;
    }

}
