package samueldev.projects.products.utils;

import requests.ProductsPostRequestBody;
import requests.ProductsPutRequestBody;

public class CreateProductsRequestBody {
    public static ProductsPostRequestBody createProductsPostRequestBody() {
        return ProductsPostRequestBody.builder()
                .name(CreateProducts.createValidProduct().getName())
                .price(CreateProducts.createValidProduct().getPrice())
                .score(CreateProducts.createValidProduct().getScore())
                .image(CreateProducts.createValidProduct().getImage())
                .build();
    }

    public static ProductsPutRequestBody createProductsPutRequestBody() {
        return ProductsPutRequestBody.builder()
                .id(CreateProducts.createValidUpdateProduct().getId())
                .name(CreateProducts.createValidUpdateProduct().getName())
                .price(CreateProducts.createValidUpdateProduct().getPrice())
                .score(CreateProducts.createValidUpdateProduct().getScore())
                .image(CreateProducts.createValidUpdateProduct().getImage())
                .build();
    }
}
