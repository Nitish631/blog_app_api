package np.com.nitishrajbanshi.blog.payloads;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	private String message;
	private boolean success;
}
