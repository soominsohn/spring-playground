package OAuth.oauth.application;

import OAuth.oauth.application.dto.AccessTokenResponse;
import OAuth.oauth.application.dto.LoginResponse;
import OAuth.oauth.application.dto.kakao.KakaoMemberResponse;
import OAuth.oauth.domain.Member;
import OAuth.oauth.domain.MemberRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoAuthService {

    private static final String KAKAO_OAUTH_TOKEN_HOST = "https://kauth.kakao.com";
    private static final String KAKAO_OAUTH_TOKEN_URI = "/oauth/token";
    private static final String KAKAO_OAUTH_RESOURCE_HOST = "https://kapi.kakao.com";
    private static final String KAKAO_OAUTH_RESOURCE_URI = "/v2/user/me";

    private final MemberRepository memberRepository;
    private final String clientId;

    public KakaoAuthService(final MemberRepository memberRepository,
                            @Value("${kakao.clientId}") final String clientId) {
        this.memberRepository = memberRepository;
        this.clientId = clientId;
    }

    public LoginResponse login(final String code, final String redirectUrl) {
        //kakao로 access token 발급 요청
        final AccessTokenResponse tokenResponse = getToken(code, redirectUrl);

        final KakaoMemberResponse kakaoMemberResponse = getMemberInfo(tokenResponse);

        final String memberEmail = kakaoMemberResponse.getEmail();
        final String nickname = kakaoMemberResponse.getNickname();

        final Member member = memberRepository.findByEmail(memberEmail)
                .orElseGet(() -> memberRepository.save(new Member(memberEmail, nickname)));

        return new LoginResponse(member.getEmail(), member.getNickname());
    }

    @Nullable
    private AccessTokenResponse getToken(String code, String redirectUrl) {
        return WebClient.create(KAKAO_OAUTH_TOKEN_HOST).post()
                .uri(uriBuilder -> uriBuilder.path(KAKAO_OAUTH_TOKEN_URI)
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectUrl)
                        .queryParam("code", code)
                        .build())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToMono(AccessTokenResponse.class)
                .block();
    }

    @Nullable
    private KakaoMemberResponse getMemberInfo(AccessTokenResponse tokenResponse) {
        return WebClient.create(KAKAO_OAUTH_RESOURCE_HOST).post().
                uri(
                        uriBuilder -> uriBuilder.path(KAKAO_OAUTH_RESOURCE_URI)
                                .queryParam("property_keys", "[\"kakao_account.profile\",\"kakao_account.email\"]")
                                .build()
                ).header(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", tokenResponse.getAccess_token()))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .retrieve()
                .bodyToMono(KakaoMemberResponse.class)
                .block();
    }
}
