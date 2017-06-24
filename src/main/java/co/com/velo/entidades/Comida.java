package co.com.velo.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author juan
 */
@Entity
@Table(name = "comidas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedido.findByDisponibilidad", query = "SELECT c FROM Comida c WHERE c.disponibilidad = :disponibilidad")})
public class Comida implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 32)
    @Column(name = "nombre", length = 32)
    private String nombre;

    @Column(name = "disponibilidad")
    private Boolean disponibilidad;

    @Column(name = "precio_base")
    private Integer precioBase;

    @Size(max = 32)
    @Column(name = "tiempo_preparacion", length = 32)
    private String tiempoPreparacion;

    @Size(max = 64)
    @Column(name = "caracteristicas", length = 64)
    private String caracteristicas;
    
    @OneToOne
    private Categoria categoria;

    public Comida() {
    }

    public Comida(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Boolean getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(Boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Integer getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Integer precioBase) {
        this.precioBase = precioBase;
    }

    public String getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(String tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }

    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + id + "\",\"nombre\" : \"" + nombre + "\"}";
    }
}