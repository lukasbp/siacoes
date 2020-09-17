package br.edu.utfpr.dv.siacoes.dao;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class TemplateMethod {

  public abstract void actions(){
        closeConn(Connection conn, PreparedStatement stmt, ResultSet rs);
        findById(int id);
        listAll(boolean onlyActive);
        save(int idUser, Department department);
        loadObject(ResultSet rs);
  }
    
  public abstract void closeConn(Connection conn, PreparedStatement stmt, ResultSet rs){
        if((rs != null) && !rs.isClosed())
            rs.close();
        if((stmt != null) && !stmt.isClosed())
            stmt.close();
        if((conn != null) && !conn.isClosed())
            conn.close();
  }

  public abstract Department findById(int id, String dbSYntax) throws SQLException;

  public  abstract List<Department> listAll(boolean onlyActive) throws SQLException;

  public abstract List<Department> listByCampus(int idCampus, boolean onlyActive) throws SQLException;

  public abstract int save(int idUser, Department department) throws SQLException;

  private abstract loadObject(ResultSet rs) throws SQLException;
  
}
