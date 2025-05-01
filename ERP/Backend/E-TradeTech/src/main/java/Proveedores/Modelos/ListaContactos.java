/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.Modelos;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author HP PORTATIL
 */
@Entity
@Table(name = "Lista_Contactos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ListaContactos.findAll", query = "SELECT l FROM ListaContactos l"),
    @NamedQuery(name = "ListaContactos.findByListaContactosID", query = "SELECT l FROM ListaContactos l WHERE l.listaContactosID = :listaContactosID"),
    @NamedQuery(name = "ListaContactos.findByNombre", query = "SELECT l FROM ListaContactos l WHERE l.nombre = :nombre"),
    @NamedQuery(name = "ListaContactos.findByDescripcion", query = "SELECT l FROM ListaContactos l WHERE l.descripcion = :descripcion")})
public class ListaContactos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Lista_ContactosID")
    private Integer listaContactosID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "Descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "listaContactosID")
    private Collection<UsuarioCompras> usuarioComprasCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "listaContactosID")
    private Collection<Proveedor> proveedorCollection;

    public ListaContactos() {
    }

    public ListaContactos(Integer listaContactosID) {
        this.listaContactosID = listaContactosID;
    }

    public ListaContactos(Integer listaContactosID, String nombre) {
        this.listaContactosID = listaContactosID;
        this.nombre = nombre;
    }

    public Integer getListaContactosID() {
        return listaContactosID;
    }

    public void setListaContactosID(Integer listaContactosID) {
        this.listaContactosID = listaContactosID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<UsuarioCompras> getUsuarioComprasCollection() {
        return usuarioComprasCollection;
    }

    public void setUsuarioComprasCollection(Collection<UsuarioCompras> usuarioComprasCollection) {
        this.usuarioComprasCollection = usuarioComprasCollection;
    }

    @XmlTransient
    public Collection<Proveedor> getProveedorCollection() {
        return proveedorCollection;
    }

    public void setProveedorCollection(Collection<Proveedor> proveedorCollection) {
        this.proveedorCollection = proveedorCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (listaContactosID != null ? listaContactosID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ListaContactos)) {
            return false;
        }
        ListaContactos other = (ListaContactos) object;
        if ((this.listaContactosID == null && other.listaContactosID != null) || (this.listaContactosID != null && !this.listaContactosID.equals(other.listaContactosID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Proveedores.Modelos.ListaContactos[ listaContactosID=" + listaContactosID + " ]";
    }
    
}
