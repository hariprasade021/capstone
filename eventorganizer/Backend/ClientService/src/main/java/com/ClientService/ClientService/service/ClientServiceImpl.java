package com.ClientService.ClientService.service;

import com.ClientService.ClientService.dto.ClientWithFeedbackDTO;
import com.ClientService.ClientService.feignClient.EventFeign;
import com.ClientService.ClientService.feignClient.FeedbackFeign;
import com.ClientService.ClientService.model.Client;
import com.ClientService.ClientService.repository.ClientRepository;
import com.eventManagement.dto.EventDTO;
import com.eventManagement.dto.UserDTO;
import com.eventManagement.feignclient.UserFeign;
import com.eventManagement.model.Event;
import com.feedbackService.dto.EventFeedbackDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientRepository clientRepo;

    @Autowired
    private EventFeign eventFeign;

    @Autowired
    private FeedbackFeign feedbackFeign;

//    public Client createClient(Client client) {
//        Client savedClient = clientRepo.save(client);
////        savedClient.setEvents(fetchEvents(savedClient));
//        return savedClient;
//    }

    public Client createClient(Client client) {
        client = clientRepo.save(client);

        if (client.getEvents() != null)
        {
            for (Long eventId : client.getEvents()) {
                eventFeign.assignEventToClient(eventId, client.getClientId());
            }
        }

        return client;
    }

    public Client getClientById(Long clientId) {
        return clientRepo.findById(clientId).orElseThrow(() ->
                new RuntimeException("Client with ID " + clientId + " not found."));
    }
//    public Client getClientById(Long clientId) {
//        Optional<Client> client = clientRepo.findById(clientId);
//        return client.orElse(null); // Return null if client is not found
//    }

    // Get all clients
    public List<Client> getAllClients() {
        return clientRepo.findAll();
    }

    // Update client information
    public Client updateClient(Long clientId, Client client) {
        if (clientRepo.existsById(clientId)) {
            client.setClientId(clientId);
            return clientRepo.save(client);
        }
        return null;
    }

    // Delete a client by clientId
    public void deleteClient(Long clientId) {
        clientRepo.deleteById(clientId);
    }


//    public ClientWithFeedbackDTO getClientWithFeedback(Long clientId) {
//        Client client = clientRepo.findById(clientId)
//                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));
//
//        List<EventDTO> eventsWithFeedback = client.getEvents().stream().map(eventId -> {
//            EventDTO eventWithFeedback = new EventDTO();
//            var event = eventFeign.getEventById(eventId);
//            eventWithFeedback.setEventId(event.getEventId());
//            eventWithFeedback.setFeedback(event.getFeedback()); // Assuming feedback is stored in the event's description
//            return eventWithFeedback;
//        }).collect(Collectors.toList());
//
//        ClientWithFeedbackDTO response = new ClientWithFeedbackDTO();
//        response.setClientId(client.getClientId());
//        response.setName(client.getName());
//        response.setEmail(client.getEmail());
//        response.setEventswithFeedback(eventsWithFeedback);
//
//        return response;
//    }

    public ClientWithFeedbackDTO getClientWithFeedback(Long clientId) {
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        ClientWithFeedbackDTO clientDTO = new ClientWithFeedbackDTO();
        clientDTO.setClientId(client.getClientId());
        clientDTO.setName(client.getName());
        clientDTO.setEmail(client.getEmail());

        List<ClientWithFeedbackDTO.EventWithFeedback> eventswithFeedback = new ArrayList<>();
        List<Long> events = client.getEvents() != null ? client.getEvents() : new ArrayList<>();

        for (Long eventId : events) {
            ClientWithFeedbackDTO.EventWithFeedback eventWithFeedback = new ClientWithFeedbackDTO.EventWithFeedback();
            eventWithFeedback.setEventId(eventId);

            feedbackFeign.getFeedbackByEventId(eventId).stream()
                    .filter(feedback -> feedback.getClientId().equals(clientId))
                    .findFirst()
                    .ifPresent(feedback -> eventWithFeedback.setFeedback(feedback.getFeedbackText()));

            eventswithFeedback.add(eventWithFeedback);
        }

        clientDTO.setEventswithFeedback(eventswithFeedback);
        return clientDTO;
    }


}
