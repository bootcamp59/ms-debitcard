package com.bootcamp.ms_debitcard.infrastructure.adapter.in.expose;


import com.bootcamp.ms_debitcard.application.port.in.DebitCardUseCase;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.api.DebitcardApi;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.mapper.DebitCardDtoMapper;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.mapper.OpenMapper;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.model.BalanceResponse;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.model.DebitCardDto;
import com.bootcamp.ms_debitcard.infrastructure.adapter.in.model.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class DebitCardController implements DebitcardApi {

    private final DebitCardUseCase usecase;

    @Override
    public Mono<ResponseEntity<DebitCardDto>> findByDebitNumber(String debitNumber, ServerWebExchange exchange) {
        return usecase.findByCardNumber(debitNumber)
            .map( res -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(DebitCardDtoMapper.toDto(res)));
    }

    @Override
    public Mono<ResponseEntity<TransactionDto>> payment(Mono<TransactionDto> transactionDto, ServerWebExchange exchange) {
        return transactionDto.flatMap( openApiDto -> usecase.payment(OpenMapper.toDto(openApiDto)))
        .map( res -> ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(OpenMapper.toOpenApi(res)));

    }


    @Override
    public Mono<ResponseEntity<BalanceResponse>> balancePrincipal(String debitNumber, ServerWebExchange exchange) {
        return usecase.balancePrincipal(debitNumber)
            .map(balance -> ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(balance));
    }

    @Override
    public Mono<ResponseEntity<DebitCardDto>> create(Mono<DebitCardDto> debitCardDto, ServerWebExchange exchange) {
        return debitCardDto.flatMap( dto -> {
            return usecase.create(DebitCardDtoMapper.toModel(dto))
                .map(resp -> ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(DebitCardDtoMapper.toDto(resp)));
        });
    }

}
