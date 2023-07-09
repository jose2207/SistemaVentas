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
import pe.edu.upeu.app.dao.conx.Conn;
import pe.edu.upeu.app.modelo.MarcaTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author ACER ASPIRE
 */
public class MarcaDAO implements MarcaDaoI {

    Statement stmt = null;
    Vector columnNames;
    Vector visitdata;
    Connection connection = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(MarcaDAO.class.getName());
    ResultSet rs = null;

    public MarcaDAO() {
        columnNames = new Vector();
        visitdata = new Vector();
    }

    @Override
    public int create(MarcaTO d) {
        int rsId = 0;
        String[] returns = {"id_marca"};
        String sql = "INSERT INTO marca(id_marca, nombre) "
                + "VALUES(?,?)";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setInt(++i, d.getId_marca());
            ps.setString(++i, d.getNombre());

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
    public int update(MarcaTO d) {
        System.out.println("actualizar d.getId_marca: " + d.getId_marca());
        int comit = 0;
        String sql = "UPDATE cliente SET "
                + "WHERE id_marca=?, "
                + "nombre=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(++i, d.getId_marca());
            ps.setString(++i, d.getNombre());

            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int delete(String id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM cliente ";
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
    public List<MarcaTO> listarMarca() {
        List<MarcaTO> listarmarca = new ArrayList();
        String sql = "SELECT * FROM cliente";
        try {
            connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                MarcaTO mar = new MarcaTO();
                
                mar.setId_marca(rs.getInt("id_producto"));
                mar.setNombre(rs.getString("nombre"));
                
                listarmarca.add(mar);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarmarca;
    }

    @Override
    public MarcaTO buscarMarca(String dni) {
        MarcaTO marca = new MarcaTO();
        String sql = "SELECT * FROM marca";
        try {
            connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            int id_categoria = 1;
            ps.setInt(1, id_categoria);
            rs = ps.executeQuery();
            if (rs.next()) {
                marca.setId_marca(rs.getInt("id_marca"));
                marca.setNombre(rs.getString("nombre"));

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return marca;
    }

    @Override
    public void reportarMarca() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
