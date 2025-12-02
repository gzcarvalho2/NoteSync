package com.PI.NoteSync.config;

import com.PI.NoteSync.entity.Usuario;
import com.PI.NoteSync.repository.UsuarioRepository;
import com.PI.NoteSync.service.JwtTokenService;
import com.PI.NoteSync.service.UserDetailsImpl;
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
import java.util.Optional;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoveryToken(request);

        if (token != null) {
            try {
                System.out.println("1. Token recebido: " + token.substring(0, 10) + "..."); // Imprime o começo do token

                String subject = jwtTokenService.getSubjectFromToken(token);
                System.out.println("2. Email extraído do token: " + subject);

                Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(subject);

                if (usuarioOptional.isPresent()) {
                    System.out.println("3. Usuário encontrado no banco!");
                    Usuario usuario = usuarioOptional.get();
                    UserDetailsImpl userDetails = new UserDetailsImpl(usuario);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("4. Autenticação realizada com sucesso.");
                } else {
                    System.out.println("ERRO: Usuário não encontrado no banco para o email: " + subject);
                }
            } catch (Exception e) {
                System.out.println("ERRO CRÍTICO NO FILTRO: " + e.getMessage());
                e.printStackTrace(); // Mostra o erro real no console
            }
        } else {
            System.out.println("Aviso: Nenhum token encontrado no Header.");
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}