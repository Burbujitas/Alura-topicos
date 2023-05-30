package com.alura.foro.infra.security;

import com.alura.foro.domain.usuarios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;
    @Autowired
    public SecurityFilter(TokenService tokenService,UsuarioRepository usuarioRepository){
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //obtener el token del header
        var authHeader = request.getHeader("Authorization");


        if(authHeader!=null){
            var token = authHeader.replace("Bearer ","");
            System.out.println(authHeader);
            System.out.println("sesion "+tokenService.getSubject(token));
            var nombreEmail = tokenService.getSubject(token);
            if(nombreEmail!=null)
            {
                var usuario = usuarioRepository.findByEmail(nombreEmail);
                var authentication = new UsernamePasswordAuthenticationToken(usuario,null,usuario.getAuthorities());//forzamos el inicio de sesion

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);
    }
}
