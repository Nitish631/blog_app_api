package np.com.nitishrajbanshi.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import np.com.nitishrajbanshi.blog.config.AppConstants;
import np.com.nitishrajbanshi.blog.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import np.com.nitishrajbanshi.blog.entities.Post;
import np.com.nitishrajbanshi.blog.exception.ResourceNotFoundException;
import np.com.nitishrajbanshi.blog.payloads.PostDto;
import np.com.nitishrajbanshi.blog.payloads.PostResponse;
import np.com.nitishrajbanshi.blog.repositories.CategoryRepo;
import np.com.nitishrajbanshi.blog.repositories.PostRepo;
import np.com.nitishrajbanshi.blog.repositories.UserRepo;
import np.com.nitishrajbanshi.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
        Post post=this.modelMapper.map(postDto, Post.class);
        post.setAddedDate(new Date());
        post.setImageName("NitishImage.png");
        post.setUser(this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId)));
        post.setCategory(this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId)));
        Post savedPost=this.postRepo.save(post);
        return this.modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));        
        this.postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
        Sort sort=null;
        sort= (sortDir.equalsIgnoreCase(AppConstants.SORT_DIR))? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable=PageRequest.of(pageNumber, pageSize,sort);
        Page<Post> pagePosts=this.postRepo.findAll(pageable);
        List<Post> posts=pagePosts.getContent();
        List<PostDto>postDto=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDto);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements(pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPostsByCategoryId(Integer categoryId) {
        Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Post> posts=this.postRepo.findByCategory(category);
        return posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getAllPostsByUserId(Integer userId) {
        // User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
        // List<Post> posts=this.postRepo.findByUser(user);
        // return posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return this.postRepo.findByUser(this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId))).stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> searcPostDtos(String keyword) {
        List<Post>posts=this.postRepo.findByTitleContaining(keyword);
        return posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId,Integer userId) {
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
        if(post.getUser().getId()==userId ){
            this.modelMapper.map(postDto, post);
            post.setAddedDate(new Date());
        }
        Post updatedPost=this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }
    
}
