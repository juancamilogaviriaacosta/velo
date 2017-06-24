package co.com.velo.ws;

import co.com.velo.entidades.Categoria;
import co.com.velo.entidades.Comida;
import co.com.velo.entidades.ComidaCantidad;
import co.com.velo.entidades.Pedido;
import co.com.velo.facades.CategoriaFacade;
import co.com.velo.facades.ComidaFacade;
import co.com.velo.facades.PedidoFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author juan
 */
@Stateless
@Path("/VeloRest")
public class VeloRest {
    
    @EJB
    private ComidaFacade comidaFacade;

    @EJB
    private CategoriaFacade categoriaFacade;

    @EJB
    private PedidoFacade pedidoFacade;

    @GET
    @Path("/getMenu")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu() {
        List<Comida> comidas = comidaFacade.ejecutarNamedQuery("Pedido.findByDisponibilidad", "disponibilidad", true);
        return Response.ok(comidas).build();
    }

    @GET
    @Path("/guardarComida")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarComida(
            @WebParam(name = "nombre") String nombre,
            @WebParam(name = "disponibilidad") Boolean disponibilidad,
            @WebParam(name = "precioBase") Integer precioBase,
            @WebParam(name = "tiempoPreparacion") String tiempoPreparacion,
            @WebParam(name = "caracteristicas") String caracteristicas,
            @WebParam(name = "idCategoria") Integer idCategoria) {
        Comida c = new Comida();
        c.setNombre(nombre);
        c.setDisponibilidad(disponibilidad);
        c.setPrecioBase(precioBase);
        c.setTiempoPreparacion(tiempoPreparacion);
        c.setCaracteristicas(caracteristicas);
        c.setCategoria(new Categoria(idCategoria));
        comidaFacade.create(c);
        return Response.ok().build();
    }

    @GET
    @Path("/guardarCategoria")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarCategoria(@WebParam(name = "nombre") String nombre) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        categoriaFacade.create(c);
        return Response.ok().build();
    }

    @GET
    @Path("/getCategorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias() {
        List<Categoria> categorias = categoriaFacade.findAll();
        return Response.ok(categorias).build();
    }

    @GET
    @Path("/hacerPedido")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hacerPedido(
            @WebParam(name = "nombreCliente") String nombreCliente,
            @WebParam(name = "comidas") List<ComidaCantidad> comidasCantidad) {
        Pedido p = new Pedido();
        p.setEntregado(false);
        p.setNombreCliente(nombreCliente);
        p.setComidaCantidad(new ArrayList<ComidaCantidad>());
        for (ComidaCantidad c : comidasCantidad) {
            p.getComidaCantidad().add(new ComidaCantidad(c.getCantidad(), c.getComida()));
        }
        pedidoFacade.create(p);
        return Response.ok("Pedido realizado con exito").build();
    }
    
    @GET
    @Path("/getPedidos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPedidos() {
        List<Pedido> pedidos = pedidoFacade.findAll();
        return Response.ok(pedidos).build();
    }
    
    @GET
    @Path("/getPedidosPendientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPedidosPendientes() {
        List<Pedido> pedidos = pedidoFacade.ejecutarNamedQuery("Pedido.findByEntregado", "entregado", false);
        return Response.ok(pedidos).build();
    }
    
    @GET
    @Path("/getPedidosEntregados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPedidosEntregados() {
        List<Pedido> pedidos = pedidoFacade.ejecutarNamedQuery("Pedido.findByEntregado", "entregado", true);
        return Response.ok(pedidos).build();
    }
    
    @GET
    @Path("/entregarPedido")
    @Produces(MediaType.APPLICATION_JSON)
    public Response entregarPedido(@WebParam(name = "idPedido") Integer idPedido) {
        Pedido p = pedidoFacade.find(idPedido);
        p.setEntregado(true);
        pedidoFacade.edit(p);
        return Response.ok("Pedido entregado con exito").build();
    }
}
