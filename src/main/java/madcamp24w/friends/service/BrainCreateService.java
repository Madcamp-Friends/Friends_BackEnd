package madcamp24w.friends.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import madcamp24w.friends.DTO.BrainCreateDTO;
import madcamp24w.friends.DTO.BrainCreateResponseDTO;
import madcamp24w.friends.DTO.BrainEditLabelDTO;
import madcamp24w.friends.DTO.LabelDTO;
import madcamp24w.friends.entity.BrainCreate;
import madcamp24w.friends.entity.Label;
import madcamp24w.friends.entity.Member;
import madcamp24w.friends.repository.BrainCreateRepository;
import madcamp24w.friends.repository.LabelRepository;
import madcamp24w.friends.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

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

        brain = brainCreateRepository.save(brain);

        // 3. Label 리스트 생성 및 BrainCreate와 연관 설정
        BrainCreate finalBrain = brain;
        List<Label> labels = dto.getLabelContent().stream()
                .map(content -> Label.builder()
                        .labelName(content)
                        .brain(finalBrain) // BrainCreate와 관계 설정
                        .build())
                .toList();


        // labelRepository.saveAll(labels); // Label 저장

        brain.setLabels(labels); // Brain에 Label 리스트 설정
        return brain; // Brain 먼저 저장

    }

    // Get a Brain by nickname
    public List<LabelDTO> getBrain(String nickname) {
        BrainCreate brain = brainCreateRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("Brain not found"));

        List<Label> result = labelRepository.findAllByBrain_Id(brain.getId());
        return LabelDTO.LabelInfo(result);

    }

    public List<LabelDTO> getFriendBrain(Long id) {
        BrainCreate brain = brainCreateRepository.findByMemberId(id)
                .orElseThrow(() -> new IllegalArgumentException("Brain not found"));

        List<Label> result = labelRepository.findAllByBrain_Id(brain.getId());
        return LabelDTO.LabelInfo(result);

    }

    public LabelDTO editBrainLabel(Long id, BrainEditLabelDTO dto){
        Label label = labelRepository.findById(id).orElseThrow(()->new IllegalArgumentException("No such Label"));
        if (!dto.getLabel().isEmpty()){
            label.setLabelName(dto.getLabel());
            label = labelRepository.save(label);
        }

        return LabelDTO.builder().labelId(id).labelTopic(label.getLabelName()).build();


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
//    @Transactional
//    public void removeLabel(String nickname, String label) {
//        BrainCreate brainCreate = getBrain(nickname);
//        if (brainCreate.getLabels().contains(label)) {
//            brainCreate.getLabels().remove(label);
//        }
//    }
}
