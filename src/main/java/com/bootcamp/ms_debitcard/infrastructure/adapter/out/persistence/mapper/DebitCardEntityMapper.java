package com.bootcamp.ms_debitcard.infrastructure.adapter.out.persistence.mapper;


import com.bootcamp.ms_debitcard.domain.model.DebitCard;
import com.bootcamp.ms_debitcard.infrastructure.adapter.out.persistence.entity.DebitCardEntity;

public class DebitCardEntityMapper {

    public static DebitCard toModel(DebitCardEntity entity){
        return DebitCard.builder()
                .cardNumber(entity.getCardNumber())
                .documentNumber(entity.getCustomerDocument())
                .primaryAccountId(entity.getPrimaryAccountId())
                .linkedAccountIds(entity.getLinkedAccountIds())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static DebitCardEntity toEntity(DebitCard model){
        return DebitCardEntity.builder()
                .cardNumber(model.getCardNumber())
                .customerDocument(model.getDocumentNumber())
                .primaryAccountId(model.getPrimaryAccountId())
                .linkedAccountIds(model.getLinkedAccountIds())
                .createdAt(model.getCreatedAt())
                .build();
    }
}
