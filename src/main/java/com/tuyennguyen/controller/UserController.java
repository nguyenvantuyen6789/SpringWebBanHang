package com.tuyennguyen.controller;

import com.tuyennguyen.entity.Role;
import com.tuyennguyen.entity.User;
import com.tuyennguyen.repository.UserRepository;
import com.tuyennguyen.serivce.UserService;
import com.tuyennguyen.util.EnumRole;
import com.tuyennguyen.util.UtilHost;
import com.tuyennguyen.util.UtilCon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class UserController extends WebController {

    Logger log = LoggerFactory.getLogger(UserController.class);
    private static final String USER = "user";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @GetMapping(value = "/" + USER)
    public String showList(Model model) {
        // log info
        log.debug("Go to: /admin/user");

        try {
            String TITLE = "User";
            // backup db
            UtilCon.backUpDb();
            setCommon(model, TITLE);

            //set list
            List<User> listUser = userService.findAll();
            model.addAttribute("listUser", listUser);
            //set page
            model.addAttribute(UtilCon.PAGE, UtilCon.USER);
        } catch (Exception e) {
            UtilCon.logData(log, e);
        }

        return UtilCon.goAdmin();
    }

    @GetMapping(value = "/" + USER + "/them")
    public String them(Model model) {
        // log info
        log.debug("Go to: /admin/user/them");

        try {
            String TITLE = "Thêm User";
            // set host, bootstrap
            setCommon(model, TITLE);

            List<Role> listRole = getListRole();
            model.addAttribute("listRole", listRole);

            model.addAttribute(UtilCon.OBJ, new User());
            model.addAttribute(UtilCon.PAGE, UtilCon.USER_THEM);

        } catch (Exception e) {
            UtilCon.logData(log, e);
        }

        return UtilCon.goAdmin();
    }

    @PostMapping(value = "/" + USER + "/save")
    public ModelAndView save(@ModelAttribute(UtilCon.OBJ) User obj) {
        // log info
        log.debug("Go to: /admin/user/save/" + obj.getUserId());

        String PAGE = "";

        try {
            obj = UtilCon.trimObject(obj);

            int count = userRepo.countUserByUsernameOrEmail(obj.getUsername(), obj.getEmail());
            // if count > 0, not save more
            if (count > 0) {
                PAGE = "user/them";
            } else {
                PAGE = "user";
                userService.save(obj);
            }

        } catch (Exception e) {
            UtilCon.logData(log, e);
        }

        return new ModelAndView(UtilCon.REDICRECT + UtilHost.LOCALHOST + "/admin/" + PAGE);
    }

    @GetMapping(value = "/" + USER + "/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        // log info
        log.debug("Go to: /admin/user/edit/" + id);

        try {
            String TITLE = "Sửa User";
            // set host, bootstrap
            setCommon(model, TITLE);

            List<Role> listRole = getListRole();
            model.addAttribute("listRole", listRole);

            User obj = userService.findById(id).get();
            model.addAttribute(USER, obj);
            model.addAttribute(UtilCon.PAGE, UtilCon.USER_EDIT);

        } catch (Exception e) {
            UtilCon.logData(log, e);
        }

        return UtilCon.goAdmin();
    }

    @PostMapping(value = "/" + USER + "/update")
    public ModelAndView update(@ModelAttribute(UtilCon.OBJ) User obj) {
        // log info
        log.debug("Go to: /admin/user/update/" + obj.getUserId());

        String PAGE = "";

        try {
            obj = UtilCon.trimObject(obj);

            int count = userRepo.countUserByUsernameOrEmail(obj.getUsername(), obj.getEmail());
            // if count > 0, not save more
            if (count > 1) {
                PAGE = "user/edit/" + obj.getUserId();
            } else {
                PAGE = "user";
                userService.save(obj);
            }
        } catch (Exception e) {
            UtilCon.logData(log, e);
        }

        return new ModelAndView(UtilCon.REDICRECT + UtilHost.LOCALHOST + "/admin/" + PAGE);
    }

    @GetMapping(value = "/" + USER + "/delete/{id}")
    public ModelAndView delete(@PathVariable int id) {
        // log info
        log.debug("Go to: /admin/user/delelte/" + id);

        try {
            userService.deleteById(id);
        } catch (Exception e) {
            UtilCon.logData(log, e);
        }

        return new ModelAndView(UtilCon.REDICRECT + UtilHost.LOCALHOST + "/admin/" + USER);
    }

    private List<Role> getListRole() {
        Role role;

        // set list role
        List<Role> listRole = new ArrayList<>();
        role = new Role(EnumRole.ADMIN.getRoleId(), EnumRole.ADMIN.getRoleName());
        listRole.add(role);
        role = new Role(EnumRole.USER.getRoleId(), EnumRole.USER.getRoleName());
        listRole.add(role);

        return listRole;
    }

}
