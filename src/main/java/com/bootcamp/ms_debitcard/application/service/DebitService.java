package com.bootcamp.ms_debitcard.application.service;

import com.bootcamp.ms_debitcard.application.port.in.DebitCardUseCase;
import com.bootcamp.ms_debitcard.application.port.out.DebitCardRepositoryPort;
import com.bootcamp.ms_debitcard.domain.dto.AccountInfo;
import com.bootcamp.ms_debitcard.domain.model.DebitCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebitService implements DebitCardUseCase {

    private final DebitCardRepositoryPort port;
    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<DebitCard> create(DebitCard model) {
        return port.findByCardNumber(model.getCardNumber())
            .flatMap( exist -> Mono.<DebitCard>error(new IllegalStateException("El numbero de tarjeta ya existe")))
            .switchIfEmpty(saveNew(model));
    }

    @Override
    public Mono<DebitCard> findByCardNumber(String cardNumber) {
        return port.findByCardNumber(cardNumber);
    }

    private Mono<DebitCard> saveNew(DebitCard model) {
        var url = "http://localhost:8086/api/v1/account/productIdIn";

        return getAccount(url, model.getLinkedAccountIds())
            .flatMap(accounts -> {
                // Filtramos las cuentas no encontradas
                var notFound = model.getLinkedAccountIds().stream()
                        .filter(id -> accounts.stream()
                                .noneMatch(acc -> acc.getProductoId().equals(id))
                        )
                        .toList();

                // Si hay cuentas no encontradas, lanzamos excepción.
                if (!notFound.isEmpty()) {
                    return Mono.error(
                            new RuntimeException("No se encontraron las cuentas: " + String.join(", ", notFound))
                    );
                }

                // Si todo está bien, guardamos la tarjeta
                return port.create(model);
            });
    }


    private Mono<List<AccountInfo>> getAccount(String url, List<String> cardNumbers){
        return webClientBuilder.build()
            .post()
            .uri(url)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(cardNumbers)
            .retrieve()
            .bodyToFlux(AccountInfo.class)
            .collectList()
            .doOnNext(f -> {
                log.info("conexion exitosa al serivicio: {}", url + f);
            })
            .doOnError( err ->  log.info("no se logro la conexion al serivicio: {}", url));
    }

}
