package np.com.nitishrajbanshi.blog.services;

import java.util.List;

import np.com.nitishrajbanshi.blog.payloads.PostDto;
import np.com.nitishrajbanshi.blog.payloads.PostResponse;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
    PostDto updatePost(PostDto postDto,Integer postId,Integer userId);
    void deletePost(Integer postId);
    PostDto getPostById(Integer postId);
    PostResponse getAllPost(Integer pageNumber,Integer PageSize,String sortBy,String sortDir);
    List<PostDto> getAllPostsByUserId(Integer userId);
    List<PostDto> getAllPostsByCategoryId(Integer categoryId);
    List<PostDto> searcPostDtos(String keyword);
}