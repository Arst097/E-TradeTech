/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

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
@Table(name = "Almacen")
@NamedQueries({
    @NamedQuery(name = "Almacen.findAll", query = "SELECT a FROM Almacen a"),
    @NamedQuery(name = "Almacen.findByAlmacenID", query = "SELECT a FROM Almacen a WHERE a.almacenID = :almacenID"),
    @NamedQuery(name = "Almacen.findByNombre", query = "SELECT a FROM Almacen a WHERE a.nombre = :nombre"),
    @NamedQuery(name = "Almacen.findByDireccion", query = "SELECT a FROM Almacen a WHERE a.direccion = :direccion"),
    @NamedQuery(name = "Almacen.findByCapacidad", query = "SELECT a FROM Almacen a WHERE a.capacidad = :capacidad"),
    @NamedQuery(name = "Almacen.findByTelefono", query = "SELECT a FROM Almacen a WHERE a.telefono = :telefono")})
public class Almacen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "AlmacenID")
    private Integer almacenID;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Direccion")
    private String direccion;
    @Basic(optional = false)
    @Column(name = "Capacidad")
    private int capacidad;
    @Basic(optional = false)
    @Column(name = "Telefono")
    private String telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "almacenID")
    private Collection<Despachador> despachadorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "almacenID")
    private Collection<Gestores> gestoresCollection;
    @OneToMany(mappedBy = "almacenID")
    private Collection<Inventario> inventarioCollection;

    public Almacen() {
    }

    public Almacen(Integer almacenID) {
        this.almacenID = almacenID;
    }

    public Almacen(Integer almacenID, String nombre, String direccion, int capacidad, String telefono) {
        this.almacenID = almacenID;
        this.nombre = nombre;
        this.direccion = direccion;
        this.capacidad = capacidad;
        this.telefono = telefono;
    }

    public Integer getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(Integer almacenID) {
        this.almacenID = almacenID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Collection<Despachador> getDespachadorCollection() {
        return despachadorCollection;
    }

    public void setDespachadorCollection(Collection<Despachador> despachadorCollection) {
        this.despachadorCollection = despachadorCollection;
    }

    public Collection<Gestores> getGestoresCollection() {
        return gestoresCollection;
    }

    public void setGestoresCollection(Collection<Gestores> gestoresCollection) {
        this.gestoresCollection = gestoresCollection;
    }

    public Collection<Inventario> getInventarioCollection() {
        return inventarioCollection;
    }

    public void setInventarioCollection(Collection<Inventario> inventarioCollection) {
        this.inventarioCollection = inventarioCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (almacenID != null ? almacenID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Almacen)) {
            return false;
        }
        Almacen other = (Almacen) object;
        if ((this.almacenID == null && other.almacenID != null) || (this.almacenID != null && !this.almacenID.equals(other.almacenID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Almacen[ almacenID=" + almacenID + " ]";
    }
    
}
