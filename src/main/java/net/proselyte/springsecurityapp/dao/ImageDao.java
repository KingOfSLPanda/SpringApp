package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.URL;

/**
 * Created by Y50-70 on 26.03.2017.
 */
public interface ImageDao extends JpaRepository<Image, Long> {
    Image findByPublicId(String url);
}
