package com.bootcamp.ms_debitcard.application.port.in;

import com.bootcamp.ms_debitcard.domain.dto.Transaction;
import com.bootcamp.ms_debitcard.domain.model.DebitCard;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.model.BalanceResponse;
import reactor.core.publisher.Mono;

public interface DebitCardUseCase {
    Mono<DebitCard> create(DebitCard model);
    Mono<DebitCard> findByCardNumber(String cardNumber);
    Mono<Transaction> payment(Transaction transaction);
    Mono<BalanceResponse> balancePrincipal(String cardNumber);
}
