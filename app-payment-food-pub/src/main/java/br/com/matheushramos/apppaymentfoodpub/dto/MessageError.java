package br.com.matheushramos.apppaymentfoodpub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageError {

    private Integer status;
    private String message;

}
