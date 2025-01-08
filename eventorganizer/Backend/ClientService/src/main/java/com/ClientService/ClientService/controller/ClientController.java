package com.ClientService.ClientService.controller;

import com.ClientService.ClientService.dto.ClientWithFeedbackDTO;
import com.ClientService.ClientService.model.Client;
import com.ClientService.ClientService.service.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/client")
public class ClientController
{
    @Autowired
    private ClientServiceImpl clientService;

    // Create a new client
//    @PostMapping
//    public Client createClient(@RequestBody Client client) {
//        return clientService.createClient(client);
//    }
//
//    // Get a client by ID
//    @GetMapping("/{clientId}")
//    public Client getClientById(@PathVariable Long clientId) {
//        return clientService.getClientById(clientId);  // Will return null if not found
//    }
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @GetMapping("/{clientId}")
    public Client getClientById(@PathVariable Long clientId)
    {
        return clientService.getClientById(clientId);
    }


        // Get all clients
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    // Update an existing client
    @PutMapping("/{clientId}")
    public Client updateClient(@PathVariable Long clientId, @RequestBody Client client) {
        return clientService.updateClient(clientId, client);  // Returns updated client or null
    }

    // Delete a client by clientId
    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable Long clientId) {
        clientService.deleteClient(clientId);
    }

    @GetMapping("/{clientId}/feedback")
    public ClientWithFeedbackDTO getClientWithFeedback(@PathVariable Long clientId) {
        return clientService.getClientWithFeedback(clientId);
    }





}
