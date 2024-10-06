/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Controlador.ConexionSQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Cristian
 */
public class Productos {
    private int id,codigo,id_proveedor,cantidad;
    private float precio;
    private String nombre;
    private ConexionSQL sql;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    
    public void agregarproducto(String nom, float precio, int codigo, int cantidad, int proveedor) {
        String querry = "insert into productos (nombre,precio,codigo,cantidad,id_proveedor) values (?,?,?,?,?)";

        try {
            ps = con.prepareStatement(querry);

            ps.setString(1, nom);
            ps.setFloat(2, precio);
            ps.setInt(3, codigo);
            ps.setInt(4, cantidad);
            ps.setInt(5, proveedor);

            ps.execute();
            con.close();

            System.out.println("Producto Ingresado");

        } catch (SQLException ex) {
            System.out.println("Error sql");
        }
    }

    public ArrayList tablaproductos() {
        ArrayList productos = new ArrayList();
        String querry = "select id,nombre,precio,codigo,cantidad,id_proveedor from productos";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            while (rs.next()) {
                productos.add(rs.getInt("id"));
                productos.add(rs.getString("nombre"));
                productos.add(rs.getFloat("precio"));
                productos.add(rs.getInt("codigo"));
                productos.add(rs.getInt("cantidad"));
                productos.add(rs.getInt("id_proveedor"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return productos;
    }
    
     public String[] buscarprod(String codigo) {

        String prod[] = {"0", "0", "0", "0"};

        /*if(codigo.length()!=13){
            return prod;
        }*/
        String querry = "select id,nombre,precio,cantidad from productos where codigo=" + codigo + ";";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();

            prod[0] = rs.getString("id");
            prod[1] = rs.getString("nombre");
            prod[2] = rs.getString("precio");
            prod[3] = rs.getString("cantidad");

            System.out.println(prod[0] + " " + prod[1] + " " + prod[2] + " " + prod[3]);
            con.close();

        } catch (SQLException ex) {
            System.out.println("Ese producto no esta registrado");
            return prod;
        }
        return prod;
    }
}
