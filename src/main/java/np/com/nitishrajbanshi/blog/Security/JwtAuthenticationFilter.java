package np.com.nitishrajbanshi.blog.Security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Skip filter for authentication endpoints (login/register)
        String requestPath = request.getServletPath();
        String requestUri = request.getRequestURI();
        // Use URI contains check so it works regardless of context path or trailing slash
        if ((requestPath != null && requestPath.startsWith("/api/v1/auth/")) || (requestUri != null && requestUri.contains("/api/v1/auth"))) {
            logger.debug("Request to auth endpoint '{}'(uri='{}'), skipping JwtAuthenticationFilter", requestPath, requestUri);
            filterChain.doFilter(request, response);
            return;
        }
        // get token
    String requestToken = request.getHeader("Authorization");
    logger.debug("Authorization header: {}", requestToken);
        //Bearer_
        String token=null;
        String userName=null;
        if (requestToken != null && requestToken.startsWith("Bearer ")) {
            try {
                token = requestToken.substring(7);
                userName = this.jwtTokenHelper.getUserNameFromToken(token);
                logger.debug("Token extracted, username from token: {}", userName);
            } catch (IllegalArgumentException ex) {
                logger.warn("Unable to get Jwt token: {}", ex.getMessage());
            } catch (ExpiredJwtException ex) {
                logger.warn("Jwt token has expired: {}", ex.getMessage());
            } catch (MalformedJwtException exx) {
                logger.warn("Invalid Jwt: {}", exx.getMessage());
            }
        } else {
            logger.debug("Jwt token does not begin with Bearer or is null");
        }
            //once we get the token and userName then we validate
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                logger.debug("Loaded userDetails for {}: authorities={}", userName, userDetails.getAuthorities());
                if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    logger.debug("Authentication set in SecurityContext for {}", userName);
                } else {
                    logger.warn("Token validation failed for user {}", userName);
                }
            } catch (Exception ex) {
                logger.warn("Exception while processing authentication for {}: {}", userName, ex.getMessage());
            }
        } else {
            logger.debug("Username is null or SecurityContext already has authentication");
        }
        filterChain.doFilter(request,response);
    }
}
