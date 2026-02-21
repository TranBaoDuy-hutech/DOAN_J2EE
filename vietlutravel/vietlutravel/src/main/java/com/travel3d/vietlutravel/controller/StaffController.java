package com.travel3d.vietlutravel.controller;

import com.travel3d.vietlutravel.model.Messages;
import com.travel3d.vietlutravel.service.MessageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/chat")
    public String staffChat(HttpSession session, Model model) {

        Object staff = session.getAttribute("user");
        if (staff == null) return "redirect:/login";

        model.addAttribute("staffId", 2);
        model.addAttribute("customers", messageService.getCustomerList());

        return "staff/chat";
    }

    @GetMapping("/history/{id}")
    @ResponseBody
    public List<Messages> history(@PathVariable int id){
        return messageService.getHistory(id);
    }
}
