package backend.model.compositekey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class VoteSelectPK implements Serializable {
    private Long post;
    private Long user;
}