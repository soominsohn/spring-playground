package OAuth.oauth.application;

import OAuth.oauth.domain.Member;

public interface AuthService {
    Member login(final String code, final String redirectUrl);
}
