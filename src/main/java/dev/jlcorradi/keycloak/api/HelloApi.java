package dev.jlcorradi.keycloak.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api/v1/hello")
@RestController
public class HelloApi {

  @GetMapping
  public ResponseEntity<?> sayHello() {
    return ResponseEntity.ok(Map.of("message", "Hello User!"));
  }

  @PreAuthorize("hasRole('root')")
  @GetMapping("/root")
  public ResponseEntity<?> sayHelloRoot() {
    return ResponseEntity.ok(Map.of("message", "Hello Root!"));
  }
}
