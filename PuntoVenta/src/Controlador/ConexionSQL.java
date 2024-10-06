/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cristian
 */
public class ConexionSQL {

    String usuario = "root";
    String contra = "";
    String url = "jdbc:mysql://localhost:3306/puntoventa";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement ps;

    public boolean Conexion() {

        con = null;

        try {
            Class.forName(driver);
            // Nos conectamos a la bd
            con = (Connection) DriverManager.getConnection(url, usuario, contra);
            // Si la conexion fue exitosa mostramos un mensaje de conexion exitosa
            if (con != null) {
                System.out.println("Conexión exitosa");
                return true;
            }
        } // Si la conexion NO fue exitosa mostramos un mensaje de error
        catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Conexión fallida " + ex);
            return false;
        }
        return false;

    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
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
    
    public void agregarlogin(String usuario, String contraseña,String puesto, int empleado) {
        String querry = "insert into login (usuario,contraseña,puesto,id_empleados) values (?,?,?,?)";
        System.out.println(usuario+" "+contraseña+" "+puesto+" "+empleado);
        try {
            ps = con.prepareStatement(querry);

            ps.setString(1, usuario);
            ps.setString(2, contraseña);
            ps.setString(3, puesto);
            ps.setInt(4, empleado);

            ps.execute();
            con.close();

            System.out.println("Login Ingresado");

        } catch (SQLException ex) {
            System.out.println("Error sql"+ex);
        }
    }
    
    public void eliminarlogin(int id) {
        String querry = "delete from login where id="+id;
        
        try {
            ps = con.prepareStatement(querry);
            
            ps.execute();
            con.close();

            System.out.println("Empleado Eliminado");

        } catch (SQLException ex) {
            System.out.println("Error sql"+ex);
        }
    }
    
    public void modificarlogin(int nid,String usuario,String contraseña,int idemple, String puesto,int id){
        String querry = "update login set id=?,usuario='"+usuario+"',contraseña='"+contraseña+"',puesto='"+puesto+"',id_empleados=? where id=?;";

        try {
            ps = con.prepareStatement(querry);

            
            ps.setInt(1, nid);
            /*ps.setString(2, usuario);
            ps.setString(3, contraseña);
            ps.setString(4, puesto);*/
            ps.setInt(2, idemple);
            ps.setInt(3, id);

            ps.execute();
            con.close();

            System.out.println("Login Modificado");

        } catch (SQLException ex) {
            System.out.println("Error sql"+ex);
        }
    }
    
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
    
    public void modificarcliente(int nid,String nombre,String apellidop,String apellidom,String tarjeta,String registro,int puntos, int id){
        String querry = "update clientes set id=?,nombre=?,apellidop=?,apellidom=?,tarjeta=?,registro=?,puntos=? where id=?";

        try {
            ps = con.prepareStatement(querry);

            ps.setInt(1, nid);
            ps.setString(2, nombre);
            ps.setString(3, apellidop);
            ps.setString(4, apellidom);
            ps.setString(5, tarjeta);
            ps.setString(6, registro);
            ps.setInt(7, puntos);
            ps.setInt(8, id);

            ps.execute();
            con.close();

            System.out.println("Cliente Modificado");

        } catch (SQLException ex) {
            System.out.println("Error sql");
        }
    }
    
    public void eliminarcliente(int id) {
        String querry = "delete from clientes where id="+id;
        
        try {
            ps = con.prepareStatement(querry);
            
            ps.execute();
            con.close();

            System.out.println("Empleado Eliminado");

        } catch (SQLException ex) {
            System.out.println("Error sql"+ex);
        }
    }
    
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
    
    public void eliminarempleado(int id) {
        String querry = "delete from empleados where id="+id;
        
        try {
            ps = con.prepareStatement(querry);
            
            ps.execute();
            con.close();

            System.out.println("Empleado Eliminado");

        } catch (SQLException ex) {
            System.out.println("Error sql"+ex);
        }
    }
    
    public void modificarempleado(int nid, String nombre, String apellidop, String apellidom, String direccion,int telefono,int sueldo,int edad,int id) {
        String querry = "update empleados set id=?,nombre=?,apellidop=?,apellidom=?,direccion=?,telefono=?,sueldo=?,edad=? where id=?";

        try {
            ps = con.prepareStatement(querry);

            ps.setInt(1, nid);
            ps.setString(2, nombre);
            ps.setString(3, apellidop);
            ps.setString(4, apellidom);
            ps.setString(5, direccion);
            ps.setInt(6, telefono);
            ps.setInt(7, sueldo);
            ps.setInt(8, edad);
            ps.setInt(9, id);

            ps.execute();
            con.close();

            System.out.println("Empleado Modificado");

        } catch (SQLException ex) {
            System.out.println("Error sql");
        }
    }
    
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
    
    public void eliminarproducto(int id) {
        String querry = "delete from productos where id="+id;
        
        try {
            ps = con.prepareStatement(querry);
            
            ps.execute();
            con.close();

            System.out.println("Producto Eliminado");

        } catch (SQLException ex) {
            System.out.println("Error sql"+ex);
        }
    }
    
    public void modificarproducto(int nid,String nombre,float precio,int codigo,int cantidad,int idprovee,int id){
        String querry = "update productos set id=?,nombre=?,precio=?,codigo=?,cantidad=?,id_proveedor=? where id=?";

        try {
            ps = con.prepareStatement(querry);

            ps.setInt(1, nid);
            ps.setString(2, nombre);
            ps.setFloat(3, precio);
            ps.setInt(4, codigo);
            ps.setInt(5, cantidad);
            ps.setInt(6, idprovee);
            ps.setInt(7, id);

            ps.execute();
            con.close();

            System.out.println("Producto Modificado");

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
    
    public void eliminarproveedor(int id) {
        String querry = "delete from proveedores where id="+id;
        
        try {
            ps = con.prepareStatement(querry);
            
            ps.execute();
            con.close();

            System.out.println("Proveedor Eliminado");

        } catch (SQLException ex) {
            System.out.println("Error sql"+ex);
        }
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
    
    public void modificarproveedor(int nid,String nom, String direccion, int telefono, String rfc, int id) {
        String querry = "update proveedores set id=?,nombre=?,direccion=?,telefono=?,rfc=? where id=?";

        try {
            ps = con.prepareStatement(querry);

            ps.setInt(1, nid);
            ps.setString(2, nom);
            ps.setString(3, direccion);
            ps.setInt(4, telefono);
            ps.setString(5, rfc);
            ps.setInt(6, id);

            ps.execute();
            con.close();

            System.out.println("Proveedor Modificado");

        } catch (SQLException ex) {
            System.out.println("Error sql");
        }
    }
    
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

    public void agregarventa(String tipopago, int ultventa, String codigo, String cantidad, float total, String fecha, String tarjeta, String usuario, String contra) {

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
            ps.setString(8, tipopago);

            ps.execute();

            querry = "update productos set cantidad=(cantidad-" + cantidad + ") where id=" + idprod;

            ps = con.prepareStatement(querry);
            ps.execute(querry);

            
            
            //System.out.println(rs.getInt("id"));
            //con.close();

        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }
    }
    public void maspuntos(int puntosmas, String tarjeta){
        String  querry = "select id from clientes where tarjeta='" + tarjeta + "';";

        try {
            
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();
            int idcli = rs.getInt("id");
            
            querry = "update clientes set puntos=(puntos+" + puntosmas + ") where id=" + idcli;
            ps = con.prepareStatement(querry);
            ps.execute(querry);
            //System.out.println(rs.getInt("id"));
            //con.close();

        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }
    }
    
    public void menospuntos(int puntosmenos, String tarjeta){
        String  querry = "select id from clientes where tarjeta='" + tarjeta + "';";

        try {
            
            st = con.createStatement();
            rs = st.executeQuery(querry);

            rs.next();
            int idcli = rs.getInt("id");
            
            querry = "update clientes set puntos=(puntos-" + puntosmenos + ") where id=" + idcli;
            ps = con.prepareStatement(querry);
            ps.execute(querry);
            //System.out.println(rs.getInt("id"));
            //con.close();

        } catch (SQLException ex) {
            System.out.println("Error" + ex);

        }
    }
    
    public void eliminarventa(int id) {
        String querry = "delete from ventas where id="+id;
        
        try {
            ps = con.prepareStatement(querry);
            
            ps.execute();
            con.close();

            System.out.println("Venta Eliminada");

        } catch (SQLException ex) {
            System.out.println("Error sql"+ex);
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
    
     
    /*
    // our SQL SELECT query. 
      // if you only need a few columns, specify them by name instead of using "*"
      String query = "SELECT * FROM users";

      // create the java statement
      Statement st = conn.createStatement();
      
      // execute the query, and get a java resultset
      ResultSet rs = st.executeQuery(query);
      
      // iterate through the java resultset
      while (rs.next())
      {
        int id = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        Date dateCreated = rs.getDate("date_created");
        boolean isAdmin = rs.getBoolean("is_admin");
        int numPoints = rs.getInt("num_points");
        
        // print the results
        System.out.format("%s, %s, %s, %s, %s, %s\n", id, firstName, lastName, dateCreated, isAdmin, numPoints);
      }
      st.close();
    
     */
}
