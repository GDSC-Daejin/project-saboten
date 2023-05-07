package backend.controller.dto;

import backend.model.banner.BannerEntity;
import common.model.reseponse.banner.BannerResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BannerDto {
    private Long bannerId;
    private String bannerTItle;
    private String bannerSubtitle;
    private String bannerUrl;
    private CategoryDto category;

    public BannerResponse toBannerResponse() {
        return new BannerResponse(
                this.bannerId,
                this.bannerTItle,
                this.bannerSubtitle,
                this.category.getCategoryName(),
                this.bannerUrl);
    }

    public BannerEntity toEntity() {
        return BannerEntity.builder()
                .bannerId(bannerId)
                .bannerTitle(bannerTItle)
                .bannerSubtitle(bannerSubtitle)
                .category(category.toEntity())
                .bannerUrl(bannerUrl)
                .build();
    }
}
