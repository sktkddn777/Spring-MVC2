package hello.login.web.argumentResolver;

import hello.login.domain.member.Member;
import hello.login.web.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        log.info("supportsParameter start");
        boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class); // Login 어노테이션이 붙어 있나?

        boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType()); // Member class가 반환됨

        return hasLoginAnnotation && hasMemberType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        log.info("resolveArgument start");

        HttpServletRequest req = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = req.getSession(false);
        if (session == null)
            return null;

        return session.getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
