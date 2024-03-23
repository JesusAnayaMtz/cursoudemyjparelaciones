package com.java.jpa.springbootjparelaciones.repositories;

import com.java.jpa.springbootjparelaciones.entities.ClientDetails;
import org.springframework.data.repository.CrudRepository;

public interface ClienteDetailsRepository extends CrudRepository<ClientDetails, Long> {
}
