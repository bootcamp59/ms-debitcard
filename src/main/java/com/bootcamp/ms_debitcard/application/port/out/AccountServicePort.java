package com.bootcamp.ms_debitcard.application.port.out;

import com.bootcamp.ms_debitcard.domain.dto.AccountInfo;
import com.bootcamp.ms_debitcard.domain.dto.Transaction;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AccountServicePort {

    Mono<List<AccountInfo>> getAccountByIds(List<String> productIds);
    Mono<Transaction> executePayment(Transaction transaction);
}
