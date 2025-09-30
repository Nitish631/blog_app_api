package np.com.nitishrajbanshi.blog.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "user_name", nullable = false, length = 100)
	private String name;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	// @Column(name="hashedPassword",nullable=false)
	// private String hashedPassword;
	@Column(name = "about")
	private String about;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet();
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"), inverseJoinColumns =  @JoinColumn(name = "role", referencedColumnName = "roleId"))
	private Set<Role> roles = new HashSet<>();
}