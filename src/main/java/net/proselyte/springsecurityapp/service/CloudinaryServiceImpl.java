package net.proselyte.springsecurityapp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import net.proselyte.springsecurityapp.dao.ImageDao;
import net.proselyte.springsecurityapp.model.Image;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private ImageDao imageDao;

    @Override
    public String sepia(Map map) {
        Cloudinary cloudinary=new Cloudinary();
        String imageHtml = cloudinary.url().format(String.valueOf(map.get("format")))
                .transformation(new Transformation().width(100).height(150).effect("sepia")).imageTag();
        return null;
    }

    @Override
    public Map saveSomething() throws IOException {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "sleepingpanda",
                "api_key", "153272752865264",
                "api_secret", "YUOdMo7YnEkrUpDfO5ljdhxbA2U"));
        File toUpload = new File("C:\\Users\\Y50-70\\Downloads\\картинки\\рик.jpg");
//        Map uploadresult = cloudinary.uploader().upload(toUpload, ObjectUtils.asMap(
//                "transformation", new Transformation().width(440).height(440)));
//      ObjectUtils.asMap("transformation", new Transformation().crop("limit").width(40).height(40)));
        Map params=ObjectUtils.asMap("public_id","sleepingpanda","transformation", new Transformation().width(400).height(400).effect("sepia"));
        Map uploadResult=cloudinary.uploader().upload(new File("C:\\Users\\Y50-70\\Downloads\\картинки\\рик.jpg"), params);
        Image image=new Image();
        image.setPublicId(String.valueOf(uploadResult.get("public_id")));
        imageDao.save(image);
        System.out.println(uploadResult.get("public_id"));
        System.out.println(uploadResult.get("secure_url"));
        System.out.println("----");
        //File toDownload=new File((String) uploadResult.get("secure_url"));
        // System.out.println(cloudinary.url().imageTag(String.valueOf(uploadResult.get("public_id"))+".jpg"));
        // uploadResult=cloudinary.uploader().upload(cloudinary.url().format("jpg").imageTag(String.valueOf(uploadResult.get("url"))), ObjectUtils.asMap(
       //       "transformation", new Transformation().width(40).height(40).effect("sepia")));
        return uploadResult;
    }

    @Override
    public Map changeSize(int w, int h) {

        return null;
    }
}
