package com.javalanguagezone.interviewtwitter.service.dto;

import com.javalanguagezone.interviewtwitter.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class UserDTO {
  private Long id;
  private String username;
  private String password;
  private String name;
  private String surname;

  public UserDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();    
    this.password = user.getPassword();
    this.name = user.getName();
    this.surname = user.getSurname();
  }
}
