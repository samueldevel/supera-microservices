package samueldev.projects.products.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import samueldev.projects.core.domain.Products;
import samueldev.projects.core.requests.ProductsPostRequestBody;
import samueldev.projects.core.requests.ProductsPutRequestBody;
import samueldev.projects.products.services.ProductsService;
import samueldev.projects.products.utils.CreateProducts;
import samueldev.projects.products.utils.CreateProductsRequestBody;
import samueldev.projects.products.utils.ValidatingProductFields;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Products controller")
class ProductsControllerTest {
    @InjectMocks
    private ProductsController productsController;

    @Mock
    private ProductsService productsServiceMock;

    @BeforeEach
    void setup() {
        PageImpl<Products> productPage = new PageImpl<>(List.of(CreateProducts.createValidProduct()));

        BDDMockito.when(productsServiceMock.findAllPageable(ArgumentMatchers.any()))
                .thenReturn(productPage);

        BDDMockito.when(productsServiceMock.findAll())
                .thenReturn(List.of(CreateProducts.createValidProduct()));

        BDDMockito.when(productsServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(CreateProducts.createValidProduct());

        BDDMockito.when(productsServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(CreateProducts.createValidProduct()));

        BDDMockito.when(productsServiceMock.save(ArgumentMatchers.any(ProductsPostRequestBody.class)))
                .thenReturn(CreateProducts.createValidProduct());

        BDDMockito.doNothing().when(productsServiceMock).replace(ArgumentMatchers.any(ProductsPutRequestBody.class));

        BDDMockito.doNothing().when(productsServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("findAllPageable returns list of pageable products when successful")
    void findAllPageable_ReturnsListOfPageableProducts_WhenSuccessful() {
        Products validProduct = CreateProducts.createValidProduct();

        ResponseEntity<Page<Products>> entityPageable = productsController.findAllPageable(null);

        Assertions.assertThat(entityPageable.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(entityPageable.getBody()).isNotNull();
        Assertions.assertThat(entityPageable.getBody().toList())
                .hasSize(1);

        Assertions.assertThat(entityPageable.getBody().toList().get(0))
                .isEqualTo(validProduct);
    }

    @Test
    @DisplayName("findAllNonPageable returns list of products when successful")
    void findAllNonPageable_ReturnsListOfProducts_WhenSuccessful() {
        Products validProduct = CreateProducts.createValidProduct();

        ResponseEntity<List<Products>> entityNonPageable = productsController.findAll("id", "admin");

        Assertions.assertThat(entityNonPageable.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(entityNonPageable.getBody())
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(entityNonPageable.getBody().get(0))
                .isEqualTo(validProduct);
    }

    @Test
    @DisplayName("findById returns product containing request id when successful")
    void findById_ReturnsProductContainingRequestId_WhenSuccessful() {
        Products validProduct = CreateProducts.createValidProduct();

        ResponseEntity<Products> entityById = productsController.findById(validProduct.getId());

        Assertions.assertThat(entityById.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(entityById.getBody())
                .isNotNull()
                .isEqualTo(validProduct);

        Assertions.assertThat(entityById.getBody().getId())
                .isNotNull()
                .isEqualTo(validProduct.getId());
    }

    @Test
    @DisplayName("findByName returns product containing request name when successful")
    void findByName_ReturnsProductContainingRequestName_WhenSuccessful() {
        Products validProduct = CreateProducts.createValidProduct();

        ResponseEntity<List<Products>> entityByName = productsController.findByName(validProduct.getName());

        Assertions.assertThat(entityByName.getStatusCode()).isEqualTo(HttpStatus.OK);

        Assertions.assertThat(entityByName.getBody())
                .isNotNull()
                .hasSize(1);

        Assertions.assertThat(entityByName.getBody().get(0))
                .isEqualTo(validProduct);
    }

    @Test
    @DisplayName("save returns product containing request body when successful")
    void save_ReturnsProductContainingRequestBody_WhenSuccessful() {
        ResponseEntity<Products> entityProduct = productsController.save(CreateProductsRequestBody.createProductsPostRequestBody());

        Assertions.assertThat(entityProduct.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Assertions.assertThat(entityProduct.getBody())
                .isNotNull()
                .isEqualTo(CreateProducts.createValidProduct());

        ValidatingProductFields.ValidateProductFields(entityProduct.getBody());
    }

    @Test
    @DisplayName("replace update product containing request body when successful")
    void replace_UpdateProductContainingRequestBody_WhenSuccessful() {

        Assertions.assertThatCode(() -> productsController.replace(CreateProductsRequestBody.createProductsPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = productsController.replace(CreateProductsRequestBody.createProductsPutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    @DisplayName("delete remove product with request id when successful")
    void delete_RemoveProductWithRequestId_WhenSuccessful() {

        Assertions.assertThatCode(() -> productsController.delete(1L))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = productsController.delete(1L);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    // ---------------------------------------- EDGE CASES ----------------------------------------

    @Test
    @DisplayName("findAllPageable returns empty pageable list when no have products")
    void findAllPageable_ReturnsEmptyPageableList_WhenNoHaveProducts() {
        BDDMockito.when(productsServiceMock.findAllPageable(ArgumentMatchers.any()))
                .thenReturn(Page.empty());

        ResponseEntity<Page<Products>> entityPageable = productsController.findAllPageable(null);

        Assertions.assertThat(entityPageable)
                .isNotNull();

        Assertions.assertThat(entityPageable.getBody())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findAllNonPageable returns empty list when no have products")
    void findAllNonPageable_ReturnsEmptyList_WhenNoHaveProducts() {
        BDDMockito.when(productsServiceMock.findAll())
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<Products>> entityNonPageable = productsController.findAll("id", "admin");

        Assertions.assertThat(entityNonPageable.getBody())
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByName returns empty list when product not found")
    void findById_ReturnsEmptyList_WhenProductNotFound() {
        BDDMockito.when(productsServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<Products>> entityProduct = productsController.findByName("Product");

        Assertions.assertThat(entityProduct.getBody())
                .isNotNull()
                .isEmpty();
    }
}