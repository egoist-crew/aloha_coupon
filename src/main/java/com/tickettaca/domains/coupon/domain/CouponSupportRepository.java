package com.tickettaca.domains.coupon.domain;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tickettaca.domains.coupon.application.dto.CouponHistoryResponse;
import com.tickettaca.domains.coupon.application.dto.CouponListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CouponSupportRepository {
  private QCouponEntity couponEntity = QCouponEntity.couponEntity;
  private final JPAQueryFactory queryFactory;

  public List<CouponListResponse> list(String userToken) {
    return queryFactory
        .select(
            Projections.constructor(
                CouponListResponse.class,
                couponEntity.status.as("state"),
                couponEntity.issueDate.as("issueDate"),
                couponEntity.expirationDate.as("deadLine"),
                couponEntity.bookEntity.contents.as("desc"),
                couponEntity.bookEntity.name,
                couponEntity.userEntity.id.as("ownSeq"), // 받는 사람
                couponEntity.id.as("seq"))) // 쿠폰 식별자
        .from(couponEntity)
        .where(couponEntity.userToken.eq(userToken))
        .fetch();
  }

  public List<CouponHistoryResponse> history(String userToken) {
    return queryFactory
        .select(
            Projections.constructor(
                CouponHistoryResponse.class,
                couponEntity.status.as("state"),
                couponEntity.issueDate.as("issueDate"),
                couponEntity.expirationDate.as("deadLine"),
                couponEntity.bookEntity.contents.as("desc"),
                couponEntity.bookEntity.name,
                couponEntity.userEntity.id.as("ownSeq"), // 받는 사람
                couponEntity.id.as("seq"))) // 쿠폰 식별자
        .from(couponEntity)
        .where(
            couponEntity
                .userToken
                .eq(userToken)
                .and(couponEntity.status.eq(CouponStatus.AFTER_USE)))
        .fetch();
  }
}
