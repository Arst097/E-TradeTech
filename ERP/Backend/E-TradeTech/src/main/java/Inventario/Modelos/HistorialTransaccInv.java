/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Inventario.Modelos;

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
@Table(name = "Historial_Transacc_Inv")
@NamedQueries({
    @NamedQuery(name = "HistorialTransaccInv.findAll", query = "SELECT h FROM HistorialTransaccInv h"),
    @NamedQuery(name = "HistorialTransaccInv.findByHistorialTransaccInvID", query = "SELECT h FROM HistorialTransaccInv h WHERE h.historialTransaccInvID = :historialTransaccInvID")})
public class HistorialTransaccInv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Historial_Transacc_InvID")
    private Integer historialTransaccInvID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "historialTransaccInvID")
    private Collection<TransaccionInv> transaccionInvCollection;

    public HistorialTransaccInv() {
    }

    public HistorialTransaccInv(Integer historialTransaccInvID) {
        this.historialTransaccInvID = historialTransaccInvID;
    }

    public Integer getHistorialTransaccInvID() {
        return historialTransaccInvID;
    }

    public void setHistorialTransaccInvID(Integer historialTransaccInvID) {
        this.historialTransaccInvID = historialTransaccInvID;
    }

    public Collection<TransaccionInv> getTransaccionInvCollection() {
        return transaccionInvCollection;
    }

    public void setTransaccionInvCollection(Collection<TransaccionInv> transaccionInvCollection) {
        this.transaccionInvCollection = transaccionInvCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (historialTransaccInvID != null ? historialTransaccInvID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistorialTransaccInv)) {
            return false;
        }
        HistorialTransaccInv other = (HistorialTransaccInv) object;
        if ((this.historialTransaccInvID == null && other.historialTransaccInvID != null) || (this.historialTransaccInvID != null && !this.historialTransaccInvID.equals(other.historialTransaccInvID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.HistorialTransaccInv[ historialTransaccInvID=" + historialTransaccInvID + " ]";
    }
    
}
