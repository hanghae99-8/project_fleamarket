package hanghae.fleamarket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghae.fleamarket.dto.BuyResponseDto;
import hanghae.fleamarket.entity.Buy;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static hanghae.fleamarket.entity.QBuy.buy;
import static hanghae.fleamarket.entity.QProduct.product;
import static hanghae.fleamarket.entity.QUser.user;

public class CustomBuyRepositoryImpl implements CustomBuyRepository{

    private final JPAQueryFactory queryFactory;

    public CustomBuyRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override //사용자id로 구매내역 조회
    public List<Buy> findByUserId(Long userId) {
        return queryFactory
                .selectFrom(buy).distinct()
                .join(buy.user, user).fetchJoin()
                .leftJoin(buy.product, product).fetchJoin()
                .where(buy.user.id.eq(userId))
                .orderBy(buy.createdAt.desc())
                .fetch();
    }

    private BuyResponseDto convertToBuyResponseDto(Buy buy) {
        return new BuyResponseDto(buy);
    }
}
