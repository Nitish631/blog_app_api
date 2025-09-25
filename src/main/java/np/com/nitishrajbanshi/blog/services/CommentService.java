package np.com.nitishrajbanshi.blog.services;

import java.util.List;

import np.com.nitishrajbanshi.blog.payloads.ApiResponse;
import np.com.nitishrajbanshi.blog.payloads.CommentDto;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
    ApiResponse deleteComment(Integer commentId);
    CommentDto updateComment(CommentDto commentDto,Integer commentID);
    List<CommentDto> getCommentsByPost(Integer pageNo,Integer pageSize,Integer postId);
    List<CommentDto> getCommentsByUser(Integer pageNo,Integer pageSize,Integer userId);
    
}
