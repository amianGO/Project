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
public class JwtAuthFilter extends OncePerRequestFilter{                            //Esta clase es la encargada de filtrar las peticiones y validar el token 
    
    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    protected boolean shouldNotFilter(@SuppressWarnings("null") HttpServletRequest request) throws ServletException{    //Esta funcion se encarga de verificar si la peticion no es de autenticacion, en este caso si la peticion no es de autenticacion, no se filtra
        String path = request.getServletPath();                                     //Esta funcion se encarga de obtener la URL 
        return path.startsWith("/api/auth");                                 //Esta funcion se encarga de verificar si la URL empieza con /api/auth, en este caso no se filtra
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)                        //Esta funcion se encarga de realizar la peticion y validar el token
                                    throws ServletException, IOException{
        String authHeader = request.getHeader("Authorization");                //Esta funcion se encarga de obtener la cabecera de autorizacion del request, en este caso del token
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {       //Esta funcion se encarga de verificar si la cabecera de autorizacion es nula o no empieza con Bearer, en este caso no se filtra
            filterChain.doFilter(request, response); 
            return;
        }

        String token = authHeader.substring(7);                          //Esta funcion se encarga de obtener el tokem, quitando los 7 primeros caracteres, en este caso seria el Bearer
        String email = jwtService.extractEmail(token);                              //Extraemos el email y lo asignamos

        System.out.println("Email Extraido del Token " + email);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) { //Esta funcion se encarga de verificar si el email no es nulo y si el contexto de seguridad no tiene autenticacion, en este caso se filtra

            Empresa empresa = empresaRepository.findByEmail(email).orElse(null);    //Buscamos la empresa por el email en la base de datos

            if (empresa != null && jwtService.isTokenValid(token, email)) {         //Esta funcion se encarga de verificar si la empresa no es nula y si el token es valido, en este caso se filtra
                CustomUserDetails userDetails = new CustomUserDetails(empresa);     //Creamos un objeto de tipo CustomUserDetails, que implementa UserDetails y le pasamos la empresa como argumento
                UsernamePasswordAuthenticationToken authToken =
                 new UsernamePasswordAuthenticationToken(
                    userDetails, 
                    null, 
                    userDetails.getAuthorities()
                );                                                                  //Creamos un objeto de tipo UsernamePasswordAuthenticationToken, que se encarga de autenticar la empresa, le pasamos el objeto userDetails, null y los roles del usuario
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //Esta funcion se encarga de asignar los detalles de autenticacion al token
                SecurityContextHolder.getContext().setAuthentication(authToken);    //Se encarga de asignar el token en el contexto de seguridad
                System.out.println("Auntenticando Empresa: " + email);
            }
        }
        filterChain.doFilter(request, response);                                    //Filtra la peticion y la respuesta
    }
}
