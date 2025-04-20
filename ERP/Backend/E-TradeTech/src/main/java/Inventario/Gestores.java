/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario;

import Uso_Comun.Model_Usuario;
import Inventario.Almacen;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author HP PORTATIL
 */
@Entity
@Table(name = "Gestores")
@NamedQueries({
    @NamedQuery(name = "Gestores.findAll", query = "SELECT g FROM Gestores g"),
    @NamedQuery(name = "Gestores.findByGestorID", query = "SELECT g FROM Gestores g WHERE g.gestorID = :gestorID"),
    @NamedQuery(name = "Gestores.findByPuesto", query = "SELECT g FROM Gestores g WHERE g.puesto = :puesto"),
    @NamedQuery(name = "Gestores.findByTelefono", query = "SELECT g FROM Gestores g WHERE g.telefono = :telefono")})
public class Gestores implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "GestorID")
    private Integer gestorID;
    @Basic(optional = false)
    @Column(name = "Puesto")
    private String puesto;
    @Basic(optional = false)
    @Column(name = "Telefono")
    private String telefono;
    @JoinColumn(name = "AlmacenID", referencedColumnName = "AlmacenID")
    @ManyToOne(optional = false)
    private Almacen almacenID;
    @JoinColumn(name = "Usuario_Usuario_id", referencedColumnName = "Usuario_id")
    @ManyToOne(optional = false)
    private Model_Usuario usuarioUsuarioid;

    public Gestores() {
    }

    public Gestores(Integer gestorID) {
        this.gestorID = gestorID;
    }

    public Gestores(Integer gestorID, String puesto, String telefono) {
        this.gestorID = gestorID;
        this.puesto = puesto;
        this.telefono = telefono;
    }

    public Integer getGestorID() {
        return gestorID;
    }

    public void setGestorID(Integer gestorID) {
        this.gestorID = gestorID;
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

    public Almacen getAlmacenID() {
        return almacenID;
    }

    public void setAlmacenID(Almacen almacenID) {
        this.almacenID = almacenID;
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
        hash += (gestorID != null ? gestorID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gestores)) {
            return false;
        }
        Gestores other = (Gestores) object;
        if ((this.gestorID == null && other.gestorID != null) || (this.gestorID != null && !this.gestorID.equals(other.gestorID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.Gestores[ gestorID=" + gestorID + " ]";
    }
    
}
