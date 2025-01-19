package com.mindgoal.domain.expert.repository;

import com.mindgoal.domain.expert.dto.ExpertSearchCondition;
import com.mindgoal.domain.expert.entity.Expert;
import com.mindgoal.domain.expert.entity.QExpert;
import com.mindgoal.domain.user.entity.QUser;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import static com.querydsl.core.types.Order.ASC;
import static com.querydsl.core.types.Order.DESC;

import java.util.List;

@RequiredArgsConstructor
public class ExpertRepositoryImpl implements ExpertRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Expert> findAllByFilters(ExpertSearchCondition condition, Pageable pageable) {
        QExpert expert = QExpert.expert;
        QUser user = QUser.user;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(expert.isActive.isTrue());

        if (StringUtils.hasText(condition.getCategory())) {
            builder.and(expert.category.eq(condition.getCategory()));
        }

        if (StringUtils.hasText(condition.getPosition())) {
            builder.and(expert.position.eq(condition.getPosition()));
        }

        if (StringUtils.hasText(condition.getRegion())) {
            builder.and(expert.region.eq(condition.getRegion()));
        }

        if (condition.getMinCareerYears() != null) {
            builder.and(expert.careerYears.goe(condition.getMinCareerYears()));
        }

        if (condition.getMaxCareerYears() != null) {
            builder.and(expert.careerYears.loe(condition.getMaxCareerYears()));
        }

        if (condition.getMinRating() != null) {
            builder.and(expert.rating.goe(condition.getMinRating()));
        }

        if (condition.getMinPrice() != null) {
            builder.and(expert.pricePerHour.goe(condition.getMinPrice()));
        }

        if (condition.getMaxPrice() != null) {
            builder.and(expert.pricePerHour.loe(condition.getMaxPrice()));
        }

        JPAQuery<Expert> query = queryFactory
                .selectFrom(expert)
                .join(user).on(expert.userId.eq(user.id))
                .where(builder);

        long total = query.fetch().size();

        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                PathBuilder<Expert> pathBuilder = new PathBuilder<>(Expert.class, "expert");
                query.orderBy(new OrderSpecifier(
                        order.isAscending() ? ASC : DESC,
                        pathBuilder.get(order.getProperty())
                ));
            }
        } else {
            query.orderBy(expert.rating.desc(), expert.matchCount.desc());
        }

        List<Expert> experts = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(experts, pageable, total);
    }
}