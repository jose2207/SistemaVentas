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
import pe.edu.upeu.app.modelo.UsuarioTO;
import pe.edu.upeu.app.util.ErrorLogger;

/**
 *
 * @author ACER ASPIRE
 */
public class UsuarioDAO implements UsuarioDaoI {

    Statement stmt = null;
    Vector columnNames;
    Vector visitdata;
    Connection connection = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(UsuarioDAO.class.getName());
    ResultSet rs = null;

    public UsuarioDAO() {
        columnNames = new Vector();
        visitdata = new Vector();
    }

    @Override
    public int create(UsuarioTO d) {
        int rsId = 0;
        String[] returns = {"dniruc"};
        String sql = "INSERT INTO usuario(id_usuario, user, clave) "
                + "VALUES(?,?,?)";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setInt(++i, d.getId_usuario());
            ps.setString(++i, d.getUser());
            ps.setString(++i, d.getClave());
            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException ex) {

            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    @Override
    public int update(UsuarioTO d) {
        System.out.println("actualizar d.getId_usuario: " + d.getUser());
        System.out.println("actualizar d.getId_usuario: " + d.getClave());

        int comit = 0;
        String sql = "UPDATE usuario SET "
                + "WHERE id_usuario=?, "
                + "user=? "
                + " clave=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);

            ps.setInt(++i, d.getId_usuario());
            ps.setString(++i, d.getUser());
            ps.setString(++i, d.getClave());
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int delete(String id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM usuario WHERE id_usuario= ?";
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
    public List<UsuarioTO> listCmb(String filter) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List listarUsuario() {
        List<UsuarioTO> listarUsuario = new ArrayList();
        String sql = "SELECT * FROM usuario";
        try {
            connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                UsuarioTO use = new UsuarioTO();
                use.setId_usuario(rs.getInt("id_usuario"));
                use.setUser(rs.getString("user"));
                use.setClave(rs.getString("clave"));
                listarUsuario.add(use);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarUsuario;
    }

    @Override
    public UsuarioTO buscarUsuario(String usuario) {
        UsuarioTO usern = new UsuarioTO();
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        try {
            //connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            String id_usuario = null;
            ps.setString(1, id_usuario);
            rs = ps.executeQuery();
            if (rs.next()) {

                usern.setId_usuario(rs.getInt("id_usuario"));
                usern.setUser(rs.getString("user"));
                usern.setClave(rs.getString("clave"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return usern;
    }

    @Override
    public void reportarUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}


