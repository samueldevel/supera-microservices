package samueldev.projects.products.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import samueldev.projects.core.domain.Products;
import samueldev.projects.core.repository.ProductsRepository;
import samueldev.projects.products.utils.CreateProducts;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@DataJpaTest()
@DisplayName("Tests for Products samueldev.projects.core.repository")
class ProductsRepositoryTest {

    @Autowired
    private ProductsRepository productsRepository;

    @Test
    @DisplayName("save Persists product when successful")
    void save_PersistsProduct_WhenSuccessful() {
        Products productToBeSaved = CreateProducts.createProductToBeSaved();

        Products productSaved = this.productsRepository.save(productToBeSaved);

        Assertions.assertThat(productToBeSaved).isNotNull();

        Assertions.assertThat(productSaved).isEqualTo(productToBeSaved);
    }

    @Test
    @DisplayName("save Updates product when successful")
    void save_UpdatesProduct_WhenSuccessful() {
        Products productToBeSaved = CreateProducts.createValidProduct();

        Products productSaved = this.productsRepository.save(productToBeSaved);

        productSaved.setName("Mortal Kombat XL");

        Products productUpdated = this.productsRepository.save(productSaved);

        Assertions.assertThat(productSaved).isNotNull();

        Assertions.assertThat(productUpdated).isEqualTo(productSaved);
    }

    @Test
    @DisplayName("delete Remove product when successful")
    void delete_RemoveProduct_WhenSuccessful() {
        Products productToBeSaved = CreateProducts.createProductToBeSaved();

        Products productSaved = this.productsRepository.save(productToBeSaved);

        productsRepository.delete(productSaved);

        Optional<Products> productById = this.productsRepository.findById(productSaved.getId());

        Assertions.assertThat(productById).isEmpty();
    }

    @Test
    @DisplayName("findAll Returns all products saved when successful")
    void findAll_ReturnsAllProductsSaved_WhenSuccessful() {
        Products productToBeSavedOne = CreateProducts.createProductToBeSaved();
        Products productToBeSavedTwo = CreateProducts.createProductToBeSaved();

        Products productSavedOne = this.productsRepository.save(productToBeSavedOne);
        Products productSavedTwo = this.productsRepository.save(productToBeSavedTwo);

        List<Products> allProducts = this.productsRepository.findAll();

        Assertions.assertThat(allProducts)
                .isNotEmpty()
                .contains(productSavedOne)
                .contains(productSavedTwo);

        Assertions.assertThat(allProducts.size())
                .isEqualTo(11);

    }

    @Test
    @DisplayName("findById Returns a product with same id when successful")
    void findById_ReturnsAProductWithSameId_WhenSuccessful() {
        Products productToBeSaved = CreateProducts.createProductToBeSaved();
        Products productSaved = this.productsRepository.save(productToBeSaved);

        Optional<Products> productById = this.productsRepository.findById(productSaved.getId());

        Assertions.assertThat(productById)
                .isNotEmpty()
                .contains(productSaved);
    }

    @Test
    @DisplayName("findByName Returns a list of products with the name found when successful")
    void findByName_ReturnsAListOfProductsWithTheNameFound_WhenSuccessful() {
        Products productToBeSaved = CreateProducts.createProductToBeSaved();

        Products productSaved = this.productsRepository.save(productToBeSaved);

        List<Products> productByName = this.productsRepository.findByName(productSaved.getName());

        Assertions.assertThat(productByName)
                .isNotEmpty()
                .contains(productSaved);

    }

    // ---------------------------------------- EDGE CASES ----------------------------------------

    @Test
    @DisplayName("save Throw ConstraintViolationException when the field name is empty or null")
    void save_ThrowConstraintViolationException_WhenTheFieldNameIsEmptyOrNull() {
        Products emptyNameToEdgeCases =
                CreateProducts.createProductIncompleteToEdgeCases("", 20.00, 70, "Image.png");

        Products nullNameToEdgeCases =
                CreateProducts.createProductIncompleteToEdgeCases(null, 20.00, 70, "Image.png");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        productsRepository.save(emptyNameToEdgeCases))
                .withMessageContaining("the field name cannot be empty");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        productsRepository.save(nullNameToEdgeCases))
                .withMessageContaining("the field name cannot be empty");
    }

    @Test
    @DisplayName("save Throw ConstraintViolationException when the field price have range less one ")
    void save_ThrowConstraintViolationException_WhenTheFieldPriceHaveRangeLessOne() {
        Products nullPriceToEdgeCases =
                CreateProducts.createProductIncompleteToEdgeCases("productName", 0, 70, "Image.png");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        productsRepository.save(nullPriceToEdgeCases))
                .withMessageContaining("the field price need be more than one");
    }

    @Test
    @DisplayName("save Throw ConstraintViolationException when the field score have range less one ")
    void save_ThrowConstraintViolationException_WhenTheFieldScoreHaveRangeLessOne() {
        Products nullScoreToEdgeCases =
                CreateProducts.createProductIncompleteToEdgeCases("productName", 100, 0, "Image.png");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        productsRepository.save(nullScoreToEdgeCases))
                .withMessageContaining("the field score need be more than one");
    }

    @Test
    @DisplayName("save Throw ConstraintViolationException when the field image is empty or null")
    void save_ThrowConstraintViolationException_WhenTheFieldImageIsEmptyOrNull() {
        Products emptyImageToEdgeCases =
                CreateProducts.createProductIncompleteToEdgeCases("productName", 20.00, 70, "");

        Products nullImageToEdgeCases =
                CreateProducts.createProductIncompleteToEdgeCases("productName", 20.00, 70, null);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        productsRepository.save(emptyImageToEdgeCases))
                .withMessageContaining("the field image cannot be empty");

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class).isThrownBy(() ->
                        productsRepository.save(nullImageToEdgeCases))
                .withMessageContaining("the field image cannot be empty");
    }

    @Test
    @DisplayName("findAll Returns empty list when no have products")
    void findAll_ReturnsEmptyList_WhenNoHaveProducts() {
        this.productsRepository.deleteAll();
        List<Products> allProducts = this.productsRepository.findAll();

        Assertions.assertThat(allProducts)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findById Returns empty list when id not found")
    void findById_ReturnsEmptyList_WhenIdNotFound() {
        Optional<Products> emptyList = this.productsRepository.findById(99L);

        Assertions.assertThat(emptyList)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByName Returns empty list when name not found")
    void findByName_ReturnsEmptyList_WhenNameNotFound() {
        List<Products> emptyList = this.productsRepository.findByName("Product");

        Assertions.assertThat(emptyList)
                .isNotNull()
                .isEmpty();
    }
}