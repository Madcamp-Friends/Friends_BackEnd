package madcamp24w.friends.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name="brain")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BrainCreate {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @OneToMany(mappedBy = "brain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Label> labels = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private Member member;

    public BrainCreate(String nickname) {
        this.nickname=nickname;
    }
}
