package com.bootcamp.ms_debitcard.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfo {

    private String productoId;
    private String document;
    private String banco;
    private String type;
    private BigDecimal saldo;
}
