package com.cloudbees.backend.Controllers;

import com.cloudbees.backend.Entities.Product;
import com.cloudbees.backend.Services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProductService pService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("getProduct/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        try{
            Product foundProduct=pService.getProduct(id);
                if(foundProduct!=null){
                    return new ResponseEntity<>(foundProduct,HttpStatus.OK);
                }
                else {
                    logger.error("Unable to find requested product😓");
                }
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            logger.error("Error retrieving product😓.");
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestBody Product product){
        try{
            logger.info("Incoming Product:"+product);
            pService.setProduct(product);
            return new ResponseEntity<>("Product added to list successfully!😁", HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error during product addition", e);
            return new ResponseEntity<>("Error adding the given product😓", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody Product product){
        try{

        }
        catch(Exception e){
            logger.error("Error during product update",e);
            return new ResponseEntity<>("Error updating product😓",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
