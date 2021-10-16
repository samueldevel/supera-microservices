package samueldev.projects.products.utils;

import domain.Products;

public class CreateProducts {

    public static Products createProductToBeSaved() {
        return Products.builder()
                .name("Call Of Duty Infinite Warfare")
                .price(49.99)
                .score(80)
                .image("call-of-duty-infinite-warfare.png")
                .build();
    }

    public static Products createProductToBeUpdated(Long id) {
        return Products.builder()
                .id(id)
                .name("Call Of Duty Infinite Warfare")
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
