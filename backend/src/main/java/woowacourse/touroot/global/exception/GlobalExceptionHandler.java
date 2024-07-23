package woowacourse.touroot.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import woowacourse.touroot.global.exception.dto.ExceptionResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException exception) {
        log.info("BAD_REQUEST_EXCEPTION :: message = {}", exception.getMessage());

        ExceptionResponse data = new ExceptionResponse(exception.getMessage());
        return ResponseEntity.badRequest()
                .body(data);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        log.info("METHOD_ARGUMENT_NOT_VALID_EXCEPTION :: message = {}", exception.getMessage());

        String message = exception.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
        ExceptionResponse data = new ExceptionResponse(message);
        return ResponseEntity.badRequest()
                .body(data);
    }

    @ExceptionHandler(ClientException.class)
    public ResponseEntity<ExceptionResponse> handleClientException(ClientException exception) {
        log.error("CLIENT_EXCEPTION :: message = {}", exception.getMessage());

        ExceptionResponse data = new ExceptionResponse(exception.getMessage());
        return ResponseEntity.internalServerError().body(data);
    }
}
