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
        Map map = cloudinaryService.saveSomething();
        model.addAttribute("imgMap", map);
        return "img";
    }

    @RequestMapping(value="/img+")
    public @ResponseBody Map imgChange(@RequestParam Map imgMap){
        System.out.println("---");
        System.out.println(imgMap);
        System.out.println("---");
        Integer w=(Integer)imgMap.get("width");
        Integer h=(Integer)imgMap.get("height");
        return imgMap;
    }
}
