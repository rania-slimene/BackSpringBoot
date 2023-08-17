package com.teamdevAcademy.academy.controller;
import com.teamdevAcademy.academy.dto.LoginDto;
import com.teamdevAcademy.academy.dto.UserDTO;
import com.teamdevAcademy.academy.entities.User;
import com.teamdevAcademy.academy.entities.UserRole;
import com.teamdevAcademy.academy.repositories.UserRepository;
import com.teamdevAcademy.academy.serviceImlp.EmailServiceImp;
import com.teamdevAcademy.academy.services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@PreAuthorize("permitAll")
@RestController
@CrossOrigin("*")
@RequestMapping("/User")
public class UserController {

    private final UserService userService; // Make sure you have this dependency properly injected
    private final UserRepository userRepository; // Make sure you have this dependency properly injected
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;
    private static final long EXPIRATION_TIME = 3600000; // 1 heure en millisecondes

    public String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @Autowired
    private HttpSession httpSession;


    @Autowired


    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${app.base.url}")
    private String appBaseUrl;
    @Autowired
    private EmailServiceImp emailService;

    @GetMapping("")
    public List<User> getAllUser() {
        return this.userService.getAllUsers();
    }


    @PutMapping("{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyAuthority(('admin:read'),('user:read'))")
    public User getAllUser(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }


    @GetMapping("/Count")
    public Long NumberUser() {
        return this.userService.CountUser();
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        if (userService.DeleteUserById(id) == true) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    Collections.singletonMap("message", " deleted with success"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Collections.singletonMap("message", "erreur id n'existe pas"));
    }

    @PostMapping("/signIn")
    public ResponseEntity<Object> loginUser(@RequestBody LoginDto loginDTO) {
        try {
            User user = userService.signIn(loginDTO);

            if (!user.isEmailVerified()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("message", "Email not verified"));
            }

            // Créer un token JWT
            String token = Jwts.builder()
                    .setSubject(Long.toString(user.getId()))
                    .claim("prenom", user.getPrenom())
                    .claim("nom", user.getNom())
                    .claim("email", user.getEmail())
                    .claim("ntel", user.getNtel())
                    .claim("file",user.getFile())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Définir la durée de validité du token
                    .signWith(SignatureAlgorithm.HS256, jwtSecret)
                    .compact();

            String role = determineUserRole(user); // Determine the role of the user

            Map<String, String> responseMap = new HashMap<>();
            responseMap.put("token", token);
            responseMap.put("role", role);

            return ResponseEntity.ok(responseMap);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }



    private String determineUserRole(User user) {
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.name().toString())
                .collect(Collectors.toSet());

        if (roles.contains(UserRole.ADMIN.toString())) {
            return UserRole.ADMIN.toString();
        } else {
            return UserRole.USER.toString();
        }
    }

    @PostMapping(path = "/Signup")
    @PreAuthorize("permitAll")
    public ResponseEntity<Object> signup(@RequestBody UserDTO user ,@RequestParam(value = "isAdmin", required = false) boolean isAdmin) {
      if(userService.getUserByEmail(user.getEmail())!= null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "Email already in use"));
        }
        if (userService.getUserByNtel(user.getNtel()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Collections.singletonMap("message", "phone Number already in use"));
        }
        if (!user.getPassword().equals(user.getConfirmeMp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "Passwords do not match"));
        }



        UserRole role = isAdmin ? UserRole.ADMIN : UserRole.USER;
        try {


            User newUser = userService.Signup(user, role);


            return ResponseEntity.ok(newUser);
        }

        catch (IllegalArgumentException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }


    @GetMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam("verificationToken") String verificationToken) {
        try {
            // Valider le jeton de vérification
            User user = userService.verifyAccount(verificationToken);

            if (user != null) {
                return ResponseEntity.ok("Account verified successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid verification token.");
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/password-reset")
    public void resetPassword(@RequestParam String email) {
        User user = userRepository.findByEmail(email);

        if (user != null) {
            String newPassword = generateRandomPassword();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encryptedPassword = passwordEncoder.encode(newPassword);

            user.setPassword(encryptedPassword);
            userRepository.save(user);

            emailService.sendPasswordResetEmail(user, newPassword);
        }
    }

}




