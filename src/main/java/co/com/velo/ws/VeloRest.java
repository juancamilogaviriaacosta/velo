package co.com.velo.ws;

import co.com.velo.entidades.Categoria;
import co.com.velo.entidades.Comida;
import co.com.velo.entidades.ComidaCantidad;
import co.com.velo.entidades.Pedido;
import co.com.velo.facades.CategoriaFacade;
import co.com.velo.facades.ComidaFacade;
import co.com.velo.facades.PedidoFacade;
import co.com.velo.utils.Utilidades;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
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
        return Response.ok(Utilidades.toJsonArrEntity(comidas)).build();
    }

    @GET
    @Path("/guardarComida")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarComida(
            @QueryParam("nombre") String nombre,
            @QueryParam("disponibilidad") String disponibilidad,
            @QueryParam("precioBase") String precioBase,
            @QueryParam("tiempoPreparacion") String tiempoPreparacion,
            @QueryParam("caracteristicas") String caracteristicas,
            @QueryParam("idCategoria") String idCategoria) {
        Comida c = new Comida();
        c.setNombre(nombre);
        c.setDisponibilidad(Boolean.valueOf(disponibilidad));
        c.setPrecioBase(Integer.valueOf(precioBase));
        c.setTiempoPreparacion(tiempoPreparacion);
        c.setCaracteristicas(caracteristicas);
        c.setCategoria(new Categoria(Integer.valueOf(idCategoria)));
        comidaFacade.create(c);
        return Response.ok().build();
    }

    @GET
    @Path("/guardarCategoria")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarCategoria(@QueryParam("nombre") String nombre) {
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
        return Response.ok(Utilidades.toJsonArrEntity(categorias)).build();
    }

    @GET
    @Path("/hacerPedido")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hacerPedido(
            @QueryParam("nombreCliente") String nombreCliente,
            @QueryParam("cantidad") String cantidad,
            @QueryParam("idComida") String idComida) {
        Pedido p;
        List<Pedido> lista = pedidoFacade.ejecutarNamedQuery("Pedido.findByNombreCliente", "entregado", false, "nombreCliente", nombreCliente);
        if(lista!=null && !lista.isEmpty() && lista.get(0)!=null) {
            p = lista.get(0);
        } else {
            p = new Pedido();
        }
        p.setEntregado(false);
        p.setNombreCliente(nombreCliente);
        p.setComidaCantidad(new ArrayList<ComidaCantidad>());
        for (ComidaCantidad c : p.getComidaCantidad()) {
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
        return Response.ok(Utilidades.toJsonArrEntity(pedidos)).build();
    }
    
    @GET
    @Path("/getPedidosPendientes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPedidosPendientes() {
        List<Pedido> pedidos = pedidoFacade.ejecutarNamedQuery("Pedido.findByEntregado", "entregado", false);
        return Response.ok(Utilidades.toJsonArrEntity(pedidos)).build();
    }
    
    @GET
    @Path("/getPedidosEntregados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPedidosEntregados() {
        List<Pedido> pedidos = pedidoFacade.ejecutarNamedQuery("Pedido.findByEntregado", "entregado", true);
        return Response.ok(Utilidades.toJsonArrEntity(pedidos)).build();
    }
    
    @GET
    @Path("/entregarPedido")
    @Produces(MediaType.APPLICATION_JSON)
    public Response entregarPedido(@QueryParam("idPedido") Integer idPedido) {
        Pedido p = pedidoFacade.find(idPedido);
        p.setEntregado(true);
        pedidoFacade.edit(p);
        return Response.ok("Pedido entregado con exito").build();
    }
}