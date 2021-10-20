package samueldev.projects.products.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import samueldev.projects.core.domain.Products;
import samueldev.projects.core.repository.ProductsRepository;
import samueldev.projects.products.utils.CreateProducts;
import samueldev.projects.products.utils.CreateProductsRequestBody;
import samueldev.projects.products.utils.ValidatingProductFields;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Products services")
class ProductsServiceTest {
    @InjectMocks
    private ProductsService productsService;

    @Mock
    private ProductsRepository productsRepositoryMock;

    @BeforeEach
    void setup() {
        PageImpl<Products> productPage = new PageImpl<>(List.of(CreateProducts.createValidProduct()));

        BDDMockito.when(productsRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(productPage);

        BDDMockito.when(productsRepositoryMock.findAll())
                .thenReturn(List.of(CreateProducts.createValidProduct()));

        BDDMockito.when(productsRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(CreateProducts.createValidProduct()));

        BDDMockito.when(productsRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(CreateProducts.createValidProduct()));

        BDDMockito.when(productsRepositoryMock.save(ArgumentMatchers.any(Products.class)))
                .thenReturn(CreateProducts.createValidProduct());

        BDDMockito.doNothing().when(productsRepositoryMock).delete(ArgumentMatchers.any(Products.class));
    }

    @Test
    @DisplayName("findAllPageable returns list of pageable products when successful")
    void findAllPageable_ReturnsListOfPageableProducts_WhenSuccessful() {
        Products validProduct = CreateProducts.createValidProduct();

        Page<Products> productPage = productsService.findAllPageable(PageRequest.of(1, 1));

        Assertions.assertThat(productPage).isNotNull();
        Assertions.assertThat(productPage.toList())
                .hasSize(1);

        Assertions.assertThat(productPage.toList().get(0))
                .isEqualTo(validProduct);
    }

    @Test
    @DisplayName("findAllNonPageable returns list of products when successful")
    void findAllNonPageable_ReturnsListOfProducts_WhenSuccessful() {
        Products validProduct = CreateProducts.createValidProduct();

        List<Products> productNonPageable = productsService.findAll();

        Assertions.assertThat(productNonPageable)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(productNonPageable.get(0))
                .isEqualTo(validProduct);
    }

    @Test
    @DisplayName("findById returns product containing request id when successful")
    void findById_ReturnsProductContainingRequestId_WhenSuccessful() {
        Products validProduct = CreateProducts.createValidProduct();

        Products productById = productsService.findById(validProduct.getId());

        Assertions.assertThat(productById)
                .isNotNull()
                .isEqualTo(validProduct);

        Assertions.assertThat(productById.getId())
                .isNotNull()
                .isEqualTo(validProduct.getId());
    }

    @Test
    @DisplayName("findByName returns product containing request name when successful")
    void findByName_ReturnsProductContainingRequestName_WhenSuccessful() {
        Products validProduct = CreateProducts.createValidProduct();

        List<Products> productByName = productsService.findByName(validProduct.getName());

        Assertions.assertThat(productByName)
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(productByName.get(0))
                .isEqualTo(validProduct);
    }

    @Test
    @DisplayName("save returns product containing request body when successful")
    void save_ReturnsProductContainingRequestBody_WhenSuccessful() {
        Products productSaved = productsService.save(CreateProductsRequestBody.createProductsPostRequestBody());

        Assertions.assertThat(productSaved)
                .isNotNull()
                .isEqualTo(CreateProducts.createValidProduct());

        ValidatingProductFields.ValidateProductFields(productSaved);
    }

    @Test
    @DisplayName("replace update product containing request body when successful")
    void replace_UpdateProductContainingRequestBody_WhenSuccessful() {

        Assertions.assertThatCode(() -> productsService.replace(CreateProductsRequestBody.createProductsPutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete remove product with request id when successful")
    void delete_RemoveProductWithRequestId_WhenSuccessful() {

        Assertions.assertThatCode(() -> productsService.delete(1L))
                .doesNotThrowAnyException();

    }

    // ---------------------------------------- EDGE CASES ----------------------------------------

    @Test
    @DisplayName("findAllPageable returns empty pageable list when no have products")
    void findAllPageable_ReturnsEmptyPageableList_WhenNoHaveProducts() {
        BDDMockito.when(productsRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(Page.empty());

        Page<Products> productPageable = productsService.findAllPageable(PageRequest.of(1, 1));

        Assertions.assertThat(productPageable)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findAllNonPageable returns empty list when no have products")
    void findAllNonPageable_ReturnsEmptyList_WhenNoHaveProducts() {
        BDDMockito.when(productsRepositoryMock.findAll())
                .thenReturn(Collections.emptyList());

        List<Products> productNonPageable = productsService.findAll();

        Assertions.assertThat(productNonPageable)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByName returns empty list when product not found")
    void findById_ReturnsEmptyList_WhenProductNotFound() {
        BDDMockito.when(productsRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<Products> productByName = productsService.findByName("Product");

        Assertions.assertThat(productByName)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findById throws RuntimeException when id not found")
    void findById_ThrowsRuntimeException_WhenIdNotFound() {
        BDDMockito.when(productsRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> productsService.findById(1L))
                .withMessageContaining("Id not found!");
    }

}