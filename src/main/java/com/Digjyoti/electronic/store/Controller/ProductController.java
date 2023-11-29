package com.Digjyoti.electronic.store.Controller;

import com.Digjyoti.electronic.store.dtos.*;
import com.Digjyoti.electronic.store.services.FileService;
import com.Digjyoti.electronic.store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value(("${product.profile.image.path}"))
    private  String imagePath;

    //    create
    @PostMapping("/created")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {

        ProductDto createProduct = productService.create(productDto);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    //    update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updatedProduct(@PathVariable int productId, @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.update(productDto, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    //    Delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> delete(@PathVariable int productId) {
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Product Delete SuccessFully").status(HttpStatus.NOT_FOUND).success(true).build();
        return new ResponseEntity<>(responseMessage, HttpStatus.NOT_FOUND);
    }

    //    getSingle
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getSingle(@PathVariable int productId) {
        ProductDto getproduct = productService.get(productId);
        return new ResponseEntity<>(getproduct, HttpStatus.OK);
    }

    //    get All
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    //    getAllLive
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>> getAllLive(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }
    //    Search
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> searchProduct(
            @PathVariable String query,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "title", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }
//    Upload Image
    @PostMapping("/image/{productId}")
    public  ResponseEntity<ImageResponse> uploadproductImage(@PathVariable int productId,
                                                             @RequestParam ("productImage")MultipartFile image) throws IOException {
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImage(fileName);
        ProductDto update = productService.update(productDto, productId);
        ImageResponse imageResponse = ImageResponse.builder().imageName(update.getProductImage())
                .message("Product Upload Successfully")
                .status(HttpStatus.CREATED).success(true).build();
        return  new ResponseEntity<>(imageResponse,HttpStatus.CREATED);


    }

//    Serve Image
@GetMapping("/image/{productId}")
public void serveUserImage(@PathVariable int productId, HttpServletResponse response) throws IOException {
    ProductDto productDto = productService.get(productId);
//    logger.info("User Image Name : {} ",user.getImage());
    InputStream resource = fileService.getResource(imagePath, productDto.getProductImage());
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource,response.getOutputStream());



}

}

