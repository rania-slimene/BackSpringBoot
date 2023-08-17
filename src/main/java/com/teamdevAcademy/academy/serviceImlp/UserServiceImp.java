package com.teamdevAcademy.academy.serviceImlp;
import com.teamdevAcademy.academy.dto.LoginDto;
import com.teamdevAcademy.academy.dto.UserDTO;
import com.teamdevAcademy.academy.entities.User;
import com.teamdevAcademy.academy.entities.UserRole;
import com.teamdevAcademy.academy.repositories.FileRepository;
import com.teamdevAcademy.academy.repositories.UserRepository;
import com.teamdevAcademy.academy.services.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpSession;
import java.util.*;


@Service
public class UserServiceImp implements UserService  {
    @Autowired
    private HttpSession httpSession;
    @Autowired
    FileRepository fileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServiceImp emailServiceImp;
    private static final long TOKEN_EXPIRATION_TIME = 3600000;
    private static final String UPLOAD_DIR = "src/main/resources/static/images";
    @Autowired
    public UserServiceImp(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Value("${jwt.secret}")
    private String jwtSecret;


//genere un token aleatoir
    private String generateVerificationToken() {
           return UUID.randomUUID().toString();
       }
    //genere un password aleatoir

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User updateUser(Long id, UserDTO user) {
        try {
            System.out.println("Error updating " + id);
            System.out.println("Error 1 " + user);
            Optional<User> userOptional = userRepository.findById(id);
            System.out.println("Error " + userOptional.get());
            if (userOptional.isPresent()) {
                System.out.println("Error ------------ c" + user);

                User user1 = userOptional.get();
                user1.setPrenom(user.getPrenom());
                user1.setNom(user.getNom());
                user1.setEmail(user.getEmail());
                user1.setPassword(user.getPassword());
                user1.setNtel(user.getNtel());
                System.out.println("Error ------2------ c" + user1);
                return userRepository.save(user1);
            } else {
                return null; // Or throw an exception if desired
            }
        } catch (Exception e) {
            System.out.println("Error updating salle" + e);
            return null; // Or throw an exception if desired
        }
    }


    @Override
    public boolean DeleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }


    @Override
    public Long CountUser() {
        return this.userRepository.count();
    }


    public User signIn(LoginDto loginDTO) {
        User user = userRepository.findByEmail(loginDTO.getEmail());

        if (user == null) {
            throw new IllegalArgumentException("Email not registered");
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }
    /*   if (loginDTO.getPassword().equals(user.getPassword()) ) {
           throw new IllegalArgumentException("Incorrect password");
       }*/

        if (!user.isEmailVerified()) {
            throw new IllegalArgumentException("Email not verified");
        }

        String token = generateToken(user.getId());
        user.setToken(token);
        userRepository.save(user);



        return user;
    }
    public User Signup(UserDTO userDTO , UserRole role) {

        // Vérifiez si l'email est déjà utilisé
        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());

        user.setEmailVerified(false);

        // Encrypter le mot de passe à l'aide de BCrypt
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptedPassword);


        String encryptedConfirmeMp = passwordEncoder.encode(userDTO.getConfirmeMp());
        user.setConfirmeMp(encryptedConfirmeMp );

        if (!userDTO.getPassword().equals(userDTO.getConfirmeMp())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        user.setNtel(userDTO.getNtel());



        if (userRepository.findByNtel((userDTO.getNtel())) != null){
            throw new IllegalArgumentException("phoneNumber already in use");

        }

        String verificationToken = generateVerificationToken();
        user.setVerificationToken(verificationToken);

        // Assigner le rôle à l'utilisateur
        user.getRoles().add(role);
        userRepository.save(user);

        // Envoi de l'email de vérification
        emailServiceImp.sendVerificationEmail(user);


        return user;
    }

/*
   public User signIn(LoginDto loginDTO) {
       Optional<User> user = userRepository.findByEmail(loginDTO.getEmail());

       if (user == null) {
           throw new IllegalArgumentException("Email not registered");
       }

       if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
           throw new IllegalArgumentException("Incorrect password");
       }
    /*   if (loginDTO.getPassword().equals(user.getPassword()) ) {
           throw new IllegalArgumentException("Incorrect password");
       }

       if (!user.isEmailVerified()) {
           throw new IllegalArgumentException("Email not verified");
       }

       String token = generateToken(user.getId());
       user.setToken(token);
       userRepository.save(user);



       return user;
   }*/

   /* public User signIn(LoginDto loginDTO) {
        Optional<User> userOptional = userRepository.findByEmail(loginDTO.getEmail());

        User user = userOptional.orElseThrow(() -> new IllegalArgumentException("Email not registered"));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password");
        }

        if (!user.isEmailVerified()) {
            throw new IllegalArgumentException("Email not verified");
        }

        String token = generateToken(user.getId());
        user.setToken(token);
        userRepository.save(user);

        return user;
    }
*/
    private String generateToken(Long userId) {
        // Génération du token JWT avec une date d'expiration
        Date expirationDate = new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Long validateToken(String Token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(Token)
                    .getBody();

            return Long.parseLong(claims.getSubject());
        } catch (Exception e) {
            // La validation du token a échoué
            return null;
        }
    }

  public User verifyAccount(String verificationToken) {
      User user = userRepository.findByVerificationToken(verificationToken);

      if (user != null) {
          user.setEmailVerified(true);
          user.setVerificationToken(null); // Réinitialiser le jeton après vérification
          userRepository.save(user);
          return user;
      } else {
          throw new IllegalArgumentException("Invalid verification token.");
      }
  }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User getUserByNtel(Integer Ntel) {
        return userRepository.findByNtel(Ntel);
    }


    @Override
    public boolean isSessionValid(User user) {
        String storedToken = (String) httpSession.getAttribute("userToken");
        return storedToken != null && storedToken.equals(user.getToken());
    }


}

