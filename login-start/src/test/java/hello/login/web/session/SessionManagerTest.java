package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.*;

class SessionManagerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        MockHttpServletResponse response = new MockHttpServletResponse();

        // session 생성
        Member member = new Member();
        sessionManager.createSession(member, response);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies());

        // session 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        // session 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }

}