package madcamp24w.friends.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import madcamp24w.friends.entity.Friends;
import madcamp24w.friends.entity.Member;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfoEditResponseDTO {
    private Long id;
    private String nickname;
    private String email;

    public static MemberInfoEditResponseDTO memberTOInfo(Member member){
        return MemberInfoEditResponseDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }

    public static List<MemberInfoEditResponseDTO> memberInfoResponseDTOList(List<Member> list){
        List<MemberInfoEditResponseDTO> dtos = new ArrayList<>();
        for (Member member: list){
            dtos.add(memberTOInfo(member));
        }
        return dtos;
    }


    public static MemberInfoEditResponseDTO FriendTOInfo(Friends friends){
        return MemberInfoEditResponseDTO.builder()
                .id(friends.getFriend().getId())
                .nickname(friends.getFriend().getNickname())
                .build();
    }


    public static List<MemberInfoEditResponseDTO> friendInfoResponseDTOList(List<Friends> list){
        List<MemberInfoEditResponseDTO> dtos = new ArrayList<>();
        for (Friends friends: list){
            dtos.add(FriendTOInfo(friends));
        }
        return dtos;
    }


    public static MemberInfoEditResponseDTO FriendWhoGiveMePendingInfo(Friends friends){
        return MemberInfoEditResponseDTO.builder()
                .id(friends.getMember().getId())
                .nickname(friends.getMember().getNickname())
                .build();
    }

    public static List<MemberInfoEditResponseDTO> FriendWhoGiveMePendingInfoList(List<Friends> list){
        List<MemberInfoEditResponseDTO> dtos = new ArrayList<>();
        for (Friends friends: list){
            dtos.add(FriendWhoGiveMePendingInfo(friends));
        }
        return dtos;
    }

}
