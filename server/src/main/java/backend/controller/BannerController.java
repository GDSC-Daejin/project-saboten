package backend.controller;

import backend.controller.annotation.Version1RestController;
import backend.service.BannerService;
import common.message.BannerResponseMessage;
import common.model.reseponse.ApiResponse;
import common.model.reseponse.banner.BannerResponse;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@Version1RestController
public class BannerController {
    private final BannerService bannerService;

    @Autowired
    public BannerController(BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @ApiOperation(value =  "배너 전체 조회 API", notes = "모든 배너 불러오기")
    @GetMapping("/banners")
    public ApiResponse<List<BannerResponse>> getBanners() {
        List<BannerResponse> banners = bannerService.findBanners();
        return ApiResponse.withMessage(banners, BannerResponseMessage.BANNER_FIND_ALL);
    }
}
