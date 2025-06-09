package fastcampus.ecommerce.api.service.product;

import fastcampus.ecommerce.api.domain.product.Product;
import fastcampus.ecommerce.api.dto.product.ProductResult;
import fastcampus.ecommerce.api.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public ProductResult findProduct(String productId) {
    Product product = findProductById(productId);
    return ProductResult.from(product);
  }

  private Product findProductById(String productId) {
    return productRepository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(productId));
  }

  public Page<ProductResult> getAllProducts(Pageable pageable) {
    return productRepository.findAll(pageable).map(ProductResult::from);
  }

  @Transactional
  public void decreaseStock(String productId, int stockQuantity) {
    Product product = findProductById(productId);
    product.decreaseStock(stockQuantity);
    productRepository.save(product);
  }

  @Transactional
  public void increaseStock(String productId, int stockQuantity) {
    Product product = findProductById(productId);
    product.increaseStock(stockQuantity);
    productRepository.save(product);
  }


}
