package OAuth.oauth.application;

import OAuth.oauth.domain.Member;
import OAuth.oauth.domain.MemberRepository;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.openid.connect.OpenIDConnectTokenRequest;
import com.slack.api.methods.request.openid.connect.OpenIDConnectUserInfoRequest;
import com.slack.api.methods.response.openid.connect.OpenIDConnectTokenResponse;
import com.slack.api.methods.response.openid.connect.OpenIDConnectUserInfoResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public class SlackAuthService {

    private final MemberRepository memberRepository;

    private final MethodsClient slackClient;
    @Value("${slack.clientId}")
    private final String clientId;
    @Value("${slack.clientSecret}")
    private final String clientSecret;

    public Member login(final String code, final String redirectUrl) throws SlackApiException, IOException {
        //slack으로 access token 발급 요청
        final OpenIDConnectTokenRequest tokenRequest = OpenIDConnectTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(code)
                .redirectUri(redirectUrl)
                .build();

        final OpenIDConnectTokenResponse tokenResponse = slackClient.openIDConnectToken(tokenRequest);

        //access token을 활용해 slack에 사용자 정보 요청
        final OpenIDConnectUserInfoRequest openIDConnectUserInfoRequest = OpenIDConnectUserInfoRequest.builder()
                .token(tokenResponse.getAccessToken())
                .build();

        final OpenIDConnectUserInfoResponse userInfoResponse = slackClient.openIDConnectUserInfo(
                openIDConnectUserInfoRequest);

        final String memberEmail = userInfoResponse.getEmail();

        final Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. "));

        return member;
    }
}