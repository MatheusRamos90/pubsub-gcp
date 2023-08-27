package br.com.matheushramos.apppaymentfoodsub.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PaymentFoodOrder {

    private UUID orderId;
    private List<FoodData> foodData;

}
