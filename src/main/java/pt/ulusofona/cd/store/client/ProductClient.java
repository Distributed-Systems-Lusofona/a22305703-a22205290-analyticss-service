package pt.ulusofona.cd.store.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ulusofona.cd.store.dto.ProductDto;

import java.util.List;

@FeignClient(name = "product-service", url = "http://product-service:8081")
public interface ProductClient {

    @GetMapping("/api/v1/products/supplier/{supplierId}")
    List<ProductDto> getProductsBySupplier(@PathVariable("supplierId") String supplierId);
}