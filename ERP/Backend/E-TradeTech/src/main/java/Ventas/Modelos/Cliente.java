/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventas.Modelos;

import Uso_Comun.Modelos.Usuario;
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
@Table(name = "Cliente")
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findByClienteID", query = "SELECT c FROM Cliente c WHERE c.clienteID = :clienteID"),
    @NamedQuery(name = "Cliente.findByTelefono", query = "SELECT c FROM Cliente c WHERE c.telefono = :telefono")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ClienteID")
    private Integer clienteID;
    @Basic(optional = false)
    @Column(name = "Telefono")
    private String telefono;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String Nombre;
    @Basic(optional = false)
    @Column(name = "Correo")
    private String Correo;
    @OneToMany(mappedBy = "clienteID")
    private Collection<Pedidos> pedidosCollection;

    public Cliente() {
    }

    public Cliente(Integer clienteID) {
        this.clienteID = clienteID;
    }

    public Cliente(Integer clienteID, String telefono) {
        this.clienteID = clienteID;
        this.telefono = telefono;
    }

    public Integer getClienteID() {
        return clienteID;
    }

    public void setClienteID(Integer clienteID) {
        this.clienteID = clienteID;
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

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }
    
    //Si algo utiliza alguno de estos dos metodos, hay que cambiarlos o borrarlo
    //Cliente ya no tiene relacion con Usuario
    public Usuario getUsuarioUsuarioid() {
        throw new UnsupportedOperationException("Funcion getUsuarioUsuarioid no disponible, UsuarioUsuarioid ya no existe.");
    }
    public void setUsuarioUsuarioid(Usuario usuarioUsuarioid) {
        throw new UnsupportedOperationException("Funcion setUsuarioUsuarioid no disponible, UsuarioUsuarioid ya no existe.");
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clienteID != null ? clienteID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.clienteID == null && other.clienteID != null) || (this.clienteID != null && !this.clienteID.equals(other.clienteID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Cliente[ clienteID=" + clienteID + " ]";
    }
    
}
