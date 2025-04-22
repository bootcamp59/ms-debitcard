package com.bootcamp.ms_debitcard.infrastructure.adapter.out.client;

import com.bootcamp.ms_debitcard.application.port.out.AccountServicePort;
import com.bootcamp.ms_debitcard.domain.dto.AccountInfo;
import com.bootcamp.ms_debitcard.domain.dto.Transaction;
import com.bootcamp.ms_debitcard.infrastructure.config.YankiProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountServiceAdapter implements AccountServicePort {

    private final WebClient.Builder webClientBuilder;
    private final YankiProperties properties;

    @Override
    public Mono<List<AccountInfo>> getAccountByIds(List<String> productIds) {
        var url = properties.getMsAccountPath() + "/productIdIn";

        return webClientBuilder.build()
                .post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(productIds)
                .retrieve()
                .bodyToFlux(AccountInfo.class)
                .collectList()
                .doOnNext(f -> {
                    log.info("conexion exitosa al serivicio: {}", url + f);
                })
                .doOnError( err ->  log.info("no se logro la conexion al serivicio: {}", url));
    }

    @Override
    public Mono<Transaction> executePayment(Transaction transaction) {
        var url = properties.getMsAccountPath() + "/transfer";

        return webClientBuilder.build()
                .post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(transaction)
                .retrieve()
                .bodyToMono(Transaction.class)
                .doOnNext(f -> {
                    log.info("conexion exitosa al serivicio: {}", url + f);
                })
                .doOnError( err ->  log.info("no se logro la conexion al serivicio: {}", url));
    }
}
