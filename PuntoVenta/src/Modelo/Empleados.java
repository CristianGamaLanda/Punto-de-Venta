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
public class Empleados {
    private int id,telefono,sueldo,edad;
    private String nombre,apellidop,apellidom,direccion;
    private ConexionSQL sql;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    
    public ArrayList selectempleado() {
        ArrayList empleados = new ArrayList();
        String querry = "select id,nombre,apellidop, apellidom from empleados";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            while (rs.next()) {
                empleados.add(rs.getInt("id"));
                empleados.add(rs.getString("nombre"));
                empleados.add(rs.getString("apellidop"));
                empleados.add(rs.getString("apellidom"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return empleados;
    }
    
    public void agregarempleado(String nom, String ap, String am, String direccion, int telefono, int sueldo, int edad) {
        String querry = "insert into empleados (nombre,apellidop,apellidom,direccion,telefono,sueldo, edad) values (?,?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(querry);

            ps.setString(1, nom);
            ps.setString(2, ap);
            ps.setString(3, am);
            ps.setString(4, direccion);
            ps.setInt(5, telefono);
            ps.setInt(6, sueldo);
            ps.setInt(7, edad);

            ps.execute();
            con.close();

            System.out.println("Empleado Ingresado");

        } catch (SQLException ex) {
            System.out.println("Error sql");
        }
    }
    
    public ArrayList tablaempleado() {
        ArrayList empleados = new ArrayList();
        String querry = "select id,nombre,apellidop,apellidom,direccion,telefono,sueldo,edad from empleados";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            while (rs.next()) {
                empleados.add(rs.getInt("id"));
                empleados.add(rs.getString("nombre"));
                empleados.add(rs.getString("apellidop"));
                empleados.add(rs.getString("apellidom"));
                empleados.add(rs.getString("direccion"));
                empleados.add(rs.getInt("telefono"));
                empleados.add(rs.getInt("sueldo"));
                empleados.add(rs.getInt("edad"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return empleados;
    }
}
