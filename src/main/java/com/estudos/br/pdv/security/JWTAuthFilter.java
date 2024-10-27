package com.estudos.br.pdv.security;

import com.estudos.br.pdv.dtos.ResponseDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authorization = request.getHeader("Authorization");

            if (authorization != null && authorization.startsWith("Bearer")) {
                String token = authorization.split(" ")[1];
                String username = jwtService.getUsername(token);

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                // Cria um usuário que será inserido um contexto do spring security
                UsernamePasswordAuthenticationToken usernamePAT = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                // Configurando o spring security com uma autenticação web
                usernamePAT.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePAT);
            }

            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(convertObjectToJson(new ResponseDTO("Token inválido!")));
        }
    }

    public String convertObjectToJson(ResponseDTO responseDTO) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(responseDTO);
    }

}
