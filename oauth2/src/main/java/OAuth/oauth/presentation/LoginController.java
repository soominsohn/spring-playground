package OAuth.oauth.presentation;

import OAuth.oauth.application.KakaoAuthService;
import OAuth.oauth.application.dto.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {

    private final KakaoAuthService kakaoAuthService;

    @GetMapping("/kakao")
    public ResponseEntity<LoginResponse> login(@RequestParam final String code, @RequestParam final String redirectUrl) {
        final LoginResponse loginResponse = kakaoAuthService.login(code, redirectUrl);
        return ResponseEntity.ok(loginResponse);
    }
}
