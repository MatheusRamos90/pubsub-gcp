package br.com.matheushramos.apppaymentfoodpub.config;

import br.com.matheushramos.apppaymentfoodpub.dto.MessageError;
import br.com.matheushramos.apppaymentfoodpub.exception.PublisherException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class HandlerCustomException {

    @ExceptionHandler(PublisherException.class)
    public ResponseEntity<MessageError> handlePublisherException(PublisherException ex) {
        log.error(ex.getMessage());
        return ResponseEntity
                .status(ex.getStatus())
                .body(MessageError.builder()
                        .status(ex.getStatus().value())
                        .message(ex.getMessage())
                        .build());
    }

}
