package dev.jlcorradi.keycloak.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class GrantedAuthoritiesWithPrefixJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

  public static final String RESOURCE_ACCESS_CLAIM = "resource_access";
  public static final String ROLES_CLAIM = "roles";

  @Value("${spring.application.name}")
  private String clientId;

  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    Collection<? extends GrantedAuthority> authorities =
        Optional.ofNullable(jwt.getClaims().get(RESOURCE_ACCESS_CLAIM))
            .map(resourceAccessClaim -> ((Map<?, ?>) resourceAccessClaim).get(clientId))
            .map(rolesClaim -> ((Map<?, ?>) rolesClaim).get(ROLES_CLAIM))
            .map(roles -> ((List<?>) roles).stream().map(role -> new SimpleGrantedAuthority(String.format("ROLE_%s", role))))
            .orElse(Stream.of())
            .collect(Collectors.toSet());

    return new JwtAuthenticationToken(jwt, authorities, jwt.getClaimAsString(JwtClaimNames.SUB));
  }
}
