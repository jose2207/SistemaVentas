/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import pe.com.syscenterlife.autocomp.ModeloDataAutocomplet;
import pe.edu.upeu.app.dao.conx.Conn;
import pe.edu.upeu.app.modelo.ProductoTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author ACER ASPIRE
 */
public class ProductoDAO implements ProductoDaoI {
    int r;
    Statement stmt = null;
    Vector columnNames;
    Vector visitdata;
    Connection connection;
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ProductoDAO.class.getName());
    ResultSet rs = null;

    public ProductoDAO() {

        connection = Conn.connectSQLite();
    }

    @Override
    public int create(ProductoTO d) {
        int rsId = 0;
        String[] returns = {"id_producto"};
        String sql = "INSERT INTO producto(id_producto, nombre, pu, utilidad, stock, id_categoria, id_marca) "
                + "VALUES(?,?,?,?,?,?,?)";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);

            ps.setInt(++i, d.getId_producto());
            ps.setString(++i, d.getNombre());
            ps.setDouble(++i, d.getPu());
            ps.setDouble(++i, d.getUtilidad());
            ps.setDouble(++i, d.getStock());
            ps.setInt(++i, d.getId_categoria());
            ps.setInt(++i, d.getId_marca());

            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException ex) {
//System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);

        }
        return rsId;
    }

    @Override
    public int update(ProductoTO d) {
        System.out.println("actualizar d.getid_producto " + d.getId_producto());
        int comit = 0;
        String sql = "UPDATE cliente SET "
                //id_producto, nombreP, pu, utilidad, stock, id_categoria, id_marca
                + "WHERE id_producto=?, "
                + "nombreP=? "
                + "pu=?, "
                + "utilidad=?, "
                + "stock=?, "
                + "id_categoria=?, "
                + "id_marca=? ";

        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(++i, d.getId_producto());
            ps.setString(++i, d.getNombre());
            ps.setDouble(++i, d.getPu());
            ps.setDouble(++i, d.getUtilidad());
            ps.setDouble(++i, d.getStock());
            ps.setInt(++i, d.getId_categoria());
            ps.setInt(++i, d.getId_marca());

            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int delete(String id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM cliente WHERE id_producto = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
// System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    @Override
    public List<ProductoTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ProductoTO> listarProductos() {
        List<ProductoTO> listarProductos = new ArrayList();
        String sql = "SELECT * FROM producto";
        try {
            connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ProductoTO pro = new ProductoTO();

                pro.setId_producto(rs.getInt("id_producto"));
                pro.setNombre(rs.getString("nombre"));
                pro.setPu(rs.getDouble("pu"));
                pro.setUtilidad(rs.getDouble("utilidad"));
                pro.setStock(rs.getDouble("stock"));
                pro.setId_categoria(rs.getInt("id_categoria"));
                pro.setId_marca(rs.getInt("id_marca"));

                listarProductos.add(pro);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarProductos;
    }

    @Override
    public ProductoTO buscarProducto(String nombreP) {
        ProductoTO producto = new ProductoTO();
        String sql = "SELECT * FROM cliente WHERE dniruc = ?";
        try {
            connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            int Id_producto = 1;
            ps.setInt(1, Id_producto);
            rs = ps.executeQuery();
            if (rs.next()) {
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombreP"));
                producto.setPu(rs.getDouble("Precio Unitario"));
                producto.setUtilidad(rs.getDouble("utilidad"));
                producto.setStock(rs.getDouble("stocc"));
                producto.setId_categoria(rs.getInt("id_categoria"));
                producto.setId_marca(rs.getInt("id_marca"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return producto;
    }

    @Override
    public void reportarProducto() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ModeloDataAutocomplet> listAutoComplet(String filter) {
        List<ModeloDataAutocomplet> listarProductos = new ArrayList();
        String sql = "SELECT * FROM producto WHERE nombre like ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, filter + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                ModeloDataAutocomplet data = new ModeloDataAutocomplet();
                /*ModeloDataAutocomplet.TIPE_DISPLAY = "ID";*/
                data.setIdx(rs.getString("nombre"));
                data.setNombreDysplay(rs.getString("id_producto"));
                data.setOtherData(rs.getDouble("pu") + ":" + rs.getDouble("stock"));
                listarProductos.add(data);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarProductos;
    }

    

    @Override
    public int actualizarStock(double cant, double idp) {
        String sql="UPDATE producto set stock=stock-? where id_producto=?";
        try {
            connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            ps.setDouble(1, cant);
            ps.setDouble(2,idp);
            ps.executeUpdate();
            
        } catch (Exception e) {
        }
        return r;
    }
}
