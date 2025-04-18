/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Uso_Comun.Model_Pedidos;
import Inventario.Model_Inventario;
import Inventario.Model_Inventario;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HP PORTATIL
 */
@Entity
@Table(name = "Producto")
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByProductoID", query = "SELECT p FROM Producto p WHERE p.productoID = :productoID"),
    @NamedQuery(name = "Producto.findByModelo", query = "SELECT p FROM Producto p WHERE p.modelo = :modelo"),
    @NamedQuery(name = "Producto.findByFechaEntrada", query = "SELECT p FROM Producto p WHERE p.fechaEntrada = :fechaEntrada")})
public class Model_Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ProductoID")
    private Integer productoID;
    @Basic(optional = false)
    @Column(name = "Modelo")
    private String modelo;
    @Basic(optional = false)
    @Column(name = "Fecha_Entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEntrada;
    @JoinColumn(name = "InventarioID", referencedColumnName = "InventarioID")
    @ManyToOne(optional = false)
    private Model_Inventario inventarioID;
    @JoinColumn(name = "PedidoID", referencedColumnName = "PedidoID")
    @ManyToOne
    private Model_Pedidos pedidoID;

    public Model_Producto() {
    }

    public Model_Producto(Integer productoID) {
        this.productoID = productoID;
    }

    public Model_Producto(Integer productoID, String modelo, Date fechaEntrada) {
        this.productoID = productoID;
        this.modelo = modelo;
        this.fechaEntrada = fechaEntrada;
    }

    public Integer getProductoID() {
        return productoID;
    }

    public void setProductoID(Integer productoID) {
        this.productoID = productoID;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public Model_Inventario getInventarioID() {
        return inventarioID;
    }

    public void setInventarioID(Model_Inventario inventarioID) {
        this.inventarioID = inventarioID;
    }

    public Model_Pedidos getPedidoID() {
        return pedidoID;
    }

    public void setPedidoID(Model_Pedidos pedidoID) {
        this.pedidoID = pedidoID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoID != null ? productoID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model_Producto)) {
            return false;
        }
        Model_Producto other = (Model_Producto) object;
        if ((this.productoID == null && other.productoID != null) || (this.productoID != null && !this.productoID.equals(other.productoID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Producto[ productoID=" + productoID + " ]";
    }
    
}
