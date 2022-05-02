package com.ashraf.ojapilayer.visitor;

import com.ashraf.ojapilayer.entity.BaseEntity;
import com.ashraf.ojapilayer.entity.Question;
import com.ashraf.ojapilayer.entity.Submission;
import com.github.tennaito.rsql.jpa.JpaCriteriaQueryVisitor;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RSQLVisitorFactory {

    private static final Map<Class<? extends BaseEntity>, Object> map = new HashMap<>(){{
        put(Submission.class, new JpaCriteriaQueryVisitor <Submission>());
        put(Question.class,  new JpaCriteriaQueryVisitor<Question>());
    }};

    public static <T> RSQLVisitor<CriteriaQuery<T>, EntityManager> getRSQLVisitor(Class<T> clazz) {
        return (RSQLVisitor<CriteriaQuery<T>, EntityManager>) map.get(clazz);
    }
}
