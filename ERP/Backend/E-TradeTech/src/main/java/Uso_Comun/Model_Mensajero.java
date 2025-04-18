/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author HP PORTATIL
 */
@Entity
@Table(name = "Mensajero")
@NamedQueries({
    @NamedQuery(name = "Mensajero.findAll", query = "SELECT m FROM Mensajero m"),
    @NamedQuery(name = "Mensajero.findByMensajeroID", query = "SELECT m FROM Mensajero m WHERE m.mensajeroID = :mensajeroID"),
    @NamedQuery(name = "Mensajero.findByNombre", query = "SELECT m FROM Mensajero m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Mensajero.findByCompa\u00f1ia", query = "SELECT m FROM Mensajero m WHERE m.compa\u00f1ia = :compa\u00f1ia"),
    @NamedQuery(name = "Mensajero.findByTelefono", query = "SELECT m FROM Mensajero m WHERE m.telefono = :telefono")})
public class Model_Mensajero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MensajeroID")
    private Integer mensajeroID;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Compa\u00f1ia")
    private String compañia;
    @Basic(optional = false)
    @Column(name = "Telefono")
    private String telefono;
    @OneToMany(mappedBy = "mensajeroID")
    private Collection<Model_Pedidos> pedidosCollection;

    public Model_Mensajero() {
    }

    public Model_Mensajero(Integer mensajeroID) {
        this.mensajeroID = mensajeroID;
    }

    public Model_Mensajero(Integer mensajeroID, String nombre, String compañia, String telefono) {
        this.mensajeroID = mensajeroID;
        this.nombre = nombre;
        this.compañia = compañia;
        this.telefono = telefono;
    }

    public Integer getMensajeroID() {
        return mensajeroID;
    }

    public void setMensajeroID(Integer mensajeroID) {
        this.mensajeroID = mensajeroID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCompañia() {
        return compañia;
    }

    public void setCompañia(String compañia) {
        this.compañia = compañia;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Collection<Model_Pedidos> getPedidosCollection() {
        return pedidosCollection;
    }

    public void setPedidosCollection(Collection<Model_Pedidos> pedidosCollection) {
        this.pedidosCollection = pedidosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mensajeroID != null ? mensajeroID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model_Mensajero)) {
            return false;
        }
        Model_Mensajero other = (Model_Mensajero) object;
        if ((this.mensajeroID == null && other.mensajeroID != null) || (this.mensajeroID != null && !this.mensajeroID.equals(other.mensajeroID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Mensajero[ mensajeroID=" + mensajeroID + " ]";
    }
    
}
