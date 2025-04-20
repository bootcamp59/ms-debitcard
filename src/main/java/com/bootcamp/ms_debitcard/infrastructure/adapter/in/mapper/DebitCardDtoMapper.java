package com.bootcamp.ms_debitcard.infrastructure.adapter.in.mapper;

import com.bootcamp.ms_debitcard.domain.model.DebitCard;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.model.DebitCardDto;

public class DebitCardDtoMapper {

    public static DebitCard toModel(DebitCardDto dto){
        return DebitCard.builder()
                .cardNumber(dto.getCardNumber())
                .documentNumber(dto.getDocumentNumber())
                .primaryAccountId(dto.getPrimaryAccountId())
                .linkedAccountIds(dto.getLinkedAccountIds())
                .build();
    }

    public static DebitCardDto toDto(DebitCard model){
        var debitCardDto = new DebitCardDto();
        debitCardDto.setCardNumber(model.getCardNumber());
        debitCardDto.setDocumentNumber(model.getDocumentNumber());
        debitCardDto.setPrimaryAccountId(model.getPrimaryAccountId());
        debitCardDto.setLinkedAccountIds(model.getLinkedAccountIds());
        return debitCardDto;
    }
}
