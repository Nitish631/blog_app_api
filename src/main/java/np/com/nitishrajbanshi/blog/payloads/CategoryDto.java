package np.com.nitishrajbanshi.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotBlank
    @Size(min=4,message = "Title must be atleast of 4 character")
    private String categoryTitle;
    @NotBlank
    @Size(min=10,message = "Description must be atleast of 10 character")
    private String categoryDescription;
}
