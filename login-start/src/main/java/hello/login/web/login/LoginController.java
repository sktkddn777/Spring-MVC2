package hello.login.web.login;

import hello.login.domain.login.Login;
import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") Login form) {
        return "login/loginForm";
    }

    // @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") Login form, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "로그인 에러");
            return "login/loginForm";
        }

        // 쿠키에 시간 정보를 주지 않으면 세션 쿠키 (브라우저 종료 시점까지 유지)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        log.info("cookie = {}", idCookie);
        response.addCookie(idCookie);

        return "redirect:/";
    }

    // @PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute("loginForm") Login form, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "로그인 에러");
            return "login/loginForm";
        }

        // session 적용
        sessionManager.createSession(loginMember, response);

        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute("loginForm") Login form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "로그인 에러");
            return "login/loginForm";
        }

        // session 적용
        // session 에 있으면 있는거 반환 없으면 새로 생성 (getSession(default=true) 일 경우)
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return "redirect:/";
    }

    // @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    // @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request) {
        sessionManager.expire(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();
        return "redirect:/";
    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
