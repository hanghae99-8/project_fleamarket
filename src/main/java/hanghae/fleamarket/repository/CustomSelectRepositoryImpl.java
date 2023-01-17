package hanghae.fleamarket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.*;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static hanghae.fleamarket.entity.QProduct.product;
import static hanghae.fleamarket.entity.QSelects.selects;
import static hanghae.fleamarket.entity.QUser.user;

public class CustomSelectRepositoryImpl implements CustomSelectRepository{

    private final JPAQueryFactory queryFactory;

    public CustomSelectRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override  // 찜하기 상태 false
    public void cancelSelect(Long selectId) {
        queryFactory
                .update(selects)
                .set(selects.status, false)
                .where(selects.id.eq(selectId))
                .execute();
    }

    @Override //찜하기 상태 true
    public void selectProduct(Long selectId) {
        queryFactory
                .update(selects)
                .set(selects.status, true)
                .where(selects.id.eq(selectId))
                .execute();
    }

    @Override //사용자id로 찜하기 목록 가져오기
    public List<ProductResponseDto> findAllByUserId(Long userId) {
        List<Selects> selectsList = queryFactory
                .selectFrom(selects).distinct()
                .join(selects.user, user).fetchJoin()
                .leftJoin(selects.product, product).fetchJoin()
                .where(selects.user.id.eq(userId))
                .fetch();


        List<ProductResponseDto> response = new ArrayList<>();

        for (Selects selectsOne : selectsList) {
            response.add(new ProductResponseDto(selectsOne.getProduct()));
        }
        return response;
    }
}
