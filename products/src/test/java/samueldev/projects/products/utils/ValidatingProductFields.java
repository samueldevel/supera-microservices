package samueldev.projects.products.utils;

import org.assertj.core.api.Assertions;
import samueldev.projects.core.domain.Products;

public class ValidatingProductFields {
    public static void ValidateProductFields(Products fromProduct) {
        Assertions.assertThat(fromProduct.getId()).isNotZero().isNotNull();
        Assertions.assertThat(fromProduct.getName()).isNotEmpty().isNotNull();
        Assertions.assertThat(fromProduct.getPrice()).isNotZero().isNotNull();
        Assertions.assertThat(fromProduct.getScore()).isNotZero().isNotNull();
        Assertions.assertThat(fromProduct.getImage()).isNotEmpty().isNotNull();
    }
}
