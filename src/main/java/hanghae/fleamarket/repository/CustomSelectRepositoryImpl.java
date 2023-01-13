package hanghae.fleamarket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.*;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static hanghae.fleamarket.entity.QProduct.product;
import static hanghae.fleamarket.entity.QSelect.select;
import static hanghae.fleamarket.entity.QUser.user;

public class CustomSelectRepositoryImpl implements CustomSelectRepository{

    private final JPAQueryFactory queryFactory;

    public CustomSelectRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override  // 찜하기 상태 false
    public void cancelSelect(Long selectId) {
        queryFactory
                .update(select)
                .set(select.status, false)
                .where(select.id.eq(selectId))
                .execute();
    }

    @Override //찜하기 상태 true
    public void selectProduct(Long selectId) {
        queryFactory
                .update(select)
                .set(select.status, true)
                .where(select.id.eq(selectId))
                .execute();
    }

    @Override //사용자id로 찜하기 목록 가져오기
    public List<ProductResponseDto> findAllByUserId(Long userId) {
        List<Select> selectList = queryFactory
                .selectFrom(select).distinct()
                .join(select.user, user).fetchJoin()
                .leftJoin(select.product, product).fetchJoin()
                .where(select.user.id.eq(userId))
                .fetch();


        List<ProductResponseDto> response = new ArrayList<>();

        for (Select selectOne : selectList) {
            response.add(new ProductResponseDto(selectOne.getProduct()));
        }
        return response;
    }
}
