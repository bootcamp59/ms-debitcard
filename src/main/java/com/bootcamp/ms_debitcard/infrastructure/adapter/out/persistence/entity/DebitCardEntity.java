package com.bootcamp.ms_debitcard.infrastructure.adapter.out.persistence.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Document(collection = "debit_card")
@Builder
public class DebitCardEntity {
    @Id
    private String cardNumber;
    private String customerDocument;
    private String primaryAccountId;           // productoId de la cuenta principal.
    private List<String> linkedAccountIds;     // productoIds en el orden de prioridad.
    private LocalDateTime createdAt;
}
