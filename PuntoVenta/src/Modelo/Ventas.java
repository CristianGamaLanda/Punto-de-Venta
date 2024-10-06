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
public class Ventas {
    private int id,id_cliente,id_empleado,id_producto,cantidad;
    private float precioT;
    private String fecha,tipo_pago;
    private ConexionSQL sql;
    private Connection con;
    private Statement st;
    private ResultSet rs;
    private PreparedStatement ps;
    
    public int ultimaventa() {

        String querry = "select id from ventas";
        int id = 0;

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();
            if (!rs.isLast() && !rs.isFirst()) {
                System.out.println("No hay ninguna venta");
                return 1;
            } else {

                querry = "select id from ventas order by id desc";
                st = con.createStatement();
                rs = st.executeQuery(querry);

                rs.next();

                id = 1 + rs.getInt("id");
                System.out.println(id);
            }

            //con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return id;
    }

    public void agregarventa(int puntos, int ultventa, String codigo, String cantidad, float total, String fecha, String tarjeta, String usuario, String contra) {

        String querry = "select id from ventas;";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();

            querry = "select id from productos where codigo=" + codigo + ";";
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();
            int idprod = rs.getInt("id");

            querry = "select id from clientes where tarjeta='" + tarjeta + "';";
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();
            int idcli = rs.getInt("id");

            querry = "select id_empleados from login where usuario='" + usuario + "' and contraseña='" + contra + "';";
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();
            int idemp = rs.getInt("id_empleados");

            querry = "insert into ventas (id ,id_producto , cantidad , precioT , fecha , id_empleado , id_cliente, tipo_pago)"
                    + "values (?,?,?,?,?,?,?,?)";

            ps = con.prepareStatement(querry);
            ps.setInt(1, ultventa);
            ps.setInt(2, idprod);
            ps.setInt(3, Integer.parseInt(cantidad));
            ps.setFloat(4, total);
            ps.setString(5, fecha);
            ps.setInt(6, idemp);
            ps.setInt(7, idcli);
            ps.setString(8, "null");

            ps.execute();

            querry = "update productos set cantidad=(cantidad-" + cantidad + ") where id=" + idprod;

            ps = con.prepareStatement(querry);
            ps.execute(querry);

            querry = "update clientes set puntos=(puntos+" + puntos + ") where id=" + idcli;

            ps = con.prepareStatement(querry);
            ps.execute(querry);
            //System.out.println(rs.getInt("id"));
            //con.close();

        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }
    }
    
    public ArrayList tablaventas() {
        ArrayList ventas = new ArrayList();
        String querry = "select id,id_producto,cantidad,precioT,fecha,id_empleado,id_cliente, tipo_pago from ventas";

        try {
            st = con.createStatement();
            rs = st.executeQuery(querry);

            while (rs.next()) {
                ventas.add(rs.getInt("id"));
                ventas.add(rs.getInt("id_producto"));
                ventas.add(rs.getInt("cantidad"));
                ventas.add(rs.getFloat("precioT"));
                ventas.add(rs.getString("fecha"));
                ventas.add(rs.getInt("id_empleado"));
                ventas.add(rs.getInt("id_cliente"));
                ventas.add(rs.getString("tipo_pago"));
            }

            con.close();
        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }

        return ventas;
    }
}
