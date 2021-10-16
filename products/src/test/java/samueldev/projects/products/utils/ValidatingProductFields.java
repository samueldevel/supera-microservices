package samueldev.projects.products.utils;

import domain.Products;
import org.assertj.core.api.Assertions;

public class ValidatingProductFields {
    public static void ValidateProductFields(Products fromProduct) {
        Assertions.assertThat(fromProduct.getId()).isNotZero().isNotNull();
        Assertions.assertThat(fromProduct.getName()).isNotEmpty().isNotNull();
        Assertions.assertThat(fromProduct.getPrice()).isNotZero().isNotNull();
        Assertions.assertThat(fromProduct.getScore()).isNotZero().isNotNull();
        Assertions.assertThat(fromProduct.getImage()).isNotEmpty().isNotNull();
    }
}
