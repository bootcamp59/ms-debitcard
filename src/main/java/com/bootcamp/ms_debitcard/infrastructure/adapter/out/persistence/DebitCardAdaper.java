package com.bootcamp.ms_debitcard.infrastructure.adapter.out.persistence;

import com.bootcamp.ms_debitcard.application.port.out.DebitCardRepositoryPort;
import com.bootcamp.ms_debitcard.domain.model.DebitCard;
import com.bootcamp.ms_debitcard.infrastructure.adapter.out.persistence.mapper.DebitCardEntityMapper;
import com.bootcamp.ms_debitcard.infrastructure.adapter.out.persistence.repository.mongodb.DebitCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class DebitCardAdaper implements DebitCardRepositoryPort {

    private final DebitCardRepository repository;

    @Override
    public Mono<DebitCard> create(DebitCard model) {
        return repository.save(DebitCardEntityMapper.toEntity(model))
            .map(DebitCardEntityMapper::toModel);
    }

    @Override
    public Mono<DebitCard> findByCardNumber(String cardNumber) {
        return repository.findByCardNumber(cardNumber)
            .map(DebitCardEntityMapper::toModel);
    }
}
