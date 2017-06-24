package co.com.velo.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author juan
 */
@Entity
@Table(name = "pedido")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findByEntregado", query = "SELECT p FROM Pedido p WHERE p.entregado = :entregado")})
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 32)
    @Column(name = "nombre_cliente", length = 32)
    private String nombreCliente;

    @Column(name = "entregado")
    private Boolean entregado;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<ComidaCantidad> comidaCantidad;

    public Pedido() {
    }

    public Pedido(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Boolean getEntregado() {
        return entregado;
    }

    public void setEntregado(Boolean entregado) {
        this.entregado = entregado;
    }

    public List<ComidaCantidad> getComidaCantidad() {
        return comidaCantidad;
    }

    public void setComidaCantidad(List<ComidaCantidad> comidaCantidad) {
        this.comidaCantidad = comidaCantidad;
    }
}
