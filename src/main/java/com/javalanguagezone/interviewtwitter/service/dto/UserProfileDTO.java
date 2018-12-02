package com.javalanguagezone.interviewtwitter.service.dto;

import com.javalanguagezone.interviewtwitter.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE)
public class UserProfileDTO {
  private Long id;
  private String username;
  private String name;
  private String surname;
  private int numberOfTweets;
  private int numberOfFollowers;
  private int numberOfUserFollowing;
  

  public UserProfileDTO(User user, int numberOfTweets) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.name = user.getName();
    this.surname = user.getSurname();
    this.numberOfTweets = numberOfTweets;
    this.numberOfFollowers = user.getFollowers().size();
    this.numberOfUserFollowing = user.getFollowing().size();;
  }
}
