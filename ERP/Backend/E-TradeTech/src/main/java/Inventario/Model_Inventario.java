/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.Model_Producto;
import Inventario.Model_Despachador;
import Inventario.Model_Almacen;
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
public class Model_Inventario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "InventarioID")
    private Integer inventarioID;
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarioID")
    private Collection<Model_Despachador> despachadorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarioID")
    private Collection<Model_Producto> productoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarioEmisorID")
    private Collection<Model_TransaccionInv> transaccionInvCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventarioReceptorD")
    private Collection<Model_TransaccionInv> transaccionInvCollection1;
    @JoinColumn(name = "AlmacenID", referencedColumnName = "AlmacenID")
    @ManyToOne
    private Model_Almacen almacenID;

    public Model_Inventario() {
    }

    public Model_Inventario(Integer inventarioID) {
        this.inventarioID = inventarioID;
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

    public Collection<Model_Despachador> getDespachadorCollection() {
        return despachadorCollection;
    }

    public void setDespachadorCollection(Collection<Model_Despachador> despachadorCollection) {
        this.despachadorCollection = despachadorCollection;
    }

    public Collection<Model_Producto> getProductoCollection() {
        return productoCollection;
    }

    public void setProductoCollection(Collection<Model_Producto> productoCollection) {
        this.productoCollection = productoCollection;
    }

    public Collection<Model_TransaccionInv> getTransaccionInvCollection() {
        return transaccionInvCollection;
    }

    public void setTransaccionInvCollection(Collection<Model_TransaccionInv> transaccionInvCollection) {
        this.transaccionInvCollection = transaccionInvCollection;
    }

    public Collection<Model_TransaccionInv> getTransaccionInvCollection1() {
        return transaccionInvCollection1;
    }

    public void setTransaccionInvCollection1(Collection<Model_TransaccionInv> transaccionInvCollection1) {
        this.transaccionInvCollection1 = transaccionInvCollection1;
    }

    public Model_Almacen getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(Model_Almacen almacenID) {
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
        if (!(object instanceof Model_Inventario)) {
            return false;
        }
        Model_Inventario other = (Model_Inventario) object;
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
