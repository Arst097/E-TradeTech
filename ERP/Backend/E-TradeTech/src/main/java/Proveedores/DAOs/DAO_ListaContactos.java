/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Proveedores.DAOs;

import Inventario.exceptions.IllegalOrphanException;
import Inventario.exceptions.NonexistentEntityException;
import Inventario.exceptions.RollbackFailureException;
import Proveedores.Modelos.ListaContactos;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import Proveedores.Modelos.UsuarioCompras;
import java.util.ArrayList;
import java.util.Collection;
import Proveedores.Modelos.Proveedor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.UserTransaction;
import java.util.List;

/**
 *
 * @author HP PORTATIL
 */
public class DAO_ListaContactos implements Serializable {

    public DAO_ListaContactos() {
    }

}
