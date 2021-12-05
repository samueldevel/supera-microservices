package samueldev.projects.products.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import samueldev.projects.core.domain.Products;

import java.util.List;
import java.util.stream.Collectors;

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
                .score(80)
                .price(49.99)
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

    public static Page<Products> createProductByFilteringPriceByMinMax(int min, int max) {
        PageImpl<Products> productsPage = new PageImpl<>(List.of(createValidProduct()));

        List<Products> productFiltered = productsPage.stream().filter((products -> products.getPrice() > min && products.getPrice() < max)).collect(Collectors.toList());

        return new PageImpl<>(productFiltered);
    }

    public static Page<Products> createProductByFilteringScoreByMinMax(int min, int max) {
        PageImpl<Products> productsPage = new PageImpl<>(List.of(createValidProduct()));

        List<Products> productFiltered = productsPage.stream().filter((products -> products.getScore() > min && products.getScore() < max)).collect(Collectors.toList());

        return new PageImpl<>(productFiltered);
    }
}