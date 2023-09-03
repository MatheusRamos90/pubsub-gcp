package br.com.matheushramos.apppaymentfoodpub.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FoodData {

    private String name;
    private String refId;
    private BigDecimal price;

}
