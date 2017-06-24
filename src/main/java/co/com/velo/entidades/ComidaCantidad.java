package co.com.velo.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "comida_cantidad")
@XmlRootElement
public class ComidaCantidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @OneToOne
    private Comida comida;

    public ComidaCantidad() {
    }

    public ComidaCantidad(Integer id) {
        this.id = id;
    }

    public ComidaCantidad(Integer cantidad, Comida comida) {
        this.cantidad = cantidad;
        this.comida = comida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Comida getComida() {
        return comida;
    }

    public void setComida(Comida comida) {
        this.comida = comida;
    }
}