/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

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
public class Despachador implements Serializable {

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
    private Collection<Pedidos> pedidosCollection;
    @JoinColumn(name = "AlmacenID", referencedColumnName = "AlmacenID")
    @ManyToOne(optional = false)
    private Almacen almacenID;
    @JoinColumn(name = "InventarioID", referencedColumnName = "InventarioID")
    @ManyToOne(optional = false)
    private Inventario inventarioID;
    @JoinColumn(name = "Usuario_Usuario_id", referencedColumnName = "Usuario_id")
    @ManyToOne(optional = false)
    private Usuario usuarioUsuarioid;

    public Despachador() {
    }

    public Despachador(Integer despachadorID) {
        this.despachadorID = despachadorID;
    }

    public Despachador(Integer despachadorID, String puesto, String telefono) {
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

    public Collection<Pedidos> getPedidosCollection() {
        return pedidosCollection;
    }

    public void setPedidosCollection(Collection<Pedidos> pedidosCollection) {
        this.pedidosCollection = pedidosCollection;
    }

    public Almacen getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(Almacen almacenID) {
        this.almacenID = almacenID;
    }

    public Inventario getInventarioID() {
        return inventarioID;
    }

    public void setInventarioID(Inventario inventarioID) {
        this.inventarioID = inventarioID;
    }

    public Usuario getUsuarioUsuarioid() {
        return usuarioUsuarioid;
    }

    public void setUsuarioUsuarioid(Usuario usuarioUsuarioid) {
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
        if (!(object instanceof Despachador)) {
            return false;
        }
        Despachador other = (Despachador) object;
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
