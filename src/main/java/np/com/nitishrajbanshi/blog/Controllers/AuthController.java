package np.com.nitishrajbanshi.blog.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import np.com.nitishrajbanshi.blog.Security.JwtAuthRequest;
import np.com.nitishrajbanshi.blog.Security.JwtAuthResponse;
import np.com.nitishrajbanshi.blog.Security.JwtTokenHelper;
import np.com.nitishrajbanshi.blog.exception.ApiException;
import np.com.nitishrajbanshi.blog.payloads.UserDto;
import np.com.nitishrajbanshi.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request)throws Exception{
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userdetails=this.userDetailsService.loadUserByUsername(request.getUsername());
        String token=this.jwtTokenHelper.generateToken(userdetails);
        JwtAuthResponse response=new JwtAuthResponse();
        response.setToken(token);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
       UserDto registeredUser= this.userService.registerNewUser(userDto);
       return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }
    private void authenticate(String username,String password)throws Exception{
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(username, password);
        try {
            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Details !!");
            throw new ApiException("Invalid username or password !!");
        }
    }
}

