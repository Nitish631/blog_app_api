package np.com.nitishrajbanshi.blog.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import np.com.nitishrajbanshi.blog.config.AppConstants;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
@Component
public class JwtTokenHelper {

    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }
    public Boolean validateToken(String token,UserDetails userDetails){
        final String username=getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    private SecretKey getSigningKey(){
        byte[] keyBytes=Decoders.BASE64.decode(AppConstants.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private String doGenerateToken(Map<String,Object> claims,String userName){
        return  Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+AppConstants.JWT_TOKEN_VALIDITY*1000)).signWith(getSigningKey(),SignatureAlgorithm.HS512).compact();
    }
    public String getUserNameFromToken(String token){
        return getClaimFromToken(token,Claims::getSubject);
    }
    private  <T>T getClaimFromToken(String token,Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    private Claims getAllClaimsFromToken(String token) {
        return  Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private   boolean isTokenExpired(String token){
        final Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    private Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }
}
