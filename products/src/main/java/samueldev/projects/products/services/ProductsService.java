package samueldev.projects.products.services;

import samueldev.projects.core.domain.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import samueldev.projects.core.repository.ProductsRepository;
import samueldev.projects.core.requests.ProductsPostRequestBody;
import samueldev.projects.core.requests.ProductsPutRequestBody;
import samueldev.projects.products.mappers.ProductsMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public Page<Products> findAllPageable(Pageable pageable) {

        return productsRepository.findAll(pageable);
    }

    public List<Products> findAll() {

        return productsRepository.findAll();
    }

    public Products findById(Long id) {

        return productsRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found!"));
    }

    public List<Products> findByName(String name) {

        return productsRepository.findByName(name);
    }

    public Products save(ProductsPostRequestBody productsPostRequestBody) {
        Products productMapped = ProductsMapper.INSTANCE.toProducts(productsPostRequestBody);
        return productsRepository.save(productMapped);
    }

    public void delete(Long id) {
        Products productSaved = findById(id);

        productsRepository.delete(productSaved);
    }

    public void replace(ProductsPutRequestBody productsPutRequestBody) {
        Products productSaved = findById(productsPutRequestBody.getId());

        Products productToBeSaved = ProductsMapper.INSTANCE.toProducts(productsPutRequestBody);
        productToBeSaved.setId(productSaved.getId());

        productsRepository.save(productToBeSaved);
    }

}
