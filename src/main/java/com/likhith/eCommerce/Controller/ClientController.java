package com.likhith.eCommerce.Controller;

import com.likhith.eCommerce.Models.Client;
import com.likhith.eCommerce.Repository.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientsRepository repo;

    @GetMapping
    public String getClients(Model model){
        List<Client> clients = repo.getClients();
        model.addAttribute("clients", clients);
        return "clients/index";
    }


}
