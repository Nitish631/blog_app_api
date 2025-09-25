package np.com.nitishrajbanshi.blog.services.impl;
import java.util.List;
import java.util.stream.Collectors;

import np.com.nitishrajbanshi.blog.entities.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import np.com.nitishrajbanshi.blog.exception.ResourceNotFoundException;
import np.com.nitishrajbanshi.blog.payloads.CategoryDto;
import np.com.nitishrajbanshi.blog.repositories.CategoryRepo;
import np.com.nitishrajbanshi.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category=this.modelMapper.map(categoryDto, Category.class);
        Category savedCategory=this.categoryRepo.save(category);
        return this.modelMapper.map(savedCategory,CategoryDto.class);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
        this.categoryRepo.delete(category);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> categories=this.categoryRepo.findAll();
        return categories.stream().map((category)->this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
        return this.modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
        this.modelMapper.map(categoryDto, category);
        Category updatedCategory=this.categoryRepo.save(category);
        return this.modelMapper.map(updatedCategory, CategoryDto.class);
    }
    
}
