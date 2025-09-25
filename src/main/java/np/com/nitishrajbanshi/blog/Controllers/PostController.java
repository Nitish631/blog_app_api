package np.com.nitishrajbanshi.blog.Controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import np.com.nitishrajbanshi.blog.config.AppConstants;
import np.com.nitishrajbanshi.blog.payloads.ApiResponse;
import np.com.nitishrajbanshi.blog.payloads.ImageResponse;
import np.com.nitishrajbanshi.blog.payloads.PostDto;
import np.com.nitishrajbanshi.blog.payloads.PostResponse;
import np.com.nitishrajbanshi.blog.services.FileService;
import np.com.nitishrajbanshi.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable("userId") Integer userId,
            @PathVariable("categoryId") Integer categoryId) {
        PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/user/{userId}/postId/{postId}/posts")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable("postId") Integer postId,@PathVariable("userId") Integer userId) {
        PostDto updatedPostDto = this.postService.updatePost(postDto, postId, userId);
        return ResponseEntity.ok(updatedPostDto);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable("userId") Integer userId){
        List<PostDto> postDtos=this.postService.getAllPostsByUserId(userId);
        return ResponseEntity.ok(postDtos);
    }
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable("categoryId") Integer categoryId){
        List<PostDto> postDtos=this.postService.getAllPostsByCategoryId(categoryId);
        return ResponseEntity.ok(postDtos);
    }
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllposts(@RequestParam(value = "pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,@RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false)String sortBy,@RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false)String sortDir){
        return ResponseEntity.ok(this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir));
    }
    @GetMapping("/posts/{postId}")
    private ResponseEntity<PostDto> getPost(@PathVariable("postId") Integer postId){
        return ResponseEntity.ok(this.postService.getPostById(postId));
    }
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId") Integer postId){
        this.postService.deletePost(postId);
        return ResponseEntity.ok(new ApiResponse("Post Deleted Successfully",true));
    }
    @GetMapping("/posts/search/{keywords}")
    public ResponseEntity<List<PostDto>> serrchPostsByKeywords(@PathVariable("keywords") String keywords){
        return ResponseEntity.ok(this.postService.searcPostDtos(keywords));
    }
    //post image upload
    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<ImageResponse> uploadPostImage(@RequestParam(value = "imageFile")MultipartFile imageFile,@PathVariable("postId") Integer postId){
        String savedImageName;
        PostDto updatedPostDto;
        PostDto postDto=this.postService.getPostById(postId);
        try{
            savedImageName=this.fileService.uploadingImage(path, imageFile);
            postDto.setImageName(savedImageName);
            updatedPostDto=this.postService.updatePost(postDto, postId, postDto.getUser().getId());
        }catch(Exception ex){
            return new ResponseEntity<ImageResponse>(new ImageResponse(null,"This file is failed to uploaded"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ImageResponse>(new ImageResponse(updatedPostDto,"Image uploaded successfully as :".concat(savedImageName)),HttpStatus.CREATED);
    }
    @GetMapping("/posts/image/postId/{postId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable("postId")Integer postId)throws IOException{
        PostDto post=this.postService.getPostById(postId);
        String imageName=post.getImageName();
        InputStream resource=this.fileService.getImage(this.path, imageName);
        byte[] imageBytes=resource.readAllBytes();
        //Detect content type dynamically
        String fullPath=this.path+File.separator+imageName;
        String contentType=Files.probeContentType(Paths.get(fullPath));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(imageBytes);
    }
}
