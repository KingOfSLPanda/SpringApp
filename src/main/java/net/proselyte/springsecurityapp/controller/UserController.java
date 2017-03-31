package net.proselyte.springsecurityapp.controller;

import net.proselyte.springsecurityapp.dao.RoleDao;
import net.proselyte.springsecurityapp.dao.UserDao;
import net.proselyte.springsecurityapp.model.User;
import net.proselyte.springsecurityapp.service.*;
import net.proselyte.springsecurityapp.validator.UserValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import twitter4j.auth.AccessToken;

import java.io.IOException;

import static java.awt.SystemColor.window;

/**
 * Controller for pages.
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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TwitterService twitterService;


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
        userForm.setEnabled(Boolean.TRUE);
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
    }

    @RequestMapping(value ="/fk", method = RequestMethod.GET)
    public String fk(){
        return "oops";
    }

    @RequestMapping(value ="/fk1", method = RequestMethod.GET)
    public String fk1(String access_token){
        JSONObject jSONObject =  jsonService.readJsonFromUrl("https://graph.facebook.com/me?access_token="+access_token);
        User socialUser;
        if(userService.findByUsername(jSONObject.getString("id")).size()==0) {
            socialUser=new User();
            socialUser.setUsername(jSONObject.getString("id"));
            socialUser.setPassword(access_token);
            socialUser.setName(jSONObject.getString("name"));
            socialUser.setEnabled(Boolean.TRUE);
            userService.save(socialUser);
        }
        else{
            socialUser=userService.findByUsername(jSONObject.getString("id")).get(0);
            socialUser.setPassword(bCryptPasswordEncoder.encode(access_token));
            userDao.save(socialUser);
        }

        securityService.autoLogin(jSONObject.getString("id"), access_token);
        return "redirect:/welcome";
    }

    @RequestMapping(value ="/twitter", method = RequestMethod.GET)
    public String twitter() throws Exception{
        return "redirect:"+twitterService.getAuthURL();
    }

    @RequestMapping(value ="/tw", method = RequestMethod.GET)
    public String tw(@RequestParam("oauth_token") String oauth_token, @RequestParam("oauth_verifier") String oauth_verifier){
        System.out.println("<--->oops");
        AccessToken accessToken = twitterService.getAccessToken(oauth_verifier);
        System.out.println(accessToken.getScreenName());
        String pas=accessToken.getToken();
        User socialUser;
        if (userService.findByUsername(String.valueOf(String.valueOf(accessToken.getUserId()))).size() == 0) {
            socialUser = new User();
            socialUser.setUsername(String.valueOf(accessToken.getUserId()));
            socialUser.setPassword(accessToken.getToken());
            socialUser.setName(accessToken.getScreenName());
            socialUser.setEnabled(Boolean.TRUE);
            userService.save(socialUser);
        } else {
            socialUser = userService.findByUsername(String.valueOf(accessToken.getUserId())).get(0);
            socialUser.setPassword(bCryptPasswordEncoder.encode(accessToken.getToken()));
            userDao.save(socialUser);
        }

        securityService.autoLogin(String.valueOf(accessToken.getUserId()), pas);

        return "redirect:/welcome";
    }

    @RequestMapping(value = "/example", method = RequestMethod.GET)
    public  String example(@RequestParam("code") String code){
        JSONObject jSONObject =  jsonService.readJsonFromUrl("https://oauth.vk.com/access_token?client_id=5934856&client_secret=EcwoQYo6Ypt47QxCv6SY&redirect_uri=http://localhost:8087/example&code="+code);
        String pas = jSONObject.getString("access_token");
        JSONObject jsonS=jsonService.readJsonFromUrl("https://api.vk.com/method/users.get?user_ids="+jSONObject.getLong("user_id"));
        User socialUser;
        if(userService.findByUsername(String.valueOf(jSONObject.getLong("user_id"))).size()==0) {
            socialUser=new User();
            socialUser.setUsername(String.valueOf(jSONObject.getLong("user_id")));
            socialUser.setPassword(jSONObject.getString("access_token"));
            socialUser.setName(((JSONObject)jsonS.getJSONArray("response").get(0)).getString("first_name")+ " " + ((JSONObject)jsonS.getJSONArray("response").get(0)).getString("last_name"));
            socialUser.setEnabled(Boolean.TRUE);
            userService.save(socialUser);
        }
        else{
            socialUser=userService.findByUsername(String.valueOf(jSONObject.getLong("user_id"))).get(0);
            socialUser.setPassword(bCryptPasswordEncoder.encode(jSONObject.getString("access_token")));
            userDao.save(socialUser);
        }

        securityService.autoLogin(String.valueOf(jSONObject.getLong("user_id")), pas);
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

    @RequestMapping(value = "/login3", method = RequestMethod.POST)
    public String loginSubmit(String username, String password) {
        securityService.autoLogin(username, password);
        return "redirect:/welcome";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        User user = userService.findByUsername(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()).get(0);
        model.addAttribute("name", user.getName());
        model.addAttribute("users",  userDao.findAll());
        model.addAttribute("currentUsername", user.getUsername());
        return "welcome";
    }

    @RequestMapping(value = "/block", method = RequestMethod.POST)
    public @ResponseBody String block(@RequestParam String username, @RequestParam String currentUsername) {
        User user = userService.findByUsername(username).get(0);
        if (user.getEnabled()) {
            user.setEnabled(false);
            //securityService.clearAuth();
        } else {
            user.setEnabled(true);
        }
        userDao.save(user);
        if(username.equals(currentUsername)){
           // securityService.clearAuth();
            return "REDIRECT";

        }
            return username;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        return "admin";
    }
}
