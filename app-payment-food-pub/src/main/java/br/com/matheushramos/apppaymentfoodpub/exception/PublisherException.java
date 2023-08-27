package br.com.matheushramos.apppaymentfoodpub.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class PublisherException extends RuntimeException {

    private final HttpStatus status;

    public PublisherException(final String message, final Throwable t, final HttpStatus status) {
        super(message, t);
        this.status = status;
    }

}
