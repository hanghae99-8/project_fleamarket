package hanghae.fleamarket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.QProduct;

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
                .set(product.selectCount, product.selectCount.add(+1))
                .where(product.id.eq(productId))
                .execute();
    }

    @Override //찜하기 갯수 빼기
    public void cancelSelect(Long productId) {
        queryFactory
                .update(product)
                .set(product.selectCount, product.selectCount.add(-1))
                .where(product.id.eq(productId))
                .execute();
    }

    @Override //판매상태 true
    public void soldProduct(Long productId) {
        queryFactory
                .update(product)
                .set(product.isSold, true)
                .where(product.id.eq(productId))
                .execute();
    }


}
