package samueldev.projects.products.mappers;

import domain.Products;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import samueldev.projects.products.requests.ProductsPostRequestBody;
import samueldev.projects.products.requests.ProductsPutRequestBody;


@Mapper(componentModel = "spring")
public abstract class ProductsMapper {
    public static final ProductsMapper INSTANCE = Mappers.getMapper(ProductsMapper.class);

    public abstract Products toProducts(ProductsPostRequestBody productsPostRequestBody);

    public abstract Products toProducts(ProductsPutRequestBody productsPutRequestBody);
}