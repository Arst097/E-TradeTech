/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.Modelos;

import Proveedores.DAOs.DAO_Proveedor;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proveedor.findAll", query = "SELECT p FROM Proveedor p"),
    @NamedQuery(name = "Proveedor.findByProveedorID", query = "SELECT p FROM Proveedor p WHERE p.proveedorID = :proveedorID"),
    @NamedQuery(name = "Proveedor.findByNombre", query = "SELECT p FROM Proveedor p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Proveedor.findByDescripcion", query = "SELECT p FROM Proveedor p WHERE p.descripcion = :descripcion"),
    @NamedQuery(name = "Proveedor.findByTelefono", query = "SELECT p FROM Proveedor p WHERE p.telefono = :telefono")})
public class Proveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ProveedorID")
    private Integer proveedorID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "Descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Telefono")
    private String telefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "Estado")
    private String estado;
    @JoinColumn(name = "Lista_ContactosID", referencedColumnName = "Lista_ContactosID")
    @ManyToOne(optional = false)
    private ListaContactos listaContactosID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proveedorID")
    private Collection<Oferta> ofertasCollection;

    public Proveedor() {
    }

    public Proveedor(Integer proveedorID) {
        this.proveedorID = proveedorID;
    }

    public Proveedor(Integer proveedorID, String nombre, String telefono) {
        this.proveedorID = proveedorID;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Proveedor(String nombre, String descripcion, String telefono, String estado, ListaContactos listaContactosID) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.estado = estado;
        this.listaContactosID = listaContactosID;
    }
    
    

    public Integer getProveedorID() {
        return proveedorID;
    }

    public void setProveedorID(Integer proveedorID) {
        this.proveedorID = proveedorID;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ListaContactos getListaContactosID() {
        return listaContactosID;
    }

    public void setListaContactosID(ListaContactos listaContactosID) {
        this.listaContactosID = listaContactosID;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @XmlTransient
    public Collection<Oferta> getOfertasCollection() {
        return ofertasCollection;
    }

    public void setOfertasCollection(Collection<Oferta> ofertasCollection) {
        this.ofertasCollection = ofertasCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proveedorID != null ? proveedorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.proveedorID == null && other.proveedorID != null) || (this.proveedorID != null && !this.proveedorID.equals(other.proveedorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Proveedores.Modelos.Proveedor[ proveedorID=" + proveedorID + " ]";
    }

    public void CrearProveedor(String nombre, String status, String telefono, String descripcion, int listaContactosID) {
        DAO_Proveedor DAOp = new DAO_Proveedor();
        
        this.proveedorID = DAOp.obtenerIDValida();
        this.nombre = nombre;
        this.estado = status;
        this.telefono = telefono;
        this.descripcion = descripcion;
        this.listaContactosID = new ListaContactos(listaContactosID);
    }
    
}
