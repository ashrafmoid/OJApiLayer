package com.ashraf.ojapilayer.resolver;

import com.ashraf.ojapilayer.DTO.PaginatedDTO;
import com.ashraf.ojapilayer.visitor.RSQLVisitorFactory;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collections;
import java.util.List;

@Component
@Log4j2
@RequiredArgsConstructor
public class RSQLBasedQueryResolver implements QueryResolver {
    private final EntityManager entityManager;

    @Override
    public <T> PaginatedDTO<T> getResultsForQuery(String queryString, Class<T> clazz, Pageable pageable) {
        RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor = RSQLVisitorFactory.getRSQLVisitor(clazz);
        CriteriaQuery<T> query = getCriteriaQuery(queryString, visitor);
        List<T> resultList = entityManager.createQuery(query).setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList();
        if (CollectionUtils.isEmpty(resultList)) {
            resultList = Collections.emptyList();
        }
        return getPaginatedResponse(resultList, pageable, clazz);
    }

    private <T> CriteriaQuery<T> getCriteriaQuery(String queryString, RSQLVisitor<CriteriaQuery<T>, EntityManager> visitor) {
        Node rootNode;
        CriteriaQuery<T> query;
        try {
            rootNode = new RSQLParser().parse(queryString);
            query = rootNode.accept(visitor, entityManager);
        }catch (Exception e){
            log.error("An error happened while executing RSQL query", e);
            throw new IllegalArgumentException(e.getMessage());
        }
        return query;
    }

    private <T> PaginatedDTO<T> getPaginatedResponse(List<T> resultList, Pageable pageable, Class<T> clazz) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder
                .createQuery(Long.class);
        countQuery.select(criteriaBuilder
                .count(countQuery.from(clazz)));
        Long totalRowCount = entityManager.createQuery(countQuery)
                .getSingleResult();
        Integer size = pageable.getPageSize();
        int totalPages = Math.toIntExact(totalRowCount / size) + (totalRowCount % size == 0 ? 0 : 1);
        return PaginatedDTO.<T>builder().content(resultList)
                .totalPages(totalPages)
                .totalElements(resultList.size())
                .pageNumber(pageable.getPageNumber())
                .size(size)
                .isLast(totalPages == pageable.getPageNumber())
                .build();
    }
}
