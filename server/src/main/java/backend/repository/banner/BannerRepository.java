package backend.repository.banner;

import backend.model.banner.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannerRepository extends JpaRepository<BannerEntity, Long> {
    BannerEntity findByBannerId(Long bannerId);
}
