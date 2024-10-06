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
public class Proveedores {

    private int id, telefono;
    private String nombre, direccion, rfc;
    private ConexionSQL sql;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;

    public ArrayList tablaproveedor() {
        ArrayList proveedores = new ArrayList();
        String querry = "select id,nombre,direccion,telefono,rfc from proveedores";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            while (rs.next()) {
                proveedores.add(rs.getInt("id"));
                proveedores.add(rs.getString("nombre"));
                proveedores.add(rs.getString("direccion"));
                proveedores.add(rs.getInt("telefono"));
                proveedores.add(rs.getString("rfc"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return proveedores;
    }

    public ArrayList selectproveedor() {
        ArrayList proveedores = new ArrayList();
        String querry = "select id,nombre from proveedores";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            while (rs.next()) {
                proveedores.add(rs.getInt("id"));
                proveedores.add(rs.getString("nombre"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return proveedores;
    }

    public void agregarproveedor(String nom, String direccion, int telefono, String rfc) {
        String querry = "insert into proveedores (nombre,direccion,telefono,rfc) values (?,?,?,?)";

        try {
            ps = con.prepareStatement(querry);

            ps.setString(1, nom);
            ps.setString(2, direccion);
            ps.setInt(3, telefono);
            ps.setString(4, rfc);

            ps.execute();
            con.close();

            System.out.println("Proveedor Ingresado");

        } catch (SQLException ex) {
            System.out.println("Error sql");
        }
    }
}
