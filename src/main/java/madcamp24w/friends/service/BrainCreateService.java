package madcamp24w.friends.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.BrainCreateDTO;
import madcamp24w.friends.entity.BrainCreate;
import madcamp24w.friends.entity.Label;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.BrainCreateRepository;
import madcamp24w.friends.repository.LabelRepository;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrainCreateService {

    private final BrainCreateRepository brainCreateRepository;
    private final MemberRepository memberRepository;
    private final LabelRepository labelRepository;

    // Create a new Brain
    @Transactional
    public BrainCreate createBrain(String nickname, BrainCreateDTO dto) {
        Member member = memberRepository.findByNickname(nickname).orElseThrow(()-> new IllegalArgumentException("NO Member"));
        if (brainCreateRepository.existsByNickname(nickname)) {
            throw new IllegalArgumentException("Brain already exists");
        }

        // dto로부터 정보를 얻어 어느 하나 객체에 또 다른 객체가 연결되고 영속되어야한다면 먼저 각각 객체 빌드하고 거기에 추가하기
        BrainCreate brain = BrainCreate.builder()
                .member(member)
                .nickname(nickname)
                .build();


        // 3. Label 리스트 생성 및 BrainCreate와 연관 설정
        List<Label> labels = dto.getLabelContent().stream()
                .map(content -> Label.builder()
                        .labelName(content)
                        .brain(brain) // BrainCreate와 관계 설정
                        .build())
                .toList();


        // labelRepository.saveAll(labels); // Label 저장

        brain.setLabels(labels); // Brain에 Label 리스트 설정
        return brainCreateRepository.save(brain); // Brain 먼저 저장

    }

    // Get a Brain by nickname
    public BrainCreate getBrain(String nickname) {
        return brainCreateRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Brain not found"));
    }

    // Add a label to a Brain
    /*@Transactional
    public void addLabel(String nickname, String label) {
        BrainCreate brainCreate = getBrain(nickname);
        if (!brainCreate.getLabels().contains(label)) {
            brainCreate.getLabels().add(label);
        }
    }*/

    // Remove a label from a Brain
    @Transactional
    public void removeLabel(String nickname, String label) {
        BrainCreate brainCreate = getBrain(nickname);
        if (brainCreate.getLabels().contains(label)) {
            brainCreate.getLabels().remove(label);
        }
    }
}
