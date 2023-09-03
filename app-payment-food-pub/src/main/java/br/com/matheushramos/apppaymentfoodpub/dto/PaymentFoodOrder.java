package br.com.matheushramos.apppaymentfoodpub.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class PaymentFoodOrder {

    private UUID orderId;
    private List<FoodData> foodData;

}
