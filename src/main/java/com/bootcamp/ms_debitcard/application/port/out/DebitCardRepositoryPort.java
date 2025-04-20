package com.bootcamp.ms_debitcard.application.port.out;

import com.bootcamp.ms_debitcard.domain.model.DebitCard;
import reactor.core.publisher.Mono;

public interface DebitCardRepositoryPort {

    Mono<DebitCard> create(DebitCard model);
    Mono<DebitCard> findByCardNumber(String cardNumber);
}
