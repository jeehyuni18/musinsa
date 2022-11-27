package com.musinsa.homework.repository.queryDsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class QuerydslConfiguration {
    @PersistenceContext(unitName = "primaryEntityManager")
    EntityManager entityManager;


    @Bean
    public JPAQueryFactory primaryQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

}
