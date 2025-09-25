package np.com.nitishrajbanshi.blog.payloads;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PostDto {
    private Integer postId;
    private String content;
    private String title;
    private String imageName;
    private Date addedDate;
    private CategoryDto category;
    private UserDto user;
}
