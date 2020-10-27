package com.decagon.springsecurityjwt.security;

import com.decagon.springsecurityjwt.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.decagon.springsecurityjwt.utils.AppConstants.HEADER_STRING;
import static com.decagon.springsecurityjwt.utils.AppConstants.TOKEN_PREFIX;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = getJwtFromRequest(httpServletRequest);

            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateJwtToken(jwt)) { // if jwt is not null and the jwt is still valid
                //get user email from jwt
                String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
                //loads the user by username, which is email in this case
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, null //set your roles here for now we set it to null
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                //set user in the spring security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception exception) {
            //TODO: throw your custom exception here
            logger.error("Could not set user authentication in security context", exception);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * This method gets the jwt token from a request
     *
     * @param request request
     * @return String
     */
    public String getJwtFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader(HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
