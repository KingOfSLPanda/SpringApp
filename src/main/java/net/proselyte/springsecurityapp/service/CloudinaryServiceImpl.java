package net.proselyte.springsecurityapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import net.proselyte.springsecurityapp.dao.ImageDao;
import net.proselyte.springsecurityapp.model.Image;
import org.json.JSONObject;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Image saveSomething() throws IOException {
        Image image=new Image();
        Map uploadResult;
        Map params=ObjectUtils.asMap("public_id","sleepingpanda");
        uploadResult=cloudinary.uploader().upload(new File("C:\\Users\\Y50-70\\Downloads\\картинки\\рик.jpg"), params);
        if(imageDao.findByUrlOriginal(String.valueOf(uploadResult.get("secure_url"))).size()==0){
            image.setUrl(String.valueOf(uploadResult.get("secure_url")));
            image.setUrlOriginal(String.valueOf(uploadResult.get("secure_url")));
            imageDao.save(image);
            System.out.println("id= " + uploadResult.get("public_id"));
            System.out.println("url= " + uploadResult.get("secure_url"));
            System.out.println("----");
            System.out.println((imageDao.findByUrlOriginal(String.valueOf(uploadResult.get("secure_url")))).size());
        }
        else{
            image=imageDao.findByUrlOriginal(String.valueOf(uploadResult.get("secure_url"))).get(0);
        }
        return image;
    }

    @Override
    public String changeGray(String url) throws IOException {
        Image image=imageDao.findByUrl(url).get(0);
        Map uploadResult = cloudinary.uploader().upload(url, ObjectUtils.asMap("transformation", new Transformation().effect("grayscale")));
        image.setUrl(String.valueOf(uploadResult.get("secure_url")));
        imageDao.save(image);
        return image.getUrl();
    }

    @Override
    public String changeSepia(String url) throws IOException {
        Image image=imageDao.findByUrl(url).get(0);
        Map uploadResult = cloudinary.uploader().upload(url, ObjectUtils.asMap("transformation", new Transformation().effect("sepia")));
        image.setUrl(String.valueOf(uploadResult.get("secure_url")));
        imageDao.save(image);
        return image.getUrl();
    }

    @Override
    public String    changeSize(String url, String s) throws IOException {
        Image image=imageDao.findByUrl(url).get(0);
        System.out.println("@<->@");
        Map uploadResult = cloudinary.uploader().upload(url, ObjectUtils.emptyMap());
        if(s.equals("+"))
        {
            uploadResult = cloudinary.uploader().upload(url, ObjectUtils.asMap("transformation", new Transformation().width((Long)uploadResult.get("width")+50).height((Long)uploadResult.get("height")+50)));

        }
        else{
            uploadResult = cloudinary.uploader().upload(url, ObjectUtils.asMap("transformation", new Transformation().width((Long)uploadResult.get("width")-50).height((Long)uploadResult.get("height")-50)));

        }
        image.setUrl(String.valueOf(uploadResult.get("secure_url")));
        imageDao.save(image);
        System.out.println(String.valueOf(uploadResult.get("secure_url")));
        return image.getUrl();
    }

    @Override
    public Integer getWidth(String url) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(url, ObjectUtils.emptyMap());
        Integer width=(Integer) uploadResult.get("width");
        return width;
    }

    @Override
    public String getOriginal(String url) throws IOException {
        Image image=imageDao.findByUrl(url).get(0);
        image.setUrl(image.getUrlOriginal());
        imageDao.save(image);
        return image.getUrl();
    }

    @Bean
    public Cloudinary createCloud(){
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "sleepingpanda",
                "api_key", "153272752865264",
                "api_secret", "YUOdMo7YnEkrUpDfO5ljdhxbA2U"));
    }
}
