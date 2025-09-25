package np.com.nitishrajbanshi.blog.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import np.com.nitishrajbanshi.blog.config.AppConstants;
import np.com.nitishrajbanshi.blog.payloads.ApiResponse;
import np.com.nitishrajbanshi.blog.payloads.CommentDto;
import np.com.nitishrajbanshi.blog.services.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService CommentService;

    @PostMapping("/postId/{postId}/userId/{userId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable("postId")Integer postId,@PathVariable("userId")Integer userId){
        CommentDto savedCommentDto=this.CommentService.createComment(commentDto, postId, userId);
        return new ResponseEntity<CommentDto>(savedCommentDto,HttpStatus.CREATED);
    }
    @PutMapping("/commentId/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@RequestBody CommentDto commentDto,@PathVariable("commentId") Integer commentId){
        CommentDto updatedCommentDto=this.CommentService.updateComment(commentDto, commentId);
        return ResponseEntity.ok(updatedCommentDto);
    }
    @DeleteMapping("/commentId/{commentId}")
    public ResponseEntity<ApiResponse> deletecomment(@PathVariable("commentId") Integer commentId){
        ApiResponse apiResponse=this.CommentService.deleteComment(commentId);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/userId/{userId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(@RequestParam(value="pageNo",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNo,@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,@PathVariable("userId")Integer userId){
        List<CommentDto> commentsDtos=this.CommentService.getCommentsByUser(pageNo, pageSize, userId);
        return ResponseEntity.ok(commentsDtos);
    }
    @GetMapping("/postId/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPost(@RequestParam(value = "pageNo",defaultValue = AppConstants.PAGE_NUMBER,required = false)Integer pageNo,@RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false)Integer pageSize,@PathVariable("postId") Integer postId){
        List<CommentDto> commentDtos=this.CommentService.getCommentsByPost(pageNo, pageSize, postId);
        return ResponseEntity.ok(commentDtos);
    }
}
