package com.example.error_management.controller;

import com.example.error_management.entity.Case;
import com.example.error_management.entity.LoginForm;
import com.example.error_management.entity.User;
import com.example.error_management.service.EMSService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
public class manageContoroller {

    @Autowired
    private EMSService emsService;

    @Autowired
    private HttpSession session;


    @GetMapping("index")
    public String index(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "index";
    }

    @PostMapping("index")
    public String login(@Validated @ModelAttribute("loginForm")LoginForm loginForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("error", "IDまたはパスワードが不正です");
            return "index";
        }
        if(emsService.findAcc(loginForm) != null){
            var user = emsService.findAcc(loginForm);
            session.setAttribute("user",user);
            return "redirect:/menu";
        }
        model.addAttribute("error", "IDまたはパスワードが不正です");
        return "index";
    }

    @GetMapping("menu")
    public String menu(Model model){
        model.addAttribute("namePiece",emsService.findNamePiece());
        System.out.println(emsService.findNamePiece());
        return "menu";
    }

    @GetMapping("errors/{id}")
    public String detail(@PathVariable("id") int id, Model model) {
        model.addAttribute("Errors",emsService.findById(id));
        model.addAttribute("Cases",emsService.findByIdCase(id));
        return "errors";
    }

    @GetMapping("insert")
    public String insert(){
        return "insert";
    }

    @PostMapping("insert")
    public  String insertPost(@RequestParam("name") String name){
        emsService.insertErrorName(name);
        return "redirect:menu";
    }

    @GetMapping("errors/insertCase")
    public String insertCase(@ModelAttribute("case")Case errorCase, Model model){
        model.addAttribute("errors",emsService.findErrorName());

        return "insertCase";
    }

    @PostMapping("insertCase")
    public String insCasPost(@ModelAttribute("case")Case errorCase,Model model){
        User user = (User) session.getAttribute("user");
        errorCase.setUser_id(user.getId());

        emsService.insertCase(errorCase);
        return "redirect:menu";
    }

    @GetMapping("/errors/case/{id}")
    public String detail1(@PathVariable("id") int id,Model model){
        model.addAttribute("cases",emsService.findUserId(id));
        model.addAttribute("errors",emsService.findErrorName());
        return "edit";
    }

    @PostMapping("errors/case/edit/{id}")
    public String delete(@PathVariable("id") int id){
        emsService.deleteCase(id);
        return "redirect:/menu" ;
    }

    @GetMapping("/detail/{id}")
    public String editDetail(@PathVariable("id") int id,Model model){
        model.addAttribute("cases",emsService.findUserId(id));
        model.addAttribute("errors",emsService.findErrorName());
        return "/detail";
    }

    @PostMapping("/detail/{id}")
    public String update(@ModelAttribute Case errorCase,@PathVariable("id")int id){
        Case errorcase = emsService.findByCaseId(id);
        errorcase.setDescription(errorCase.getDescription());
        errorcase.setSolve(errorCase.isSolve());
        errorcase.setSolution(errorCase.getSolution());
        emsService.updateCase(errorcase);

        return"redirect:/menu";
    }

    @GetMapping("/newUser")
    public String newUser(@ModelAttribute("user") User user){
        return "newUser";
    }

    @PostMapping("/newUser")
    public String newUserPost(@ModelAttribute("user") User user){
        emsService.insertUser(user);
        return "redirect:/index";
    }
}
