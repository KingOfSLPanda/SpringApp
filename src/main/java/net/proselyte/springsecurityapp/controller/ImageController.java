package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.Map;

@Controller
public class ImageController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @RequestMapping(value = "/img")
    public String img(Model model) throws IOException {
        model.addAttribute("imgURL", cloudinaryService.saveSomething().get("secure_url"));
        return "img";
    }

    @RequestMapping(value="/imggray")
    public String imgGray(@RequestParam String url, Model model) throws IOException {
        model.addAttribute("imgURL", cloudinaryService.changeGray(url));
        return "img";
    }

    @RequestMapping(value="/imgsepia")
    public String imgSepia(@RequestParam String url, Model model) throws IOException {
        model.addAttribute("imgURL", cloudinaryService.changeSepia(url));
        return "img";
    }

    @RequestMapping(value="/img+")
    public @ResponseBody String imgChange(@RequestParam String imgURL) throws IOException {
        System.out.println("Change");
        imgURL=cloudinaryService.changeSize(imgURL);
        return imgURL;
    }
}
