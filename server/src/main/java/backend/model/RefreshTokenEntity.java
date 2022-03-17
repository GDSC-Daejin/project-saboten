package backend.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenEntity {
    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
}