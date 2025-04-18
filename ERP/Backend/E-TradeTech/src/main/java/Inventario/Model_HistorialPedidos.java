/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.Model_Pedidos;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
@Table(name = "Historial_Pedidos")
@NamedQueries({
    @NamedQuery(name = "HistorialPedidos.findAll", query = "SELECT h FROM HistorialPedidos h"),
    @NamedQuery(name = "HistorialPedidos.findByHistorialPredidosID", query = "SELECT h FROM HistorialPedidos h WHERE h.historialPredidosID = :historialPredidosID")})
public class Model_HistorialPedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Historial_PredidosID")
    private Integer historialPredidosID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historialPredidosID")
    private Collection<Model_Pedidos> pedidosCollection;

    public Model_HistorialPedidos() {
    }

    public Model_HistorialPedidos(Integer historialPredidosID) {
        this.historialPredidosID = historialPredidosID;
    }

    public Integer getHistorialPredidosID() {
        return historialPredidosID;
    }

    public void setHistorialPredidosID(Integer historialPredidosID) {
        this.historialPredidosID = historialPredidosID;
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
        hash += (historialPredidosID != null ? historialPredidosID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model_HistorialPedidos)) {
            return false;
        }
        Model_HistorialPedidos other = (Model_HistorialPedidos) object;
        if ((this.historialPredidosID == null && other.historialPredidosID != null) || (this.historialPredidosID != null && !this.historialPredidosID.equals(other.historialPredidosID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.HistorialPedidos[ historialPredidosID=" + historialPredidosID + " ]";
    }
    
}
