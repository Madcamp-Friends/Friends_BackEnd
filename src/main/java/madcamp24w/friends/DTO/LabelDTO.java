package madcamp24w.friends.DTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LabelDTO {
    private Long labelId;
    private String labelTopic;
}
