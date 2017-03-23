package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.RoleDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.JsonService;
import net.proselyte.springsecurityapp.service.SecurityService;
import net.proselyte.springsecurityapp.service.UserService;
import net.proselyte.springsecurityapp.service.UserServiceImpl;
import net.proselyte.springsecurityapp.validator.UserValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static java.awt.SystemColor.window;

/**
 * Controller for {@link net.proselyte.springsecurityapp.model.User}'s pages.
 *
 * @author Eugene Suleimanov
 * @version 1.0
 */

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    public JsonService jsonService ;


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/welcome";
    }

    @RequestMapping(value ="/vk", method = RequestMethod.GET)
    public String vk(){
        return "redirect:https://oauth.vk.com/authorize?client_id=5934856&display=page&redirect_uri=http://localhost:8087/example&response_type=code&v=5.62";
    }

    @RequestMapping(value ="/facebook", method = RequestMethod.GET)
    public String facebook(){
        return "redirect:https://www.facebook.com/v2.8/dialog/oauth?client_id=628809023972734&response_type=token&redirect_uri=http://localhost:8087/fk";
        //return "oops";
    }

    @RequestMapping(value ="/fk", method = RequestMethod.GET)
    public String fk(){
        return "oops";
    }

    @RequestMapping(value ="/fk1", method = RequestMethod.GET)
    public String fk1(String access_token){
        JSONObject jSONObject =  jsonService.readJsonFromUrl("https://graph.facebook.com/me?access_token="+access_token);
        User socialUser=new User();
        System.out.println(jSONObject);
        socialUser.setUsername(jSONObject.getString("id"));
        socialUser.setPassword(access_token);
        if(userService.findByUsername(socialUser.getUsername()).size()==0) {
            userService.save(socialUser);
        }
        else{
            User user=userService.findByUsername(socialUser.getUsername()).get(0);
            user.setPassword(access_token);
            userDao.save(user);
        }

        securityService.autoLogin(socialUser.getUsername(), access_token);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public  String example(@RequestParam("code") String code){
        JSONObject jSONObject =  jsonService.readJsonFromUrl("https://oauth.vk.com/access_token?client_id=5934856&client_secret=EcwoQYo6Ypt47QxCv6SY&redirect_uri=http://localhost:8087/example&code="+code);
        User socialUser=new User();
        socialUser.setUsername(String.valueOf(jSONObject.getLong("user_id")));
        socialUser.setPassword(String.valueOf(jSONObject.getLong("user_id")));
        String pas = socialUser.getPassword();
//        JSONObject jsonS=jsonService.readJsonFromUrl("https://api.vk.com/method/users.get?user_ids="+jSONObject.getLong("user_id"));
//        System.out.println(jsonS);
        if(userService.findByUsername(socialUser.getUsername()).size()==0) {
            userService.save(socialUser);
        }
        securityService.autoLogin(socialUser.getUsername(), pas);
        return "redirect:/welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Username or password is incorrect.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
