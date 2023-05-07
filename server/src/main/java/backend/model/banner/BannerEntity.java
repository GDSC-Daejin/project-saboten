package backend.model.banner;

import backend.controller.dto.BannerDto;
import backend.model.category.CategoryEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "banner_subtitle", nullable = false, length = 50)
    private String bannerSubtitle;

    @Column(name = "banner_url", nullable = false)
    private String bannerUrl;

    private String bannerStartColor;
    private String bannerEndColor;

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    public BannerDto toDto() {
        return BannerDto.builder()
                .bannerId(bannerId)
                .bannerTItle(bannerTitle)
                .bannerSubtitle(bannerSubtitle)
                .bannerStartColor(bannerStartColor)
                .bannerEndColor(bannerEndColor)
                .category(category.toDto())
                .bannerUrl(bannerUrl)
                .build();
    }
}
