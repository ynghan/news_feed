package com.sparta.springprepare;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.springprepare.domain.Comment;
import com.sparta.springprepare.domain.QComment;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SpringPrepareApplicationTests {

    @Autowired
    EntityManager em;

    @Test
    void contextLoads() {
        Comment comment = new Comment();
        em.persist(comment);

        JPAQueryFactory query = new JPAQueryFactory(em);
        QComment qComment = new QComment("C");

        Comment result = query.selectFrom(qComment).fetchOne();

        assertThat(result).isEqualTo(comment);
        assertThat(result.getId()).isEqualTo(comment.getId());

    }

}
