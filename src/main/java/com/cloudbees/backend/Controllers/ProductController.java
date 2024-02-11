package com.cloudbees.backend.Controllers;

import com.cloudbees.backend.Entities.DiscountOrTax;
import com.cloudbees.backend.Entities.Product;
import com.cloudbees.backend.Entities.ProductNotFoundException;
import com.cloudbees.backend.Services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
public class ProductController {

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private ProductService pService;

    private ObjectMapper objectMapper;


    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("getProduct/{id}")
    public ResponseEntity<Object> getProduct(@PathVariable("id") Long id){
        try{
            Product foundProduct=pService.getProduct(id);
            if(foundProduct==null){
                logger.error("Unable to find requested product😓");
                throw new ProductNotFoundException("No product found");
            }
            return new ResponseEntity<>(foundProduct,HttpStatus.OK);
        }
        catch(ProductNotFoundException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            logger.error("Error retrieving product😓.");
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<Object> addProduct(@RequestBody Product product){
        try{
//            logger.info("Incoming Product:"+product);
            pService.setProduct(product);
            return new ResponseEntity<>("Product added to list successfully!😁", HttpStatus.OK);
        }
        catch(Exception e){
            logger.error("Error during product addition", e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<Object> updateProduct(@RequestBody Product product){
        try {
            Product updatedProduct = pService.updateProduct(product);
            if (updatedProduct == null) {
                throw new ProductNotFoundException("Product not found");
            }
            return new ResponseEntity<>("Product details updated.", HttpStatus.OK);
        }
        catch(ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            logger.error("Error during product update",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/addTax")
    public ResponseEntity<Object> addTax(@RequestBody DiscountOrTax drt){
        try{
            Product updatedProduct=pService.applyTax(drt);
            if(updatedProduct==null){
                throw new ProductNotFoundException("Product not found");
            }
            return new ResponseEntity<>("Product details updated.", HttpStatus.OK);
        }
        catch(ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch(IllegalArgumentException e){
            logger.error("Error during product update",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/addDiscount")
    public ResponseEntity<Object> addDiscount(@RequestBody DiscountOrTax drt){
        try{
            Product updatedProduct=pService.applyDiscount(drt);
            if(updatedProduct==null){
                throw new ProductNotFoundException("Product not found");
            }
            return new ResponseEntity<>("Product details updated.", HttpStatus.OK);
        }
        catch(ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch(IllegalArgumentException e){
            logger.error("Error during product update",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") Long id){
        try{
            int status=pService.deleteProduct(id);
            if(status==0)throw new ProductNotFoundException("Product not found");
            return new ResponseEntity<>("Deleted the product.",HttpStatus.OK);
        }
        catch(ProductNotFoundException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
        catch(Exception e){
            logger.error("Error during product update",e);
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
