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
public class MemberInfoResponseDTO {
    private Long id;
    private String nickname;

    public static MemberInfoResponseDTO memberTOInfo(Member member){
        return MemberInfoResponseDTO.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .build();
    }

    public static List<MemberInfoResponseDTO> memberInfoResponseDTOList(List<Member> list){
        List<MemberInfoResponseDTO> dtos = new ArrayList<>();
        for (Member member: list){
            dtos.add(memberTOInfo(member));
        }
        return dtos;
    }


    public static MemberInfoResponseDTO FriendTOInfo(Friends friends){
        return MemberInfoResponseDTO.builder()
                .id(friends.getFriend().getId())
                .nickname(friends.getFriend().getNickname())
                .build();
    }


    public static List<MemberInfoResponseDTO> friendInfoResponseDTOList(List<Friends> list){
        List<MemberInfoResponseDTO> dtos = new ArrayList<>();
        for (Friends friends: list){
            dtos.add(FriendTOInfo(friends));
        }
        return dtos;
    }


    public static MemberInfoResponseDTO FriendWhoGiveMePendingInfo(Friends friends){
        return MemberInfoResponseDTO.builder()
                .id(friends.getMember().getId())
                .nickname(friends.getMember().getNickname())
                .build();
    }

    public static List<MemberInfoResponseDTO> FriendWhoGiveMePendingInfoList(List<Friends> list){
        List<MemberInfoResponseDTO> dtos = new ArrayList<>();
        for (Friends friends: list){
            dtos.add(FriendWhoGiveMePendingInfo(friends));
        }
        return dtos;
    }

}
