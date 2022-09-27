package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
// 컨트롤러에 있던 오류들을 여기에서 다 처리
public class ExControllerAdvice {

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

}
