package hello.exception.api;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex"))
            throw new RuntimeException("잘못된 사용자");

        if (id.equals("bad"))
            throw new IllegalArgumentException("잘못된 입력");
            // 여기서 예외가 터지면 ExceptionResolver 에게 오류 해결 시도
            // 이때 우선순위에 따라 (ExceptionHandlerExceptionResolver) 가 호출됨
            // @ExceptionHandler 어노테이션을 찾아서 있으면 메소드 호출

        if (id.equals("user-ex"))
            throw new UserException("사용자 오류");
        return new MemberDto(id, "name"+id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
