package com.java.jpa.springbootjparelaciones.repositories;

import com.java.jpa.springbootjparelaciones.entities.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    //creamos un metodo para obtener por id con un query
    @Query("select c from Client  c left join fetch c.addresses where c.id=?1")
    Optional<Client> findOne(Long id);

    //se ocupa left join para tarer tenga o no facturas
    @Query("select c from Client  c left join fetch c.invoices where c.id=?1")
    Optional<Client> findOneInvoices(Long id);

    @Query("select c from Client  c left join fetch c.invoices left join fetch c.addresses left join fetch c.clientDetails where c.id=?1")
    Optional<Client> findAll(Long id);

}
