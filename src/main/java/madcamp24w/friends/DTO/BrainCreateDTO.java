package madcamp24w.friends.DTO;

import lombok.Builder;
import lombok.Getter;
import madcamp24w.friends.entity.BrainCreate;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class BrainCreateDTO {
    private List<String> labelContent;
}

