package samueldev.projects.products.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import samueldev.projects.core.domain.Products;
import samueldev.projects.core.repository.ProductsRepository;
import samueldev.projects.core.requests.ProductsPostRequestBody;
import samueldev.projects.core.requests.ProductsPutRequestBody;
import samueldev.projects.products.mappers.ProductsMapper;

import java.util.List;
import java.util.stream.Collectors;

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

    public Page<Products> findByName(Pageable pageable, String name) {
        List<Products> productsFilteredByName = findAllPageable(pageable).stream().filter(
                (product) -> product.getName().contains(name)).collect(Collectors.toList());

        return new PageImpl<>(productsFilteredByName);
    }

    public Page<Products> filterScoreToMaxMin(Pageable pageable, int min, int max) {
        List<Products> productsFilteredByScore = findAllPageable(pageable).stream().filter(
                (product) -> product.getScore() > min && product.getScore() < max).collect(Collectors.toList());

        return new PageImpl<>(productsFilteredByScore);
    }

    public Page<Products> filterPriceToMaxMin(Pageable pageable, int min, int max) {
        List<Products> productsFilteredByPrice = findAllPageable(pageable).stream().filter(
                (product -> product.getPrice() >= min && product.getPrice() <= max)).collect(Collectors.toList());

        return new PageImpl<>(productsFilteredByPrice);
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
