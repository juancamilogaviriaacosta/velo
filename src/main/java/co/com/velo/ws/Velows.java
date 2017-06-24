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
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author juan
 */
@WebService(serviceName = "Velows")
public class Velows {
@EJB
    private ComidaFacade comidaFacade;

    @EJB
    private CategoriaFacade categoriaFacade;

    @EJB
    private PedidoFacade pedidoFacade;

    @WebMethod(operationName = "getMenu")
    public List<Comida> getMenu() {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        List<Comida> comidas = comidaFacade.ejecutarNamedQuery("Pedido.findByDisponibilidad", "disponibilidad", true);
        return comidas;
    }

    @WebMethod(operationName = "guardarComida")
    public void guardarComida(
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
    }

    @WebMethod(operationName = "guardarCategoria")
    public void guardarCategoria(@WebParam(name = "nombre") String nombre) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        categoriaFacade.create(c);
    }

    @WebMethod(operationName = "getCategorias")
    public List<Categoria> getCategorias() {
        List<Categoria> categorias = categoriaFacade.findAll();
        return categorias;
    }

    @WebMethod(operationName = "hacerPedido")
    public String hacerPedido(
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
        return "Pedido realizado con exito";
    }
    
    @WebMethod(operationName = "getPedidos")
    public List<Pedido> getPedidos() {
        List<Pedido> pedidos = pedidoFacade.findAll();
        return pedidos;
    }
    
    @WebMethod(operationName = "getPedidosPendientes")
    public List<Pedido> getPedidosPendientes() {
        List<Pedido> pedidos = pedidoFacade.ejecutarNamedQuery("Pedido.findByEntregado", "entregado", false);
        return pedidos;
    }
    
    @WebMethod(operationName = "getPedidosEntregados")
    public List<Pedido> getPedidosEntregados() {
        List<Pedido> pedidos = pedidoFacade.ejecutarNamedQuery("Pedido.findByEntregado", "entregado", true);
        return pedidos;
    }
    
    @WebMethod(operationName = "entregarPedido")
    public String entregarPedido(@WebParam(name = "idPedido") Integer idPedido) {
        Pedido p = pedidoFacade.find(idPedido);
        p.setEntregado(true);
        pedidoFacade.edit(p);
        return "Pedido entregado con exito";
    }
}