package madcamp24w.friends.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import madcamp24w.friends.entity.Label;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class LabelDTO {
    private Long labelId;
    private String labelTopic;

    public static List<LabelDTO> LabelInfo(List<Label> labels){
        return labels.stream().
                map(label -> LabelDTO.builder()
                        .labelId(label.getId())
                        .labelTopic(label.getLabelName())
                        .build())
                .collect(Collectors.toList());

    }
}
