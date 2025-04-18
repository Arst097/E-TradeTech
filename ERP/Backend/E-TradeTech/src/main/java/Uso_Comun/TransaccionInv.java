/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Uso_Comun;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author HP PORTATIL
 */
@Entity
@Table(name = "Transaccion_Inv")
@NamedQueries({
    @NamedQuery(name = "TransaccionInv.findAll", query = "SELECT t FROM TransaccionInv t"),
    @NamedQuery(name = "TransaccionInv.findByTransaccionInvID", query = "SELECT t FROM TransaccionInv t WHERE t.transaccionInvID = :transaccionInvID"),
    @NamedQuery(name = "TransaccionInv.findByFecha", query = "SELECT t FROM TransaccionInv t WHERE t.fecha = :fecha")})
public class TransaccionInv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Transaccion_InvID")
    private Integer transaccionInvID;
    @Basic(optional = false)
    @Column(name = "Fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @JoinColumn(name = "Historial_Transacc_InvID", referencedColumnName = "Historial_Transacc_InvID")
    @ManyToOne(optional = false)
    private HistorialTransaccInv historialTransaccInvID;
    @JoinColumn(name = "Inventario_EmisorID", referencedColumnName = "InventarioID")
    @ManyToOne(optional = false)
    private Inventario inventarioEmisorID;
    @JoinColumn(name = "Inventario_ReceptorD", referencedColumnName = "InventarioID")
    @ManyToOne(optional = false)
    private Inventario inventarioReceptorD;

    public TransaccionInv() {
    }

    public TransaccionInv(Integer transaccionInvID) {
        this.transaccionInvID = transaccionInvID;
    }

    public TransaccionInv(Integer transaccionInvID, Date fecha) {
        this.transaccionInvID = transaccionInvID;
        this.fecha = fecha;
    }

    public Integer getTransaccionInvID() {
        return transaccionInvID;
    }

    public void setTransaccionInvID(Integer transaccionInvID) {
        this.transaccionInvID = transaccionInvID;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public HistorialTransaccInv getHistorialTransaccInvID() {
        return historialTransaccInvID;
    }

    public void setHistorialTransaccInvID(HistorialTransaccInv historialTransaccInvID) {
        this.historialTransaccInvID = historialTransaccInvID;
    }

    public Inventario getInventarioEmisorID() {
        return inventarioEmisorID;
    }

    public void setInventarioEmisorID(Inventario inventarioEmisorID) {
        this.inventarioEmisorID = inventarioEmisorID;
    }

    public Inventario getInventarioReceptorD() {
        return inventarioReceptorD;
    }

    public void setInventarioReceptorD(Inventario inventarioReceptorD) {
        this.inventarioReceptorD = inventarioReceptorD;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transaccionInvID != null ? transaccionInvID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TransaccionInv)) {
            return false;
        }
        TransaccionInv other = (TransaccionInv) object;
        if ((this.transaccionInvID == null && other.transaccionInvID != null) || (this.transaccionInvID != null && !this.transaccionInvID.equals(other.transaccionInvID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Uso_Comun.TransaccionInv[ transaccionInvID=" + transaccionInvID + " ]";
    }
    
}
