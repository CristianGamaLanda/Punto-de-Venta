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
public class Login {
    private int id,id_empleados;
    private String usuario,contraseña,puesto;
    private ConexionSQL sql;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    
    public boolean login(String usuario, String contraseña) {
        boolean si = false;
        String us, co;
        String querry = "select usuario, contraseña from login where usuario='" + usuario + "' and contraseña='" + contraseña + "';";

        //System.out.println(usuario+" "+contraseña);
        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();

            us = rs.getString("usuario");
            co = rs.getString("contraseña");
            //System.out.println(us+" "+co);
            if (us.equals(usuario) && co.equals(contraseña)) {
                return true;
            }
            //con.close();
        } catch (SQLException ex) {
            System.out.println("Datos incorrectos");
        }

        return si;
    }
    
    public boolean esADMIN(String usuario, String contraseña) {
        boolean si = false;
        String puesto;
        String querry = "select usuario, contraseña, puesto from login where usuario='" + usuario + "' and contraseña='" + contraseña + "';";

        //System.out.println(usuario+" "+contraseña);
        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();

            puesto = rs.getString("puesto");
            //System.out.println(us+" "+co);
            if (puesto.equals("ADMIN")) {
                return true;
            }
            con.close();
        } catch (SQLException ex) {
            System.out.println("Datos incorrectos");
        }

        return si;
    }
    
    public ArrayList tablalogin() {
        ArrayList login = new ArrayList();
        String querry = "select id,usuario,contraseña,puesto,id_empleados from login";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            while (rs.next()) {
                login.add(rs.getInt("id"));
                login.add(rs.getString("usuario"));
                login.add(rs.getString("contraseña"));
                login.add(rs.getString("puesto"));
                login.add(rs.getInt("id_empleados"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return login;
    }
    
    public void agregarlogin(String usuario, String contraseña, int empleado) {
        String querry = "insert into login (usuario,contraseña,id_empleado) values (?,?,?)";

        try {
            ps = con.prepareStatement(querry);

            ps.setString(1, usuario);
            ps.setString(2, contraseña);
            ps.setInt(3, empleado);

            ps.execute();
            con.close();

            System.out.println("Login Ingresado");

        } catch (SQLException ex) {
            System.out.println("Error sql");
        }
    }
    
}
