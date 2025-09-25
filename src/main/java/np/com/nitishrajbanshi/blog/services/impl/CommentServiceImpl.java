package np.com.nitishrajbanshi.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import np.com.nitishrajbanshi.blog.entities.Comment;
import np.com.nitishrajbanshi.blog.entities.Post;
import np.com.nitishrajbanshi.blog.entities.User;
import np.com.nitishrajbanshi.blog.exception.ResourceNotFoundException;
import np.com.nitishrajbanshi.blog.payloads.ApiResponse;
import np.com.nitishrajbanshi.blog.payloads.CommentDto;
import np.com.nitishrajbanshi.blog.repositories.CommentRepo;
import np.com.nitishrajbanshi.blog.repositories.PostRepo;
import np.com.nitishrajbanshi.blog.repositories.UserRepo;
import np.com.nitishrajbanshi.blog.services.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);
        comment.setUser(this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId)));
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public ApiResponse deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
        this.commentRepo.delete(comment);
        return new ApiResponse("Comment deleted successfully", true);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentID) {
        Comment comment = this.commentRepo.findById(commentID)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentID));
        this.modelMapper.map(commentDto, comment);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentsByPost(Integer pageNo, Integer pageSize, Integer postId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("commentId").ascending());
        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        Page<Comment> pageComment = this.commentRepo.findByPost(post, pageable);
        List<Comment> comments = pageComment.getContent();
        List<CommentDto> commentDtos = comments.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public List<CommentDto> getCommentsByUser(Integer pageNo, Integer pageSize, Integer userId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("commentId").ascending());
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        Page<Comment> pageComment = this.commentRepo.findByUser(user, pageable);
        List<Comment> comments = pageComment.getContent();
        List<CommentDto> commentDtos = comments.stream().map(comment -> this.modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
        return commentDtos;
    }

}
