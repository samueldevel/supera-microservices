package samueldev.projects.products.controller;

import domain.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import samueldev.projects.products.requests.ProductsPostRequestBody;
import samueldev.projects.products.requests.ProductsPutRequestBody;
import samueldev.projects.products.services.ProductsService;

import java.util.List;

@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductsController {
    private final ProductsService productsService;

    @GetMapping()
    public ResponseEntity<List<Products>> findAll() {

        return new ResponseEntity<>(productsService.findAll(), HttpStatus.FOUND);
    }

    @GetMapping(path = "/pageable")
    public ResponseEntity<Page<Products>> findAllPageables(Pageable pageable) {

        return new ResponseEntity<>(productsService.findAllPageables(pageable), HttpStatus.FOUND);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Products> findById(@PathVariable Long id) {

        return new ResponseEntity<>(productsService.findById(id), HttpStatus.FOUND);
    }

    @GetMapping(path = "/name")
    public ResponseEntity<List<Products>> findById(@RequestParam String name) {

        return new ResponseEntity<>(productsService.findByName(name), HttpStatus.FOUND);
    }

    @PostMapping()
    public ResponseEntity<Products> save(@RequestBody ProductsPostRequestBody productsPostRequestBody) {

        return new ResponseEntity<>(productsService.save(productsPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        productsService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    public ResponseEntity<Void> replace(@RequestBody ProductsPutRequestBody productsPutRequestBody) {

        productsService.replace(productsPutRequestBody);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
