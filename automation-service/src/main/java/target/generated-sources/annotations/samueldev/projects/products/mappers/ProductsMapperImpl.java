package samueldev.projects.products.mappers;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import samueldev.projects.core.domain.Products;
import samueldev.projects.core.domain.Products.ProductsBuilder;
import samueldev.projects.core.requests.ProductsPostRequestBody;
import samueldev.projects.core.requests.ProductsPutRequestBody;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-10-27T20:41:19-0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.12 (Oracle Corporation)"
)
@Component
public class ProductsMapperImpl extends ProductsMapper {

    @Override
    public Products toProducts(ProductsPostRequestBody productsPostRequestBody) {
        if ( productsPostRequestBody == null ) {
            return null;
        }

        ProductsBuilder products = Products.builder();

        products.name( productsPostRequestBody.getName() );
        products.price( productsPostRequestBody.getPrice() );
        products.score( productsPostRequestBody.getScore() );
        products.image( productsPostRequestBody.getImage() );

        return products.build();
    }

    @Override
    public Products toProducts(ProductsPutRequestBody productsPutRequestBody) {
        if ( productsPutRequestBody == null ) {
            return null;
        }

        ProductsBuilder products = Products.builder();

        products.id( productsPutRequestBody.getId() );
        products.name( productsPutRequestBody.getName() );
        products.price( productsPutRequestBody.getPrice() );
        products.score( productsPutRequestBody.getScore() );
        products.image( productsPutRequestBody.getImage() );

        return products.build();
    }
}
