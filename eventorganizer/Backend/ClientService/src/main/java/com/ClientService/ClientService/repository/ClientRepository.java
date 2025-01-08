package com.ClientService.ClientService.repository;

import com.ClientService.ClientService.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
