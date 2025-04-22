package com.bootcamp.ms_debitcard.application.service;

import com.bootcamp.ms_debitcard.application.port.in.DebitCardUseCase;
import com.bootcamp.ms_debitcard.application.port.out.AccountServicePort;
import com.bootcamp.ms_debitcard.application.port.out.DebitCardRepositoryPort;
import com.bootcamp.ms_debitcard.domain.dto.Transaction;
import com.bootcamp.ms_debitcard.domain.model.DebitCard;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.model.BalanceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebitService implements DebitCardUseCase {

    private final DebitCardRepositoryPort port;
    private final AccountServicePort accountServicePort;

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

    @Override
    public Mono<Transaction> payment(Transaction transaction) {
        return accountServicePort.executePayment(transaction);
    }

    @Override
    public Mono<BalanceResponse> balancePrincipal(String cardNumber) {
        return findByCardNumber(cardNumber)
            .map(DebitCard::getPrimaryAccountId)
            .flatMap(productId -> accountServicePort.getAccountByIds(List.of(productId)))
            .map(accounts -> {
                var acc = accounts.stream().findFirst();
                var resp = new BalanceResponse();
                if(acc.isPresent()){
                    resp.balance(acc.get().getSaldo());
                    resp.productoId(acc.get().getProductoId());
                }
                return resp;
            })
            .switchIfEmpty(Mono.error(new RuntimeException("no se encontro cuenta asociada a la tarjeta principal")));
    }

    private Mono<DebitCard> saveNew(DebitCard model) {

        model.getLinkedAccountIds().add(model.getPrimaryAccountId());

        return accountServicePort.getAccountByIds(model.getLinkedAccountIds())
            .flatMap(accounts -> {
                // Filtramos las cuentas no encontradas
                var notFound = model.getLinkedAccountIds()
                    .stream()
                    .filter(id -> accounts.stream()
                            .noneMatch(acc -> acc.getProductoId().equals(id))
                    ).toList();

                if (!notFound.isEmpty()) {
                    return Mono.error(
                            new RuntimeException("No se encontraron las cuentas: " + String.join(", ", notFound))
                    );
                }

                // Si todo está bien, guardamos la tarjeta
                return port.create(model);
            });
    }


}
