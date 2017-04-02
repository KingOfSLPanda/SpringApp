package net.proselyte.springsecurityapp.dao;

import net.proselyte.springsecurityapp.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.URL;
import java.util.List;

/**
 * Created by Y50-70 on 26.03.2017.
 */
public interface ImageDao extends JpaRepository<Image, Long> {
    List<Image> findByUrl(String url);
    List<Image> findById(String id);
    List<Image> findByUrlOriginal(String url);
}
