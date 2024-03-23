package com.java.jpa.springbootjparelaciones;

import com.java.jpa.springbootjparelaciones.entities.*;
import com.java.jpa.springbootjparelaciones.repositories.ClientRepository;
import com.java.jpa.springbootjparelaciones.repositories.ClienteDetailsRepository;
import com.java.jpa.springbootjparelaciones.repositories.InvoiceRepository;
import com.java.jpa.springbootjparelaciones.repositories.StudentRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootApplication
public class SpringbootJpaRelacionesApplication implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClienteDetailsRepository clienteDetailsRepository;

    @Autowired
    private StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaRelacionesApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        manyToManyFindById();
    }

    @Transactional
    public void manyToManyFindById(){

        //una forma de pasarle al estudiante el curso asignado
        /*Course course1 = new Course("Matematicas", "Andres");
        Course course2 = new Course("Ciencias", "Nadia");

        Optional<Student> optionalStudent1 = studentRepository.findById(1L);
        Optional<Student> optionalStudent2 = studentRepository.findById(2L);


        optionalStudent1.ifPresent(student1 -> {
            student1.setCourses(Set.of(course1, course2));
            studentRepository.save(student1);
            System.out.println(student1);
        });

        optionalStudent2.ifPresent(student2 -> {
            student2.setCourses(Set.of(course2));
            studentRepository.save(student2);
            System.out.println(student2);
        });*/

        //forma numero 2 de hacerlo
        Optional<Student> optionalStudent1 = studentRepository.findById(1L);
        Optional<Student> optionalStudent2 = studentRepository.findById(2L);

        Student student1 = optionalStudent1.get();
        Student student2 = optionalStudent2.get();

        Course course1 = new Course("Matematicas", "Andres");
        Course course2 = new Course("Ciencias", "Nadia");

        student1.setCourses(Set.of(course1,course2));
        student2.setCourses(Set.of(course2));

        studentRepository.saveAll(List.of(student1, student2));
        System.out.println(student1);
        System.out.println(student2);
    }

    @Transactional
    public void manyToMany(){
        Student student1 = new Student("Jose", "Diaz");
        Student student2 = new Student("Emma", "Ruiz");

        Course course1 = new Course("Matematicas", "Andres");
        Course course2 = new Course("Ciencias", "Nadia");

        student1.setCourses(Set.of(course1, course2));
        student2.setCourses(Set.of(course1));

        //salvamos ocupando un set ya que asi ponemos pasar los dos estudiantes o tambien se puede ocupar List.of
        studentRepository.saveAll(Set.of(student1, student2));
        System.out.println(student1);
        System.out.println(student2);
    }

    @Transactional
    public void oneToOneBidireccionalFindById(){
        ClientDetails clientDetails = new ClientDetails(true, 5000);  //creamos los detalles del cliente
        clienteDetailsRepository.save(clientDetails); //guardamos los detalles del cliente

        Optional<Client> optionalClient = clientRepository.findOne(1L);
        optionalClient.ifPresent(client -> {
            client.setClientDetails(clientDetails);    //le pasamos los detalles al cliente que se le asiganaran
            clientRepository.save(client);  //guardamos el cliente

            System.out.println(client);
        });

    }


    @Transactional
    public void oneToOneBidireccional(){
        Client client = new Client("Jorge", "Andrade");  //creamos un nuevo cliente

        ClientDetails clientDetails = new ClientDetails(true, 5000);  //creamos los detalles del cliente

        client.setClientDetails(clientDetails);    //le pasamos los detalles al cliente que se le asiganaran

        clientRepository.save(client);  //guardamos el cliente

        System.out.println(client);

    }

    @Transactional
    public void oneToOneFindById(){
        ClientDetails clientDetails = new ClientDetails(true, 5000);  //creamos los detalles del cliente
        clienteDetailsRepository.save(clientDetails); //guardamos los detalles del cliente

        //buscamos al cliente en la bd al cual le asiganermos los detalles del cliente
        Optional<Client> clientOptional = clientRepository.findOne(1L);
        clientOptional.ifPresent(client -> {
            client.setClientDetails(clientDetails);    //le pasamos los detalles al cliente que se le asiganaran
            clientRepository.save(client);  //guardamos el cliente
            System.out.println(client);
        });





    }

    @Transactional
    public void oneToOne(){
        ClientDetails clientDetails = new ClientDetails(true, 5000);  //creamos los detalles del cliente
        clienteDetailsRepository.save(clientDetails); //guardamos los detalles del cliente

        Client client = new Client("Jorge", "Andrade");  //creamos un nuevo cliente
        client.setClientDetails(clientDetails);    //le pasamos los detalles al cliente que se le asiganaran
        clientRepository.save(client);  //guardamos el cliente


        System.out.println(client);

    }

    @Transactional
    public void removeInvoiceBidireccionalFindById(){
        Optional<Client> optionalClient = clientRepository.findOne(1L);  //con un optional buscamos al cliente por id

        //con ifpresent vaidamos que el cliente exista
        optionalClient.ifPresent(client -> {
            Invoice invoice1 = new Invoice("Compras de la casa nueva", 3500L);
            Invoice invoice2 = new Invoice("Compras nuevas de la oficina", 6000L);

            //le agregamos las facturas al cliente existente
            client.addInvoice(invoice1);
            client.addInvoice(invoice2);

            //guardamos las modificaciones
            clientRepository.save(client);

            //imprimimos en consola
            System.out.println(client);
        });

        //buscamos al cliente con nuestro metodo findone para que traiga lso datos del cliente adress y invoices
        Optional<Client> optionalClientDb = clientRepository.findOne(1L);
        //validamos que el cliente exista
        optionalClientDb.ifPresent(client -> {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(2L);  //si existe buscamos la factura dentreo del cliente
            //validamos que exista la factura
            invoiceOptional.ifPresent(invoice -> {
                client.getInvoices().remove(invoice);  //removemos la factura por el id que pasamos al buscarla
                invoice.setClient(null);  //y seteamos al cliente con null para que quede vacio

                //salvamos
                clientRepository.save(client);
                //imprimimos
                System.out.println(client);
            });
        });
    }



    @Transactional
    public void oneToManyCreateBidireccionalFindById() {
        Optional<Client> optionalClient = clientRepository.findAll(1L);
        optionalClient.ifPresent(client -> {
            Invoice invoice = new Invoice("Compras de la casa", 1200L);
            Invoice invoice2 = new Invoice("Compras de la empresa", 7500L);
            client.addInvoice(invoice);
            client.addInvoice(invoice2);
            clientRepository.save(client);
            System.out.println(client);
        });
    }

    @Transactional
    public void oneToManyCreateBidireccional() {
        Client client = new Client("Gerardo", "Mora");  //se crea un cliente

        //se crean las facturas
        Invoice invoice = new Invoice("Compras de la casa", 1200L);
        Invoice invoice2 = new Invoice("Compras de la empresa", 7500L);

        //se crean dos direcciones
        Address address = new Address("Cordoba", 1234);
        Address address1 = new Address("Fortin", 4567);

        //ahora le asignaremos las direcciones al cliente
        Set<Address> addresses = new HashSet<>();
        addresses.add(address);
        addresses.add(address1);
        client.setAddresses(addresses);

        //creamos un array de facturas y le agregamos las facturas, despues le seteamos al cliente las facturas creadas
        client.addInvoice(invoice);
        client.addInvoice(invoice2);
        /*List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice);
        invoices.add(invoice2);
        client.setInvoices(invoices);*/

        //le seteamos a las facturas el cliente al que pertenecen
//        invoice.setClient(client);
//        invoice2.setClient(client);

        //guardamos el cliente e imprimimos
        clientRepository.save(client);
        System.out.println(client);

        //le eliminamos una direccion al cliente con un optional para pasar el id del que se le va a eliminar
        Optional<Client> cliente = clientRepository.findById(3L);
        cliente.ifPresent(client1 -> {
            client1.getAddresses().remove(address);
            clientRepository.save(client1);
            System.out.println(client1);
        });
    }

    @Transactional
    public void deleteAddressesFindById(){
        Optional<Client> optionalClient = clientRepository.findById(1L); //con un optional de cliente se busca
        optionalClient.ifPresent(client -> {   //con ifpresent se evalua que exista el cliente
            //si existe se crea la direccion y se le asignan al cliente y se guarda
            Address address3 = new Address("Orizaba", 7894);
            Address address4 = new Address("Huatusco", 5632);

            //se debe setear la direccion al cliente con un arrays.aslist y dentro le pasamos las direcciones
            Set<Address> addresses = new HashSet<>();
            addresses.add(address3);
            addresses.add(address4);
            client.setAddresses(addresses);
            //client.setAddresses(Arrays.asList(address3, address4));

            //actualizamos el cliente
            clientRepository.save(client);

            System.out.println(client);


            Optional<Client> optionalClient2 = clientRepository.findOne(1L); //con un optional de cliente se busca
            optionalClient2.ifPresent(client2 -> {              //validamos que el cliente exista
                client2.getAddresses().remove(address4);  //eliminamos la primera direccion
                clientRepository.save(client2);  //actualizamos el cliente guardando.
                System.out.println(client2);
            });
        });
    }


    @Transactional
    public void deleteAddress(){
        Client client = new Client("Gerardo","Mora" );  //se crea un cliente

        //se crean dos direcciones
        Address address = new Address("Cordoba", 1234);
        Address address1 = new Address("Fortin", 4567);

        //ahora le asignaremos las direcciones al cliente
        client.getAddresses().add(address);
        client.getAddresses().add(address1);

        //guardamos el cliente ccon las direcciones asiganadas
        clientRepository.save(client);

        System.out.println(client.toString());

        Optional<Client> optionalClient = clientRepository.findById(3L); //pasamos el cliente a eliminar
        //validamos que el cliente exista
        optionalClient.ifPresent(client1 -> {
            client1.getAddresses().remove(address1);  //eliminamos la primera direccion
            clientRepository.save(client1);  //actualizamos el cliente guardando.
            System.out.println(client1);
        });
    }

    @Transactional
    public void oneToManyFindById(){
        Optional<Client> optionalClient = clientRepository.findById(2L); //con un opctional de cliente se busca
        optionalClient.ifPresent(client -> {   //con ifpresent se evalua que exista el cliente
            //si existe se crea la direccion y se le asignan al cliente y se guarda
            Address address = new Address("Orizaba", 7894);
            Address address1 = new Address("Huatusco", 56321);

            //se debe setear la direccion al cliente con un arrays.aslist y dentro le pasamos las direcciones
            Set<Address> addressSet = new HashSet<>();
            addressSet.add(address);
            addressSet.add(address1);
            client.setAddresses(addressSet);

            //actualizamos el cliente
            clientRepository.save(client);

            System.out.println(client.toString());
        });
    }

    @Transactional
    public void oneToManyCreate(){
        Client client = new Client("Gerardo","Mora" );  //se crea un cliente

        //se crean dos direcciones
        Address address = new Address("Cordoba", 1234);
        Address address1 = new Address("Fortin", 4567);

        //ahora le asignaremos las direcciones al cliente
        client.getAddresses().add(address);
        client.getAddresses().add(address1);

        //guardamos el cliente ccon las direcciones asiganadas
        clientRepository.save(client);

        System.out.println(client.toString());
    }

    @Transactional
    public void manyToOneCreate(){
        //creamos un nuevo cliente y lo salvamos en la bd
        Client client = new Client("Rodrigo", "Mendez");
        clientRepository.save(client);

        //creamos una factura y le pasamos el cliente y la guardamos
        Invoice invoice = new Invoice("Compras de Tienda", 3000L);
        invoice.setClient(client);
        Invoice invoiceDb = invoiceRepository.save(invoice);
        System.out.println(invoiceDb);

    }

    @Transactional
    public void manyToOneFindByIdClient(){
        //se crea un optional de client y se le pasa el id a buscar
        Optional<Client> optionalClient = clientRepository.findById(2L);
        //evaluamos que exista el cliente
        if (optionalClient.isPresent()) {
            Client client = optionalClient.orElseThrow();

            //y le creamos una nueva factura y se le asiga al cliente existente
            Invoice invoice = new Invoice("Compras de Material Didactico", 4000L);
            invoice.setClient(client);
            Invoice invoiceDb = invoiceRepository.save(invoice);
            System.out.println(invoiceDb);
        }
    }
}
