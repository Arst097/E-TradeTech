/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun.Modelos;

import Uso_Comun.Modelos.Producto;
import Ventas.Modelos.Mensajero;
import Ventas.Modelos.Cliente;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Inventario;
import Uso_Comun.DAOs.DAO_Pedidos;
import Uso_Comun.DAOs.DAO_Producto;
import Uso_Comun.Modelos.Producto;
import Ventas.DAOS.DAO_Cliente;
import Ventas.DAOS.DAO_HistorialPedido;
import Ventas.Modelos.Cliente;
import Ventas.Modelos.HistorialPedidos;
import Ventas.Modelos.Mensajero;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class Pedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "PedidoID")
    private Integer pedidoID;
    @Column(name = "Estado")
    private String estado;
    @Basic(optional = false)
    @Column(name = "Fecha_Inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechainicio;
    @JoinColumn(name = "ClienteID", referencedColumnName = "ClienteID")
    @ManyToOne
    private Cliente clienteID;
    @JoinColumn(name = "DespachadorID", referencedColumnName = "DespachadorID")
    @ManyToOne
    private Despachador despachadorID;
    @JoinColumn(name = "Historial_PredidosID", referencedColumnName = "Historial_PredidosID")
    @ManyToOne(optional = false)
    private HistorialPedidos historialPredidosID;
    @JoinColumn(name = "MensajeroID", referencedColumnName = "MensajeroID")
    @ManyToOne
    private Mensajero mensajeroID;
    @OneToMany(mappedBy = "pedidoID")
    private Collection<Producto> productoCollection;

    public Pedidos() {
    }
    
    public Pedidos(Cliente clienteID) {
        this.estado = "Solicitado";
        this.fechainicio = new Date();
        this.clienteID = clienteID;
    }
    
    //el como se crea ese objeto historialpedidos esta incompleta, pero no se si esta bien tenerlo asi
    public void GenerarPedidoSolicitado(int clienteID, int historialPredidosID){
        DAO_HistorialPedido DAOh = new DAO_HistorialPedido();
        DAO_Cliente DAOc = new DAO_Cliente();
        DAO_Pedidos DAOp = new DAO_Pedidos();
        this.pedidoID = DAOp.obtenerIDValida();
        this.estado = "Solicitado";
        this.fechainicio = new Date();
        try {
            this.historialPredidosID = DAOh.findModel_HistorialPedidos(historialPredidosID);
            this.clienteID = DAOc.findCliente(clienteID);
        } catch (SQLException ex) {
            System.out.println("Error al encontrar Historial o ClienteID: "+ex);
        }
    }

    public Pedidos(Integer pedidoID) {
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

    public Cliente getClienteID() {
        return clienteID;
    }

    public void setClienteID(Cliente clienteID) {
        this.clienteID = clienteID;
    }

    public Despachador getDespachadorID() {
        return despachadorID;
    }

    public void setDespachadorID(Despachador despachadorID) {
        this.despachadorID = despachadorID;
    }

    public HistorialPedidos getHistorialPredidosID() {
        return historialPredidosID;
    }

    public void setHistorialPredidosID(HistorialPedidos historialPredidosID) {
        this.historialPredidosID = historialPredidosID;
    }

    public Mensajero getMensajeroID() {
        return mensajeroID;
    }

    public void setMensajeroID(Mensajero mensajeroID) {
        this.mensajeroID = mensajeroID;
    }

    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
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
        if (!(object instanceof Pedidos)) {
            return false;
        }
        Pedidos other = (Pedidos) object;
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
