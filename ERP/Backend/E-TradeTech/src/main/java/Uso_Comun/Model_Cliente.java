/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import Uso_Comun.Model_Pedidos;
import Uso_Comun.Model_Usuario;
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
public class Model_Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ClienteID")
    private Integer clienteID;
    @Basic(optional = false)
    @Column(name = "Telefono")
    private String telefono;
    @OneToMany(mappedBy = "clienteID")
    private Collection<Model_Pedidos> pedidosCollection;
    @JoinColumn(name = "Usuario_Usuario_id", referencedColumnName = "Usuario_id")
    @ManyToOne(optional = false)
    private Model_Usuario usuarioUsuarioid;

    public Model_Cliente() {
    }

    public Model_Cliente(Integer clienteID) {
        this.clienteID = clienteID;
    }

    public Model_Cliente(Integer clienteID, String telefono) {
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

    public Collection<Model_Pedidos> getPedidosCollection() {
        return pedidosCollection;
    }

    public void setPedidosCollection(Collection<Model_Pedidos> pedidosCollection) {
        this.pedidosCollection = pedidosCollection;
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
        hash += (clienteID != null ? clienteID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Model_Cliente)) {
            return false;
        }
        Model_Cliente other = (Model_Cliente) object;
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
