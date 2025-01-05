package madcamp24w.friends.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import madcamp24w.friends.entity.BrainCreate;
import madcamp24w.friends.repository.BrainCreateRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class BrainCreateService {

    private final BrainCreateRepository brainCreateRepository;

    // Create a new Brain
    @Transactional
    public void createBrain(String nickname) {
        if (brainCreateRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("Brain already exists");
        }
        BrainCreate brain = BrainCreate.builder()
                .nickname(nickname)
                .labels(new ArrayList<>())
                .build();
        brainCreateRepository.save(brain);
    }

    // Get a Brain by nickname
    public BrainCreate getBrain(String nickname) {
        return brainCreateRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Brain not found"));
    }

    // Add a label to a Brain
    @Transactional
    public void addLabel(String nickname, String label) {
        BrainCreate brainCreate = getBrain(nickname);
        if (!brainCreate.getLabels().contains(label)) {
            brainCreate.getLabels().add(label);
        }
    }

    // Remove a label from a Brain
    @Transactional
    public void removeLabel(String nickname, String label) {
        BrainCreate brainCreate = getBrain(nickname);
        if (brainCreate.getLabels().contains(label)) {
            brainCreate.getLabels().remove(label);
        }
    }
}
