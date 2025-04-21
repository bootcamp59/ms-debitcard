package com.bootcamp.ms_debitcard.infrastructure.adapter.in.mapper;

import com.bootcamp.ms_debitcard.domain.dto.Transaction;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.model.TransactionDto;

public class OpenMapper {

    public static Transaction toDto(TransactionDto dto){
        return Transaction.builder()
                .amount(dto.getAmount())
                .document(dto.getDocument())
                .originProductId(dto.getOriginProductId())
                .destinyProductId(dto.getDestinyProductId())
                .description(dto.getDescription())
                .tipo(dto.getTipo())
                .build();
    }

    public static TransactionDto toOpenApi(Transaction model){
        var transaction = new TransactionDto();
        transaction.amount(model.getAmount());
        transaction.document(model.getDocument());
        transaction.originProductId(model.getOriginProductId());
        transaction.destinyProductId(model.getDestinyProductId());
        transaction.description(model.getDescription());
        transaction.tipo(model.getTipo());
        return transaction;
    }
}
