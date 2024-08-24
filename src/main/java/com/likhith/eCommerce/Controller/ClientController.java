package com.likhith.eCommerce.Controller;

import com.likhith.eCommerce.Models.Client;
import com.likhith.eCommerce.Models.ClientDto;
import com.likhith.eCommerce.Repository.ClientsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @GetMapping("/create")
    public String showCreatePage(Model model){
        ClientDto clientDto = new ClientDto();
        model.addAttribute("clientDto",clientDto);
        return "clients/create";
    }
    @PostMapping("/create")
    public String createClients(@Valid @ModelAttribute ClientDto clientDto, BindingResult result){
        if (repo.getClient(clientDto.getEmail()) != null){
            result.addError(
                    new FieldError("clientDto", "email",
                            clientDto.getEmail(),false,null,null,
                            "Email adress is already used")
            );
        }
        if (result.hasErrors()){
            return "clients/create";
        }

        Client newClient = new Client();
        newClient.setFirstName(clientDto.getFirstName());
        newClient.setLastName(clientDto.getLastName());
        newClient.setEmail(clientDto.getEmail());
        newClient.setPhone(clientDto.getPhone());
        newClient.setAddress(clientDto.getAddress());
        newClient.setCreatedAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        repo.createClient(newClient);

        return "redirect:/clients";



    }

}
