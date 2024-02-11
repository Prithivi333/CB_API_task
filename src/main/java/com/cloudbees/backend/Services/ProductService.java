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
            product.setProductID((long) products.size() + 1);
            products.add(product);
            httpSession.setAttribute("products", products);
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
            httpSession.setAttribute("products", products);
            return foundProduct;
        }
        catch(Exception e){
            throw e;
        }
    }

    public Product updateProduct(Product updatedProduct){
        try{
            List<Product> products = (List<Product>) httpSession.getAttribute("products");
            if (products == null) {
                return null;
            }
            Long id= updatedProduct.getProductID();
            Product existingProduct = products.stream()
                    .filter(p -> p.getProductID().equals(id)).findAny()
                    .orElse(null);
            if (existingProduct == null) {
                return null;
            }

            if(updatedProduct.getName()!=null)existingProduct.setName(updatedProduct.getName());
            if(updatedProduct.getDescription()!=null)existingProduct.setDescription(updatedProduct.getDescription());
            if(updatedProduct.getPrice()!=null)existingProduct.setPrice(updatedProduct.getPrice());
            if(updatedProduct.getQuantityAvailable()!=null)existingProduct.setQuantityAvailable(updatedProduct.getQuantityAvailable());

            httpSession.setAttribute("products", products);
            return updatedProduct;
        }
        catch(Exception e){
            throw e;
        }
    }

    public int deleteProduct(Long id) {
        try{
            List<Product> products = (List<Product>) httpSession.getAttribute("products");
            Product foundProduct = products.stream()
                    .filter(product -> product.getProductID().equals(id)).findAny()
                    .orElse(null);
            if (foundProduct == null) {
                return 0;
            }
            products.remove(foundProduct);
            httpSession.setAttribute("products",products);
            return 1;
        }
        catch(Exception e){
            throw e;
        }
    }
}
