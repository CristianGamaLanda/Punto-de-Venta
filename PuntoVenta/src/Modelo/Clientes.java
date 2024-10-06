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
public class Clientes {
    private int id,puntos;
    private String nombre,apellidop,apellidom,tarjeta,registro;
    private ConexionSQL sql;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    
    public String[] buscarXtarjeta(String tarjeta) {
        String cliente[] = {"0", "0"};

        String querry = "select nombre,apellidop,apellidom,puntos from clientes where tarjeta='" + tarjeta + "';";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();

            cliente[0] = rs.getString("nombre");
            cliente[0] = cliente[0] + " " + rs.getString("apellidop");
            cliente[0] = cliente[0] + " " + rs.getString("apellidom");
            cliente[1] = "" + rs.getInt("puntos");

            System.out.println(cliente[0] + " " + cliente[1]);
            con.close();

        } catch (SQLException ex) {
            System.out.println("Tarjeta erronea o inexistente" + ex);
            return cliente;
        }
        return cliente;
    }
    
    public ArrayList tablaclientes() {
        ArrayList clientes = new ArrayList();
        String querry = "select id,nombre,apellidop,apellidom,tarjeta,registro,puntos from clientes";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            while (rs.next()) {
                clientes.add(rs.getInt("id"));
                clientes.add(rs.getString("nombre"));
                clientes.add(rs.getString("apellidop"));
                clientes.add(rs.getString("apellidom"));
                clientes.add(rs.getString("tarjeta"));
                clientes.add(rs.getString("registro"));
                clientes.add(rs.getInt("puntos"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return clientes;
    }
    
    public void agregarcliente(String nom, String ap, String am, String tarjeta, String registro, int puntos) {

        String querry = "insert into clientes (nombre,apellidop,apellidom,tarjeta,registro,puntos) values (?,?,?,?,?,?)";

        try {
            ps = con.prepareStatement(querry);

            ps.setString(1, nom);
            ps.setString(2, ap);
            ps.setString(3, am);
            ps.setString(4, tarjeta);
            ps.setString(5, registro);
            ps.setInt(6, puntos);

            ps.execute();
            con.close();

            System.out.println("Cliente Ingresado");

        } catch (SQLException ex) {
            System.out.println("Error sql");
        }
    }
}
