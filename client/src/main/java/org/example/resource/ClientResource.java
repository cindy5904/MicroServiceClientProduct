package org.example.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.Client;
import org.example.service.ClientService;

import java.util.List;
import java.util.Optional;

@Path("/clients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientResource {
    @Inject
    ClientService clientService;

    @GET
    public List<Client> getAllClients() {
        return clientService.getAllClient();
    }

    @POST
    @Path("/create")
    public Response createClient(Client client) {
        Client newClient = clientService.createClient(client.getName(), client.getEmail(), client.getPhone());
        return Response.ok(newClient).status(201).build();
    }

    @GET
    @Path("{id}")
    public Response searchClientById(@PathParam("id") Long id) {
        Optional<Client> client = clientService.getClientById(id);
        return client.map(cli -> Response.ok(cli).build()).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    public Response deleteClient(@PathParam("id") Long id){
        clientService.deleteById(id);
        return Response.noContent().build();
    }
    @PUT
    @Path("{id}")
    public Response updateClient(@PathParam("id") Long id, Client updatedClient) {
        try {
            Client client = clientService.updateClient(id, updatedClient.getName(), updatedClient.getEmail(), updatedClient.getPhone());
            return Response.ok(client).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }




}
