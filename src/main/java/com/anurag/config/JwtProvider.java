package com.anurag.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtProvider {

   static SecretKey secretKey= Keys.hmacShaKeyFor(JwtConstant.SECREATE_KEY.getBytes());


        public static String genereteToken(Authentication auth){

            return Jwts.builder().setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+86400000))
                    .claim("email",auth.getName())
                    .signWith(secretKey)
                    .compact();
        }

        public static String getEmailfromToken(String jwt)
        {
            jwt=jwt.substring(7);

            Claims claims= Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(jwt).getBody();

            return String.valueOf(claims.get("email"));


        }

}
