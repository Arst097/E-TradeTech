/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.Modelos;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author HP PORTATIL
 */
@Entity
@Table(name = "Ofertas")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ofertas.findAll", query = "SELECT o FROM Ofertas o"),
    @NamedQuery(name = "Ofertas.findByOfertasID", query = "SELECT o FROM Ofertas o WHERE o.ofertasID = :ofertasID"),
    @NamedQuery(name = "Ofertas.findByProductoOfertado", query = "SELECT o FROM Ofertas o WHERE o.productoOfertado = :productoOfertado"),
    @NamedQuery(name = "Ofertas.findByPrecioUnidad", query = "SELECT o FROM Ofertas o WHERE o.precioUnidad = :precioUnidad")})
public class Ofertas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "OfertasID")
    private Integer ofertasID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Producto_Ofertado")
    private String productoOfertado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Precio_Unidad")
    private String precioUnidad;
    @JoinColumn(name = "ProveedorID", referencedColumnName = "ProveedorID")
    @ManyToOne(optional = false)
    private Proveedor proveedorID;

    public Ofertas() {
    }

    public Ofertas(Integer ofertasID) {
        this.ofertasID = ofertasID;
    }

    public Ofertas(Integer ofertasID, String productoOfertado, String precioUnidad) {
        this.ofertasID = ofertasID;
        this.productoOfertado = productoOfertado;
        this.precioUnidad = precioUnidad;
    }

    public Integer getOfertasID() {
        return ofertasID;
    }

    public void setOfertasID(Integer ofertasID) {
        this.ofertasID = ofertasID;
    }

    public String getProductoOfertado() {
        return productoOfertado;
    }

    public void setProductoOfertado(String productoOfertado) {
        this.productoOfertado = productoOfertado;
    }

    public String getPrecioUnidad() {
        return precioUnidad;
    }

    public void setPrecioUnidad(String precioUnidad) {
        this.precioUnidad = precioUnidad;
    }

    public Proveedor getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(Proveedor proveedorID) {
        this.proveedorID = proveedorID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ofertasID != null ? ofertasID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ofertas)) {
            return false;
        }
        Ofertas other = (Ofertas) object;
        if ((this.ofertasID == null && other.ofertasID != null) || (this.ofertasID != null && !this.ofertasID.equals(other.ofertasID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Proveedores.Modelos.Ofertas[ ofertasID=" + ofertasID + " ]";
    }
    
}
