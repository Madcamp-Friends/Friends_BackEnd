package madcamp24w.friends.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name="brain_create")
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

    @ElementCollection
    @CollectionTable(name="brain_labels", joinColumns = @JoinColumn(name="brain_id"))
    @Column(name= "labels")
    private List<String> labels=new ArrayList<>();

    public BrainCreate(String nickname) {
        this.nickname=nickname;
        this.labels=new ArrayList<>();
    }
}
