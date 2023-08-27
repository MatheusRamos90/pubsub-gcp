package br.com.matheushramos.apppaymentfoodpub.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FoodData {

    private String name;
    private String refId;
    private BigDecimal price;

}
