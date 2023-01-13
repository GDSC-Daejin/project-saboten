package backend.model.banner;

import backend.controller.dto.BannerDto;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="TB_Banner")
public class BannerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id", nullable = false)
    private Long bannerId;

    @Column(name = "banner_title", nullable = false, length = 50)
    private String bannerTitle;

    @Column(name = "banner_text", length = 50)
    private String bannerText;

    @Column(name = "banner_url", nullable = false)
    private String bannerUrl;

    public BannerDto toDto() {
        return BannerDto.builder()
                .bannerId(bannerId)
                .bannerTItle(bannerTitle)
                .bannerText(bannerText)
                .bannerUrl(bannerUrl)
                .build();
    }
}
