package br.edu.utfpr.dv.siacoes.dao;

import br.edu.utfpr.dv.siacoes.model.Department;

import java.sql.*;
import java.util.List;


public abstract class TemplateMethod  {

  final void  actions() throws SQLException {
        closeConn(Connection conn, PreparedStatement stmt, ResultSet rs);
        findById(int id);
        listAll(boolean onlyActive);
        save(int idUser, Department department);
        loadObject(ResultSet rs);
  }

   final void closeConn(Connection conn, PreparedStatement stmt, ResultSet rs) throws SQLException{
        if((rs != null) && !rs.isClosed())
            rs.close();
        if((stmt != null) && !stmt.isClosed())
            stmt.close();
        if((conn != null) && !conn.isClosed())
            conn.close();

  }

  abstract Department findById(int id, String dbSYntax) throws SQLException;

  abstract List<Department> listAll(boolean onlyActive) throws SQLException;

  abstract List<Department> listByCampus(int idCampus, boolean onlyActive) throws SQLException;

  abstract int save(int idUser, Department department) throws SQLException;

  abstract Department loadObject(ResultSet rs) throws SQLException;
  
}