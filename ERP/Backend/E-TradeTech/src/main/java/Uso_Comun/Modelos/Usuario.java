/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun.Modelos;

import Uso_Comun.Modelos.Cliente;
import Inventario.Modelos.Gestores;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Gestores;
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
@Table(name = "Usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByUsuarioid", query = "SELECT u FROM Usuario u WHERE u.usuarioid = :usuarioid"),
    @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuario.findByContrase\u00f1aSHA256", query = "SELECT u FROM Usuario u WHERE u.contrase\u00f1aSHA256 = :contrase\u00f1aSHA256")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Usuario_id")
    private Integer usuarioid;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "Contrase\u00f1a_SHA256")
    private String contraseñaSHA256;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuarioid")
    private Collection<Despachador> despachadorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuarioid")
    private Collection<Gestores> gestoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioUsuarioid")
    private Collection<Cliente> clienteCollection;

    public Usuario() {
    }

    public Usuario(Integer usuarioid) {
        this.usuarioid = usuarioid;
    }

    public Usuario(Integer usuarioid, String nombre, String correo, String contraseñaSHA256) {
        this.usuarioid = usuarioid;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseñaSHA256 = contraseñaSHA256;
    }

    public Integer getUsuarioid() {
        return usuarioid;
    }

    public void setUsuarioid(Integer usuarioid) {
        this.usuarioid = usuarioid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseñaSHA256() {
        return contraseñaSHA256;
    }

    public void setContraseñaSHA256(String contraseñaSHA256) {
        this.contraseñaSHA256 = contraseñaSHA256;
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

    public Collection<Cliente> getClienteCollection() {
        return clienteCollection;
    }

    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        this.clienteCollection = clienteCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuarioid != null ? usuarioid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.usuarioid == null && other.usuarioid != null) || (this.usuarioid != null && !this.usuarioid.equals(other.usuarioid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Usuario[ usuarioid=" + usuarioid + " ]";
    }
    
}
