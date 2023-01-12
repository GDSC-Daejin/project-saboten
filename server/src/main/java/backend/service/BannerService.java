package backend.service;

import backend.controller.dto.BannerDto;
import backend.exception.ApiException;
import backend.model.banner.BannerEntity;
import backend.repository.banner.BannerRepository;
import common.message.BannerResponseMessage;
import common.model.reseponse.banner.BannerResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BannerService {
    private final BannerRepository bannerRepository;

    @Autowired
    public BannerService(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }
    public List<BannerResponse> findBanners() {
        List<BannerEntity> bannerEntities = bannerRepository.findAll();

        List<BannerResponse> banners = new ArrayList<>();
        for(BannerEntity bannerEntity : bannerEntities) {
            banners.add(bannerEntity.toDto().toBannerResponse());
        }
        return banners;
    }
}
