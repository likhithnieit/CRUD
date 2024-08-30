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
import org.springframework.web.bind.annotation.*;

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
                            "Email is already used")
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
    @GetMapping("/edit")
    String showEditPage(Model model, @RequestParam int id){

        Client client = repo.getClient(id);
        if(client == null){
            return "redirect:/clients";
        }
        model.addAttribute("client",client);

        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName(client.getFirstName());
        clientDto.setLastName(client.getLastName());
        clientDto.setEmail(client.getEmail());
        clientDto.setPhone(client.getPhone());
        clientDto.setAddress(client.getAddress());

        model.addAttribute("clientDto",clientDto);
        return "clients/edit";
    }

    @PostMapping("/edit")
    String updateClient(Model model,@RequestParam int id,
                        @Valid @ModelAttribute ClientDto clientDto,
                        BindingResult result){

        Client client = repo.getClient(id);
        if (client == null){
            return "redirect:clients";
        }
        model.addAttribute("client",client);

        if (result.hasErrors()){
            return "clients/edit";
        }

        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());

        repo.updateClient(client);

        return "redirect:/clients";
    }
    @GetMapping("/delete")
    String deleteClient(@RequestParam int id){
        Client client = repo.getClient(id);

        if(client == null){
            return "redirect:/clients";
        }
        repo.deleteClient(id);

        return "redirect:/clients";
    }
}

