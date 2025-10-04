package np.com.nitishrajbanshi.blog.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // String requestPath=request.getServletPath();
        // if(requestPath.equals("/api/v1/auth/login") || requestPath.equals("/api/v1/auth/register")){
        //     filterChain.doFilter(request, response);
        //     return;
        // }
        // get token
        String requestToken=request.getHeader("Authorization");
        System.out.println(requestToken);
        //Bearer_
        String token=null;
        String userName=null;
        if(requestToken!=null && requestToken.startsWith("Bearer ")){
            try {
                token=requestToken.substring(7);
                userName=this.jwtTokenHelper.getUserNameFromToken(token);
            }catch (IllegalArgumentException ex){
                System.out.println("Unable to get Jwt token");
            }catch (ExpiredJwtException ex){
                System.out.println("Jwt token has expired");
            }catch (MalformedJwtException exx){
                System.out.println("Invalid Jwt");
            }
        }else{
            System.out.println("Jwt token doesnot begin with bearer ");
        }
            //once we get the token and userName then we validate
        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails=this.userDetailsService.loadUserByUsername(userName);
                if(this.jwtTokenHelper.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }else{
                    System.out.println("Invalid jwt token");
                }
            }else{
                System.out.println("Username is null or context is not null");
            }
        filterChain.doFilter(request,response);
    }
}
