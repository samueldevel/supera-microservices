package samueldev.projects.products.utils;

import samueldev.projects.core.domain.Products;

public class CreateProducts {

    public static Products createProductToBeSaved() {
        return Products.builder()
                .name("Call Of Duty Infinite Warfare")
                .price(49.99)
                .score(80)
                .image("call-of-duty-infinite-warfare.png")
                .build();
    }

    public static Products createValidProduct() {
        return Products.builder()
                .id(1L)
                .name("Call Of Duty Infinite Warfare")
                .price(49.99)
                .score(80)
                .image("call-of-duty-infinite-warfare.png")
                .build();
    }

    public static Products createValidUpdateProduct() {
        return Products.builder()
                .id(1L)
                .name("Call Of Duty Infinite Warfare II")
                .price(49.99)
                .score(80)
                .image("call-of-duty-infinite-warfare.png")
                .build();
    }

    public static Products createProductIncompleteToEdgeCases(String name, double price, int score, String image) {
        return Products.builder()
                .name(name)
                .price(price)
                .score(score)
                .image(image)
                .build();
    }
}
