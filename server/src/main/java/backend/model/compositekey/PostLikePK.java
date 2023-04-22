package backend.model.compositekey;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class PostLikePK implements Serializable {
    private Long post;
    private Long user;
}