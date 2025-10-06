package np.com.nitishrajbanshi.blog.Security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        System.out.println("JwtAuthenticationEntryPoint: Unauthorized access to " + request.getRequestURI() + " reason: " + (authException==null?"N/A":authException.getMessage()));
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        String message = authException == null ? "Unauthorized" : authException.getMessage();
        String json = String.format("{\"timestamp\":%d,\"status\":401,\"error\":\"Unauthorized\",\"message\":\"%s\",\"path\":\"%s\"}", System.currentTimeMillis(), message, request.getRequestURI());
        response.getWriter().write(json);
    }
}
