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


@RestController
public class ProductController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProductService pService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("getProduct/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable("id") Long id){
        try{
            Product foundProduct=pService.getProduct(id);
            if(foundProduct==null){
                return new ResponseEntity<>(new Error("Product not found"),HttpStatus.NOT_FOUND);
            }
            else {
                logger.error("Unable to find requested product😓");
            }
            return new ResponseEntity<>(foundProduct,HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error retrieving product😓.");
            return new ResponseEntity<>(new Exception(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Object> addProduct(@RequestBody Product product){
        try{
            logger.info("Incoming Product:"+product);
            pService.setProduct(product);
            return new ResponseEntity<>("Product added to list successfully!😁", HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error during product addition", e);
            return new ResponseEntity<>(new Exception(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product){
        try{
            Product updatedProduct=pService.updateProduct(product);
            if(updatedProduct==null){
                return new ResponseEntity<>("No existing product", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Product details updated.", HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error during product update",e);
            return new ResponseEntity<>(new Exception(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id){
        try{
            int status=pService.deleteProduct(id);
            if(status==0)return new ResponseEntity<>("No existing product", HttpStatus.NOT_FOUND);
            return new ResponseEntity<>("Deleted the product.",HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error during product update",e);
            return new ResponseEntity<>(new Exception(e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
