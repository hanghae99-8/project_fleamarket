package hanghae.fleamarket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import static hanghae.fleamarket.entity.QProduct.product;

public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final JPAQueryFactory queryFactory;

    public CustomProductRepositoryImpl(EntityManager em) {
        queryFactory = new JPAQueryFactory(em);
    }

    @Override //찜하기 갯수 추가
    public void selectProduct(Long productId) {
        queryFactory
                .update(product)
                .set(product.select_count, product.select_count.add(+1))
                .where(product.id.eq(productId))
                .execute();
    }

    @Override //찜하기 갯수 빼기
    public void cancelSelect(Long productId) {
        queryFactory
                .update(product)
                .set(product.select_count, product.select_count.add(-1))
                .where(product.id.eq(productId))
                .execute();
    }


}
