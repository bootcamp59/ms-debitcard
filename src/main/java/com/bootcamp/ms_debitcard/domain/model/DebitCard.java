package com.bootcamp.ms_debitcard.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class DebitCard {

    private String cardNumber;
    private String documentNumber;
    private String primaryAccountId;           // productoId de la cuenta principal.
    private List<String> linkedAccountIds;     // productoIds en el orden de prioridad.
    private LocalDateTime createdAt;
}
