package com.bootcamp.ms_debitcard.infrastructure.adapter.out.persistence.repository.mongodb;

import com.bootcamp.ms_debitcard.infrastructure.adapter.out.persistence.entity.DebitCardEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DebitCardRepository extends ReactiveMongoRepository<DebitCardEntity, String> {

    Mono<DebitCardEntity> findByCardNumber(String cardNumber);
}
