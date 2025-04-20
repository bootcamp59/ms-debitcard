package com.bootcamp.ms_debitcard.application.port.in;

import com.bootcamp.ms_debitcard.domain.model.DebitCard;
import reactor.core.publisher.Mono;

public interface DebitCardUseCase {
    Mono<DebitCard> create(DebitCard model);
    Mono<DebitCard> findByCardNumber(String cardNumber);
}
