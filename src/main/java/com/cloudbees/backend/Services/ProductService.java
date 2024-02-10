package com.cloudbees.backend.Services;

import com.cloudbees.backend.Controllers.ProductController;
import com.cloudbees.backend.Entities.Product;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private HttpSession httpSession;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    public void setProduct(Product product){
        try{
            List<Product> products = (List<Product>) httpSession.getAttribute("products");
            if (products == null) {
                products = new ArrayList<>();
            }
            Product newProduct = new Product();
            newProduct.setProductID((long) products.size() + 1);
            newProduct.setName(product.getName());
            newProduct.setDescription(product.getDescription());
            newProduct.setPrice(product.getPrice());
            newProduct.setQuantityAvailable(product.getQuantityAvailable());
            products.add(newProduct);
            httpSession.setAttribute("products", products);
//        logger.info(newProduct.toString());
            logger.info("Product added😁");
        }
        catch(Exception e){
            throw e;
        }
    }

    public Product getProduct(Long id){
        try{
            List<Product> products = (List<Product>) httpSession.getAttribute("products");
            if (products == null) {
                return null;
            }
            Product foundProduct = products.stream()
                    .filter(product -> product.getProductID().equals(id)).findAny()
                    .orElse(null);
            if (foundProduct == null) {
                return null;
            }
            return foundProduct;
        }
        catch(Exception e){
            throw e;
        }
    }

    public Product updateProduct(Long id){
        try{
            List<Product> products = (List<Product>) httpSession.getAttribute("products");
            if (products == null) {
                return null;
            }
            Product foundProduct = products.stream()
                    .filter(product -> product.getProductID().equals(id)).findAny()
                    .orElse(null);
            if (foundProduct == null) {
                return null;
            }
            return foundProduct;
        }
        catch(Exception e){
            throw e;
        }
    }
}
