package np.com.nitishrajbanshi.blog.services;

import java.util.List;

import np.com.nitishrajbanshi.blog.payloads.CategoryDto;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    void deleteCategory(Integer categoryId);
    CategoryDto getCategory(Integer categoryId);
    List<CategoryDto> getAllCategory();

}
