package hanghae.fleamarket.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import hanghae.fleamarket.dto.ProductResponseDto;
import hanghae.fleamarket.entity.Product;
import hanghae.fleamarket.entity.QComment;
import hanghae.fleamarket.entity.QProduct;
import hanghae.fleamarket.entity.QUser;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static hanghae.fleamarket.entity.QComment.comment;
import static hanghae.fleamarket.entity.QProduct.product;
import static hanghae.fleamarket.entity.QUser.user;

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

    @Override
    public List<ProductResponseDto> getProducts() {
        List<Product> productList = queryFactory
                .selectFrom(product)
                .join(product.user, user).fetchJoin()
                .leftJoin(product.comments, comment).fetchJoin()
                .orderBy(product.createdAt.desc())
                .fetch();

        List<ProductResponseDto> response = new ArrayList<>();

        for (Product product1 : productList) {
            response.add(new ProductResponseDto(product1));
        }
        return response;
    }


}
