package com.java.jpa.springbootjparelaciones.repositories;

import com.java.jpa.springbootjparelaciones.entities.Client;
import com.java.jpa.springbootjparelaciones.entities.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
