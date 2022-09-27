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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("exceptionHandler ex", e);
        return new ErrorResult("bad", e.getMessage());
        // 정상적으로 리턴하고 json 으로 반환하면서 끝남
        // was 도 정상응답으로 나감
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("exceptionHandler ex", e);
        ErrorResult errorResult = new ErrorResult("user-ex", e.getMessage());
        return new ResponseEntity(errorResult, HttpStatus.BAD_REQUEST);
    }

    // 다른 exceptionHandler 가 처리하지 못한 가장 넓은 범위의 에러
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) {
        log.error("internal exceptionHandler ex", e);
        return new ErrorResult("server error :(", e.getMessage());
    }

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
