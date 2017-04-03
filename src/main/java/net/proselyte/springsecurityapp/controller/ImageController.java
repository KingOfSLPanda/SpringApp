package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.model.Image;
import net.proselyte.springsecurityapp.service.CloudinaryService;
import org.omg.CORBA.IMP_LIMIT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.font.ImageGraphicAttribute;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.Map;

@Controller
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @RequestMapping(value = "/img")
    public String img(Model model) throws IOException {
        model.addAttribute("image", cloudinaryService.saveSomething());
        return "img";
    }

    @RequestMapping(value="/imggray", method = RequestMethod.POST)
    public @ResponseBody String imgGray(@RequestParam String imgURL) throws IOException {
        imgURL = cloudinaryService.changeGray(imgURL);
        return imgURL;
    }

    @RequestMapping(value="/imgsepia", method = RequestMethod.POST)
    public @ResponseBody String imgSepia(@RequestParam String imgURL) throws IOException {
        imgURL = cloudinaryService.changeSepia(imgURL);
        return imgURL;
    }

    @RequestMapping(value="/img+", method = RequestMethod.POST)
    public @ResponseBody String imgChange(@RequestParam String imgURL, String s) throws IOException {
        imgURL=cloudinaryService.changeSize(imgURL, s);
        return imgURL;
    }

    @RequestMapping(value="/getimgwidth", method = RequestMethod.POST)
    public @ResponseBody Integer getImgWidth(@RequestParam String imgURL) throws IOException {
        Integer width =cloudinaryService.getWidth(imgURL);
        return width;
    }

    @RequestMapping(value="/getoriginal", method = RequestMethod.POST)
    public @ResponseBody String getImgOriginal(@RequestParam String imgURL) throws IOException {
        imgURL = cloudinaryService.getOriginal(imgURL);
        return imgURL;
    }
}
