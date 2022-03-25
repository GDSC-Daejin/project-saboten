package backend.model.user;

import backend.model.user.UserEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity @Table(name = "TB_Refresh_Token")
public class RefreshTokenEntity {

    @Id
    @Column(name="user_id", nullable = false)
    private Long userId;

    @MapsId
    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    public UserEntity user;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;
}