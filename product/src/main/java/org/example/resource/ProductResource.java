package org.example.resource;

import jakarta.inject.Inject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.entity.Product;
import org.example.repository.ProductRepository;
import org.example.service.ProductService;

import java.util.List;
import java.util.Optional;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
    @Inject
    ProductService produictService;

    @GET
    public List<Product> getAllProducts() {
        return produictService.getAllProduct();
    }

    @POST
    @Path("/createProduct")
    public Response createProduct(Product product) {
        Product newProduct = produictService.createProduct((product.getName(), product.getDescription(), product.getPrice());
        return Response.ok(newProduct).status(201).build();
    }

    @GET
    @Path("{id}")
    public Response searchProductById(@PathParam("id") Long id) {
        Optional<Product> product = produictService.getProducttById(id);
        return product.map(pro -> Response.ok(pro).build()).orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    public Response deleteProduct(@PathParam("id") Long id){
        produictService.deleteById(id);
        return Response.noContent().build();
    }
    @PUT
    @Path("{id}")
    public Response updateProduct(@PathParam("id") Long id, Product updatedProduct) {
        try {
            Product product = produictService.updateProduct(id, updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice());
            return Response.ok(product).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
