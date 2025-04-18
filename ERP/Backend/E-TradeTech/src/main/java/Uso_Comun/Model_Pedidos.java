/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Inventario.Model_HistorialPedidos;
import Inventario.Model_Despachador;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Pedidos")
@NamedQueries({
    @NamedQuery(name = "Pedidos.findAll", query = "SELECT p FROM Pedidos p"),
    @NamedQuery(name = "Pedidos.findByPedidoID", query = "SELECT p FROM Pedidos p WHERE p.pedidoID = :pedidoID"),
    @NamedQuery(name = "Pedidos.findByEstado", query = "SELECT p FROM Pedidos p WHERE p.estado = :estado")})
public class Model_Pedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PedidoID")
    private Integer pedidoID;
    @Column(name = "Estado")
    private String estado;
    @JoinColumn(name = "ClienteID", referencedColumnName = "ClienteID")
    @ManyToOne
    private Model_Cliente clienteID;
    @JoinColumn(name = "DespachadorID", referencedColumnName = "DespachadorID")
    @ManyToOne
    private Model_Despachador despachadorID;
    @JoinColumn(name = "Historial_PredidosID", referencedColumnName = "Historial_PredidosID")
    @ManyToOne(optional = false)
    private Model_HistorialPedidos historialPredidosID;
    @JoinColumn(name = "MensajeroID", referencedColumnName = "MensajeroID")
    @ManyToOne
    private Model_Mensajero mensajeroID;
    @OneToMany(mappedBy = "pedidoID")
    private Collection<Model_Producto> productoCollection;

    public Model_Pedidos() {
    }

    public Model_Pedidos(Integer pedidoID) {
        this.pedidoID = pedidoID;
    }

    public Integer getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(Integer pedidoID) {
        this.pedidoID = pedidoID;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Model_Cliente getClienteID() {
        return clienteID;
    }

    public void setClienteID(Model_Cliente clienteID) {
        this.clienteID = clienteID;
    }

    public Model_Despachador getDespachadorID() {
        return despachadorID;
    }

    public void setDespachadorID(Model_Despachador despachadorID) {
        this.despachadorID = despachadorID;
    }

    public Model_HistorialPedidos getHistorialPredidosID() {
        return historialPredidosID;
    }

    public void setHistorialPredidosID(Model_HistorialPedidos historialPredidosID) {
        this.historialPredidosID = historialPredidosID;
    }

    public Model_Mensajero getMensajeroID() {
        return mensajeroID;
    }

    public void setMensajeroID(Model_Mensajero mensajeroID) {
        this.mensajeroID = mensajeroID;
    }

    public Collection<Model_Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Model_Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pedidoID != null ? pedidoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model_Pedidos)) {
            return false;
        }
        Model_Pedidos other = (Model_Pedidos) object;
        if ((this.pedidoID == null && other.pedidoID != null) || (this.pedidoID != null && !this.pedidoID.equals(other.pedidoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Pedidos[ pedidoID=" + pedidoID + " ]";
    }
    
}
