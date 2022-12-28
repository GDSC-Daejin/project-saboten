package backend.controller.dto;

import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class PostScrapDto {
    public PostDto post;

    public UserDto user;
}
