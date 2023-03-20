// package propensi.project.water.service.impl;

// import org.springframework.stereotype.Service;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.core.userdetails.UsernameNotFoundException;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;

// import propensi.project.water.model.User.UserModel;
// import propensi.project.water.repository.User.UserDb;


// @Service
// public class UserDetailsServiceImpl implements UserDetailsService {

//     private final UserDb userRepository;

//     public UserDetailsServiceImpl(UserDb userRepository) {
//         this.userRepository = userRepository;
//     }

//     @Override
//     public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//         UserModel user = userRepository.findByUsername(username)
//                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//         List<GrantedAuthority> authorities = new ArrayList<>();
//         authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

//         return new User(user.getUsername(), user.getPassword(), authorities);
//     }
// }
