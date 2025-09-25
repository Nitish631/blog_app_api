package np.com.nitishrajbanshi.blog.Controllers;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import np.com.nitishrajbanshi.blog.payloads.ApiResponse;
import np.com.nitishrajbanshi.blog.payloads.CategoryDto;
import np.com.nitishrajbanshi.blog.services.CategoryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;





@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto createdCategoryDto=this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(createdCategoryDto,HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer cid){
        CategoryDto updatedCategoryDto=this.categoryService.updateCategory(categoryDto, cid);
        return ResponseEntity.ok(updatedCategoryDto);
    }
    
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteUser(@PathVariable("categoryId") Integer cid){
        this.categoryService.deleteCategory(cid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted Successfully",true),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategory(){
        return ResponseEntity.ok(this.categoryService.getAllCategory());
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("categoryId") Integer cid){
        return ResponseEntity.ok(this.categoryService.getCategory(cid));
    }
    
    
}
