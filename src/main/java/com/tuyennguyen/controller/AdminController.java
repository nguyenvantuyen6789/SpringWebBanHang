package com.tuyennguyen.controller;

import com.tuyennguyen.entity.Product;
import com.tuyennguyen.entity.User;
import com.tuyennguyen.serivce.ProductService;
import com.tuyennguyen.serivce.UserService;
import com.tuyennguyen.util.UtilCon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController extends WebController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    Logger log = LoggerFactory.getLogger(AdminController.class);
    private static final String ADMIN = "admin";
    private static final String TITLE = ADMIN;

    @GetMapping(value = "/admin-login")
    public String login(Model model) {
        // log info
        log.debug("Go to: /admin-login");

        try {
            setCommon(model, TITLE);
        } catch (Exception e) {
            UtilCon.logData(log, e);
        }

        return UtilCon.toAdmin("admin-login");
    }

    @GetMapping(value = "/admin")
    public String admin(Model model) {
        // log info
        log.debug("Go to: /admin");

        try {
            setCommon(model, TITLE);

    //        List<Product> listProduct = productService.findAll();
            List<Product> listProduct = null;
            model.addAttribute("listProduct", listProduct);
            model.addAttribute("page", "product");

            List<User> listUser = userService.findAll();
            model.addAttribute("listUser", listUser);
        } catch (Exception e) {
            UtilCon.logData(log, e);
        }

        return UtilCon.goAdmin();
    }

}
