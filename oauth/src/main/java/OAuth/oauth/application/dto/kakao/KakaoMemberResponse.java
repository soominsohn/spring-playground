package OAuth.oauth.application.dto.kakao;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoMemberResponse {

    private Long id;
    private KakaoAccount kakao_account;

    public String getEmail() {
        return kakao_account.getEmail();
    }

    public String getNickname() {
        return kakao_account.getProfile().getNickname();
    }
}
