package backend.model.compositekey;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class PostScrapPK implements Serializable {
    private Long post;
    private Long user;
}
