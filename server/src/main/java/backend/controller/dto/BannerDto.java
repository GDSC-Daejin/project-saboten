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
    private String bannerText;
    private String bannerUrl;

    public BannerResponse toBannerResponse() {
        return new BannerResponse(this.bannerId, this.bannerTItle, this.bannerTItle, this.bannerUrl);
    }

    public BannerEntity toEntity() {
        return BannerEntity.builder()
                .bannerId(bannerId)
                .bannerTitle(bannerTItle)
                .bannerText(bannerText)
                .bannerUrl(bannerUrl)
                .build();
    }
}
