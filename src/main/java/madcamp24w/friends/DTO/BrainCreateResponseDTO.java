package madcamp24w.friends.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import madcamp24w.friends.entity.BrainCreate;
import madcamp24w.friends.entity.Label;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BrainCreateResponseDTO {
    private Long brain_id;
    private List<LabelDTO> topic;

    public static BrainCreateResponseDTO toBrainResponse(BrainCreate brain) {
        return BrainCreateResponseDTO.builder()
                .brain_id(brain.getId())
                .topic(
                        brain.getLabels().stream()
                                .map(label -> LabelDTO.builder()
                                        .labelId(label.getId())
                                        .labelTopic(label.getLabelName())
                                        .build())
                                .toList()
                )
                .build();
    }
}
