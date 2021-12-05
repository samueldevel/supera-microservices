package samueldev.projects.products.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samueldev.projects.core.domain.Products;
import samueldev.projects.core.requests.ProductsPostRequestBody;
import samueldev.projects.core.requests.ProductsPutRequestBody;
import samueldev.projects.products.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
@Api(value = "Endpoints to manage products")
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping()
    @ApiOperation(value = "List all available products", response = Products.class)
    public ResponseEntity<List<Products>> findAll() {

        return new ResponseEntity<>(productsService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/pageable")
    @ApiOperation(value = "List all pageable available products", response = Products.class)

    public ResponseEntity<Page<Products>> findAllPageable(Pageable pageable) {

        return new ResponseEntity<>(productsService.findAllPageable(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @ApiOperation(value = "List products with the same order id", response = Products.class)
    public ResponseEntity<Products> findById(@PathVariable Long id) {

        return new ResponseEntity<>(productsService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/name")
    @ApiOperation(value = "List products with the same order name", response = Products.class)
    public ResponseEntity<Page<Products>> findByName(Pageable pageable, @RequestParam String name) {

        return new ResponseEntity<>(productsService.findByName(pageable, name), HttpStatus.OK);
    }

    @GetMapping(path = "/score")
    @ApiOperation(value = "List products with score between max and min", response = Products.class)
    public ResponseEntity<Page<Products>> filterScoreToMaxMin(Pageable pageable, @RequestParam int min, @RequestParam() int max) {
        return new ResponseEntity<>(productsService.filterScoreToMaxMin(pageable, min, max), HttpStatus.OK);
    }

    @GetMapping(path = "/price")
    @ApiOperation(value = "List products with price between max and min", response = Products.class)
    public ResponseEntity<Page<Products>> filterPriceToMaxMin(Pageable pageable, @RequestParam int min, @RequestParam() int max) {
        return new ResponseEntity<>(productsService.filterPriceToMaxMin(pageable, min, max), HttpStatus.OK);
    }

    @PostMapping()
    @ApiOperation(value = "save products with the same order body", response = Products.class)
    public ResponseEntity<Products> save(@RequestBody ProductsPostRequestBody productsPostRequestBody) {

        return new ResponseEntity<>(productsService.save(productsPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    @ApiOperation(value = "delete products with the same order id", response = Products.class)
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        productsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    @ApiOperation(value = "replace products that are the same old id and same order body", response = Products.class)
    public ResponseEntity<Void> replace(@RequestBody ProductsPutRequestBody productsPutRequestBody) {

        productsService.replace(productsPutRequestBody);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
