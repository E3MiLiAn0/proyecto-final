package com.cuatrodecopas.salud.core.filtro;

import com.cuatrodecopas.salud.core.servicios.implementaciones.AutenticacionServicioImpl;
import com.cuatrodecopas.salud.core.servicios.implementaciones.JwtServicioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestJwtFiltro  extends OncePerRequestFilter {

    @Autowired
    private AutenticacionServicioImpl autenticacionServicioImpl;
    @Autowired
    private JwtServicioImpl jwtServicioImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String mail = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            mail = jwtServicioImpl.extractUsername(jwt);
        }

        if (mail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.autenticacionServicioImpl.loadUserByUsername(mail);

            if (jwtServicioImpl.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authReq =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authReq.setDetails(authReq);
                SecurityContextHolder.getContext().setAuthentication(authReq);
            }
        }
        chain.doFilter(request, response);
    }
}
