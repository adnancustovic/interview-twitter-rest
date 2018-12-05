package com.javalanguagezone.interviewtwitter.service;

import com.javalanguagezone.interviewtwitter.domain.User;
import com.javalanguagezone.interviewtwitter.repository.TweetRepository;
import com.javalanguagezone.interviewtwitter.repository.UserRepository;
import com.javalanguagezone.interviewtwitter.service.dto.UserDTO;
import com.javalanguagezone.interviewtwitter.service.dto.UserProfileDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class UserService implements UserDetailsService {

  private UserRepository userRepository;
  private TweetRepository tweetRepository;

  public UserService(UserRepository userRepository, TweetRepository tweetRepository) {
    this.userRepository = userRepository;
    this.tweetRepository = tweetRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = getUser(username);
    if(user == null)
      throw new UsernameNotFoundException(username);
    return user;
  }

  @Transactional
  public Collection<UserDTO> getUsersFollowing(Principal principal) {
    User user = getUser(principal.getName());
    return convertUsersToDTOs(user.getFollowing());
  }

  @Transactional
  public Collection<UserDTO> getUsersFollowers(Principal principal) {
    User user = getUser(principal.getName());
    return convertUsersToDTOs(user.getFollowers());
  }

  @Transactional
  public UserProfileDTO getUserProfile(Principal principal) {
      System.err.println("principal:" + (principal == null));
    User user = getUser(principal.getName());
    System.out.println(user.getName() + ";" + user.getUsername());
    int tweetCount = (tweetRepository.findAllByAuthor(user) != null)?tweetRepository.findAllByAuthor(user).size():0;
    return new UserProfileDTO(user, tweetCount);
  }

  @Transactional
  public boolean usernameExists(String username) {
    boolean usernameExists = false;
    if (username != null) {
        User user = getUser(username);
        usernameExists = user != null;
    }
      System.err.println("usernameExists:" + usernameExists + ";" + username);
    return usernameExists;
  }

  public UserDTO createUser(UserDTO userDTO) {
    if(usernameExists(userDTO.getUsername())) 
      throw new InvalidUserException(userDTO.getUsername());
    
    User user = new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getName(), userDTO.getSurname());
    User saved = userRepository.save(user);
    return new UserDTO(saved);
  }
  
  private User getUser(String username) {
    return userRepository.findOneByUsername(username);
  }

  private List<UserDTO> convertUsersToDTOs(Set<User> users) {
    return users.stream().map(UserDTO::new).collect(toList());
  }
  
  
  public static class InvalidUserException extends RuntimeException {

    private InvalidUserException(String username) {
      super("'" +  username + "'");
    }
  }
}
