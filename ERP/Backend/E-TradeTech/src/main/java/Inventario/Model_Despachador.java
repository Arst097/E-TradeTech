/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.Model_Usuario;
import Uso_Comun.Model_Pedidos;
import Inventario.Model_Almacen;
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
@Table(name = "Despachador")
@NamedQueries({
    @NamedQuery(name = "Despachador.findAll", query = "SELECT d FROM Despachador d"),
    @NamedQuery(name = "Despachador.findByDespachadorID", query = "SELECT d FROM Despachador d WHERE d.despachadorID = :despachadorID"),
    @NamedQuery(name = "Despachador.findByPuesto", query = "SELECT d FROM Despachador d WHERE d.puesto = :puesto"),
    @NamedQuery(name = "Despachador.findByTelefono", query = "SELECT d FROM Despachador d WHERE d.telefono = :telefono")})
public class Model_Despachador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "DespachadorID")
    private Integer despachadorID;
    @Basic(optional = false)
    @Column(name = "Puesto")
    private String puesto;
    @Basic(optional = false)
    @Column(name = "Telefono")
    private String telefono;
    @OneToMany(mappedBy = "despachadorID")
    private Collection<Model_Pedidos> pedidosCollection;
    @JoinColumn(name = "AlmacenID", referencedColumnName = "AlmacenID")
    @ManyToOne(optional = false)
    private Model_Almacen almacenID;
    @JoinColumn(name = "InventarioID", referencedColumnName = "InventarioID")
    @ManyToOne(optional = false)
    private Model_Inventario inventarioID;
    @JoinColumn(name = "Usuario_Usuario_id", referencedColumnName = "Usuario_id")
    @ManyToOne(optional = false)
    private Model_Usuario usuarioUsuarioid;

    public Model_Despachador() {
    }

    public Model_Despachador(Integer despachadorID) {
        this.despachadorID = despachadorID;
    }

    public Model_Despachador(Integer despachadorID, String puesto, String telefono) {
        this.despachadorID = despachadorID;
        this.puesto = puesto;
        this.telefono = telefono;
    }

    public Integer getDespachadorID() {
        return despachadorID;
    }

    public void setDespachadorID(Integer despachadorID) {
        this.despachadorID = despachadorID;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
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

    public Model_Almacen getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(Model_Almacen almacenID) {
        this.almacenID = almacenID;
    }

    public Model_Inventario getInventarioID() {
        return inventarioID;
    }

    public void setInventarioID(Model_Inventario inventarioID) {
        this.inventarioID = inventarioID;
    }

    public Model_Usuario getUsuarioUsuarioid() {
        return usuarioUsuarioid;
    }

    public void setUsuarioUsuarioid(Model_Usuario usuarioUsuarioid) {
        this.usuarioUsuarioid = usuarioUsuarioid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (despachadorID != null ? despachadorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model_Despachador)) {
            return false;
        }
        Model_Despachador other = (Model_Despachador) object;
        if ((this.despachadorID == null && other.despachadorID != null) || (this.despachadorID != null && !this.despachadorID.equals(other.despachadorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Despachador[ despachadorID=" + despachadorID + " ]";
    }
    
}
