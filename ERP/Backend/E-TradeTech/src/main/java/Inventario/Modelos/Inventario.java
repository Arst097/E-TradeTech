/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario.Modelos;

import Uso_Comun.Modelos.Producto;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Almacen;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
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
@Table(name = "Inventario")
@NamedQueries({
    @NamedQuery(name = "Inventario.findAll", query = "SELECT i FROM Inventario i"),
    @NamedQuery(name = "Inventario.findByInventarioID", query = "SELECT i FROM Inventario i WHERE i.inventarioID = :inventarioID"),
    @NamedQuery(name = "Inventario.findByTipo", query = "SELECT i FROM Inventario i WHERE i.tipo = :tipo")})
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "InventarioID")
    private Integer inventarioID;
    
    @Column(name = "tipo")
    private String tipo;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarioID")
    private Collection<Despachador> despachadorCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarioID")
    private Collection<Producto> productoCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarioEmisorID")
    private Collection<TransaccionInv> transaccionInvCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarioReceptorD")
    private Collection<TransaccionInv> transaccionInvCollection1;
    
    @JoinColumn(name = "AlmacenID", referencedColumnName = "AlmacenID")
    @ManyToOne
    private Almacen almacenID;

    public Inventario() {
    }

    public Inventario(Integer inventarioID) {
        this.inventarioID = inventarioID;
    }
    
    public Inventario(Integer inventarioID, String tipo, Almacen almacenID) {
        this.inventarioID = inventarioID;
        this.tipo = tipo;
        this.almacenID = almacenID;
    }
    
    

    public Integer getInventarioID() {
        return inventarioID;
    }

    public void setInventarioID(Integer inventarioID) {
        this.inventarioID = inventarioID;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Collection<Despachador> getDespachadorCollection() {
        return despachadorCollection;
    }

    public void setDespachadorCollection(Collection<Despachador> despachadorCollection) {
        this.despachadorCollection = despachadorCollection;
    }

    public Collection<Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    public Collection<TransaccionInv> getTransaccionInvCollection() {
        return transaccionInvCollection;
    }

    public void setTransaccionInvCollection(Collection<TransaccionInv> transaccionInvCollection) {
        this.transaccionInvCollection = transaccionInvCollection;
    }

    public Collection<TransaccionInv> getTransaccionInvCollection1() {
        return transaccionInvCollection1;
    }

    public void setTransaccionInvCollection1(Collection<TransaccionInv> transaccionInvCollection1) {
        this.transaccionInvCollection1 = transaccionInvCollection1;
    }

    public Almacen getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(Almacen almacenID) {
        this.almacenID = almacenID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (inventarioID != null ? inventarioID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventario)) {
            return false;
        }
        Inventario other = (Inventario) object;
        if ((this.inventarioID == null && other.inventarioID != null) || (this.inventarioID != null && !this.inventarioID.equals(other.inventarioID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Inventario[ inventarioID=" + inventarioID + " ]";
    }
    
}
