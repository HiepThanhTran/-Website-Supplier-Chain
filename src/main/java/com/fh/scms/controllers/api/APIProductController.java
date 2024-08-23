package com.fh.scms.controllers.api;

import com.fh.scms.dto.product.ProductResponseWithTagUnit;
import com.fh.scms.pojo.Product;
import com.fh.scms.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/products", produces = "application/json; charset=UTF-8")
public class APIProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> listProducts(@RequestParam(required = false, defaultValue = "") Map<String, String> params) {
        List<ProductResponseWithTagUnit> products = this.productService.getAllProductResponseWithTagUnit(params);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable(value = "productId") Long productId) {
        Product product = this.productService.findById(productId);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        ProductResponseWithTagUnit productResponseWithTagUnit = this.productService.getProductResponseWithTagUnit(product);

        return ResponseEntity.ok(productResponseWithTagUnit);
    }
}