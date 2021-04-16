package me.dongqinglin.bootstarter.Secure.filter;

import me.dongqinglin.bootstarter.Secure.service.FlinaUserDetailService;
import me.dongqinglin.bootstarter.Secure.converter.JwtState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtState jwtState;

    @Autowired
    private FlinaUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authenticationHeader = request.getHeader("Authorization");
        // System.out.println("132412341234");
        if (authenticationHeader == null || authenticationHeader.trim().isEmpty() ) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = this.getJwt(authenticationHeader);
        if (jwt.trim().isEmpty() ){
            filterChain.doFilter(request, response);
            return;
        }
        final String username = jwtState.extractUsername(jwt);
        if ( username.trim().isEmpty() ) {
            filterChain.doFilter(request, response);
            return;
        }
        UserDetails userDetails = userDetailService.loadUserByUsername(username);
        if (!jwtState.validateToken(jwt, userDetails)){
            filterChain.doFilter(request, response);
            return;
        }
        if (SecurityContextHolder.getContext().getAuthentication() != null ) {
            filterChain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }

    private String getJwt(String authenticationHeader) {
        String result = "";
        if(!authenticationHeader.startsWith("Bearer ")) return result;
        result = authenticationHeader.substring(7);
        return result;
    }

}
