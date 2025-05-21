/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun.Modelos;

import Ventas.Modelos.Cliente;
import Inventario.Modelos.Gestores;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Despachador;
import Inventario.Modelos.Gestores;
import Proveedores.Modelos.UsuarioCompras;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author HP PORTATIL
 */
@Entity
@Table(name = "Empleado")
public class Empleado implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Usuario_id")
    private Integer empleadoid;
    @Basic(optional = false)
    @Column(name = "Nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "Correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "Contrase\u00f1a_SHA256")
    private String contraseñaSHA256;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioEmpleadoid")
    private Collection<Despachador> despachadorCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioEmpleadoid")
    private Collection<Gestores> gestoresCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarioEmpleadoid")
    private Collection<UsuarioCompras> usuarioComprasCollection;
    
    public Empleado() {
    }

    public Empleado(Integer empleadoid) {
        this.empleadoid = empleadoid;
    }

    public Empleado(Integer empleadoid, String nombre, String correo, String contraseñaSHA256) {
        this.empleadoid = empleadoid;
        this.nombre = nombre;
        this.correo = correo;
        this.contraseñaSHA256 = contraseñaSHA256;
    }

    public Integer getEmpleadoid() {
        return empleadoid;
    }

    public void setEmpleadoid(Integer empleadoid) {
        this.empleadoid = empleadoid;
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

    //Si algo utiliza alguno de estos dos metodos, hay que cambiarlos o borrarlo
    //Cliente ya no tiene relacion con Empleado
    public Collection<Cliente> getClienteCollection() {
        throw new UnsupportedOperationException("Funcion getClienteCollection no disponible, Cliente ya no tiene relacion con usuario.");
    }
    public void setClienteCollection(Collection<Cliente> clienteCollection) {
        throw new UnsupportedOperationException("Funcion setClienteCollection no disponible, Cliente ya no tiene relacion con usuario.");
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (empleadoid != null ? empleadoid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.empleadoid == null && other.empleadoid != null) || (this.empleadoid != null && !this.empleadoid.equals(other.empleadoid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Usuario[ empleadoid=" + empleadoid + " ]";
    }

    @XmlTransient
    public Collection<UsuarioCompras> getUsuarioComprasCollection() {
        return usuarioComprasCollection;
    }

    public void setUsuarioComprasCollection(Collection<UsuarioCompras> usuarioComprasCollection) {
        this.usuarioComprasCollection = usuarioComprasCollection;
    }
    
}
