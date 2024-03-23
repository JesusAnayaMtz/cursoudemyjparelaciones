package com.java.jpa.springbootjparelaciones.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastName;

    // @JoinTable(name = "tbl_clientes_to_direcciones", joinColumns = @JoinColumn(name = "id_cliente"), inverseJoinColumns = @JoinColumn(name = "id_direcciones"), uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}))
    //@JoinColumn(name = "client_id")  //pernozalizamos nuestra llave foranea dentro de la tabla addresses
    //cascade se indicia para que cuando se crea un cliente tambien se cree la direccion y se guarde la relacion y si se elimine igual se elimine la relacion y la direccion
    //con el jointable personalizamos nuestra tabla de enlaze la cual se desacopla y se crea aparte para la relacion de direcciones con cliente
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)   //esto significa que un cliente puede tener muchas direcciones
    @JoinTable(name = "tbl_clientes_to_direcciones", joinColumns = @JoinColumn(name = "id_cliente"), inverseJoinColumns = @JoinColumn(name = "id_direcciones"), uniqueConstraints = @UniqueConstraint(columnNames = {"id_direcciones"}))
    private Set<Address> addresses;

    //se estrutura una relacion inversa en la cual tendra la llave primaria sera invoice si en la clase se pude el mapedby esta no puede tener el joincolumn
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private Set<Invoice> invoices;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client")
    private ClientDetails clientDetails;

    //al ser una relacion tipo lista se debe inicializar dentro del constructor o directo en la declaracion de la variable
    public Client() {
        addresses = new HashSet<>();
        invoices = new HashSet<>();
    }
    public Client(String name, String lastName) {
        this();
        this.name = name;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(Set<Invoice> invoices) {
        this.invoices = invoices;
    }

    public ClientDetails getClientDetails() {
        return clientDetails;
    }

    public void setClientDetails(ClientDetails clientDetails) {
        this.clientDetails = clientDetails;
        clientDetails.setClient(this);  // a detalles cliente le pasamos el cliente
    }

    public void removeClienteDetails(ClientDetails clientDetails) {
        clientDetails.setClient(null);  //dejamos en null al cliente en detalles de cliente
        this.clientDetails = null;  //dejamos en null el detalle cliente en cliente

    }



    //metodo para agregar las facturas al cliente
    public void addInvoice(Invoice invoice){
        invoices.add(invoice);  //agregamos la factura
        invoice.setClient(this);  //le pasamos a la factura el cliente creado
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addresses=" + addresses +
                ", invoices=" + invoices +
                ", clientDetails=" + clientDetails +
                '}';
    }
}
