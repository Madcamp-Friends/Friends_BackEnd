package madcamp24w.friends.DTO;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BrainCreateDTO {
    private String nickname;
    private ArrayList<String> labels;
}
