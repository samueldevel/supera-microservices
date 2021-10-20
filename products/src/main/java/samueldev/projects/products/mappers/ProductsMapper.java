package samueldev.projects.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import samueldev.projects.core.domain.Products;
import samueldev.projects.core.requests.ProductsPostRequestBody;
import samueldev.projects.core.requests.ProductsPutRequestBody;


@Mapper(componentModel = "spring")
public abstract class ProductsMapper {
    public static final ProductsMapper INSTANCE = Mappers.getMapper(ProductsMapper.class);

    public abstract Products toProducts(ProductsPostRequestBody productsPostRequestBody);

    public abstract Products toProducts(ProductsPutRequestBody productsPutRequestBody);
}