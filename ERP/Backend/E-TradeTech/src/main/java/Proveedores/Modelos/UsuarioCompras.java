/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.Modelos;

import Uso_Comun.Modelos.Empleado;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author HP PORTATIL
 */
@Entity
@Table(name = "Usuario_Compras")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioCompras.findAll", query = "SELECT u FROM UsuarioCompras u"),
    @NamedQuery(name = "UsuarioCompras.findByUsuarioComprasID", query = "SELECT u FROM UsuarioCompras u WHERE u.usuarioComprasID = :usuarioComprasID"),
    @NamedQuery(name = "UsuarioCompras.findByPuesto", query = "SELECT u FROM UsuarioCompras u WHERE u.puesto = :puesto"),
    @NamedQuery(name = "UsuarioCompras.findByTelefono", query = "SELECT u FROM UsuarioCompras u WHERE u.telefono = :telefono")})
public class UsuarioCompras implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Usuario_ComprasID")
    private Integer usuarioComprasID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "Puesto")
    private String puesto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "Telefono")
    private String telefono;
    @JoinColumn(name = "Lista_ContactosID", referencedColumnName = "Lista_ContactosID")
    @ManyToOne
    private ListaContactos listaContactosID;
    @JoinColumn(name = "Usuario_Usuario_id", referencedColumnName = "Usuario_id")
    @ManyToOne(optional = false)
    private Empleado usuarioEmpleadoid;

    public UsuarioCompras() {
    }

    public UsuarioCompras(Integer usuarioComprasID) {
        this.usuarioComprasID = usuarioComprasID;
    }

    public UsuarioCompras(Integer usuarioComprasID, String puesto, String telefono) {
        this.usuarioComprasID = usuarioComprasID;
        this.puesto = puesto;
        this.telefono = telefono;
    }

    public Integer getUsuarioComprasID() {
        return usuarioComprasID;
    }

    public void setUsuarioComprasID(Integer usuarioComprasID) {
        this.usuarioComprasID = usuarioComprasID;
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

    public ListaContactos getListaContactosID() {
        return listaContactosID;
    }

    public void setListaContactosID(ListaContactos listaContactosID) {
        this.listaContactosID = listaContactosID;
    }

    public Empleado getUsuarioEmpleadoid() {
        return usuarioEmpleadoid;
    }

    public void setUsuarioEmpleadoid(Empleado usuarioEmpleadoid) {
        this.usuarioEmpleadoid = usuarioEmpleadoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioComprasID != null ? usuarioComprasID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioCompras)) {
            return false;
        }
        UsuarioCompras other = (UsuarioCompras) object;
        if ((this.usuarioComprasID == null && other.usuarioComprasID != null) || (this.usuarioComprasID != null && !this.usuarioComprasID.equals(other.usuarioComprasID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Proveedores.Modelos.UsuarioCompras[ usuarioComprasID=" + usuarioComprasID + " ]";
    }
    
}
