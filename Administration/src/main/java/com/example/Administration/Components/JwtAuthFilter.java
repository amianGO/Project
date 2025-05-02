package com.example.Administration.Components;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.Administration.Config.CustomUserDetails;
import com.example.Administration.Entities.Empresa;
import com.example.Administration.Repositories.EmpresaRepository;
import com.example.Administration.Services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Este filtro intercepta cada request, valida el token y autentica la entidad Empresa
@Component
public class JwtAuthFilter extends OncePerRequestFilter{
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    protected boolean shouldNotFilter(@SuppressWarnings("null") HttpServletRequest request) throws ServletException{
        String path = request.getServletPath();
        return path.startsWith("/api/auth");
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) 
                                    throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        System.out.println("Email Extraido del Token " + email);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Empresa empresa = empresaRepository.findByEmail(email).orElse(null);

            if (empresa != null && jwtService.isTokenValid(token, email)) {
                CustomUserDetails userDetails = new CustomUserDetails(empresa);
                UsernamePasswordAuthenticationToken authToken =
                 new UsernamePasswordAuthenticationToken(
                    userDetails, 
                    null, 
                    userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Auntenticando Empresa: " + email);
            }
        }
        filterChain.doFilter(request, response);
    }
}
