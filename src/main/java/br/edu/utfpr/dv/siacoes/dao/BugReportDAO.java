package br.edu.utfpr.dv.siacoes.dao;

import br.edu.utfpr.dv.siacoes.model.*;
import br.edu.utfpr.dv.siacoes.model.BugReport.BugStatus;
import br.edu.utfpr.dv.siacoes.model.Module;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BugReportDAO extends TemplateMethod{
	@Override
	BugReport findByIdBug(int id, String dbSYntax) throws SQLException {
		try(Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null){
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.prepareStatement("SELECT bugreport.*, \"user\".name " +
					"FROM bugreport INNER JOIN \"user\" ON \"user\".idUser=bugreport.idUser " +
					"WHERE idBugReport = ?");

			stmt.setInt(1, id);

			rs = stmt.executeQuery();

			if(rs.next()){
				return this.loadObject(rs);
			}else{
				return null;
			}
		}finally{
			closeConn(conn,stmt,rs);
		}
	}

	@Override
	List<BugReport> listAllBug(boolean onlyActive) throws SQLException {
		try(Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null){
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = (PreparedStatement) conn.createStatement();

			rs = stmt.executeQuery("SELECT bugreport.*, \"user\".name " +
					"FROM bugreport INNER JOIN \"user\" ON \"user\".idUser=bugreport.idUser " +
					"ORDER BY status, reportdate");
			List<BugReport> list = new ArrayList<BugReport>();

			while(rs.next()){
				list.add(this.loadObject(rs));
			}

			return list;
		}finally{
			closeConn(conn,stmt,rs);
		}
	}

	@Override
	int save(int idUser, Department department) throws SQLException {
		boolean insert = (bug.getIdBugReport() == 0);
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try(Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null; boolean insert = (bug.getIdBugReport() == 0)){
			conn = ConnectionDAO.getInstance().getConnection();

			if(insert){
				stmt = conn.prepareStatement("INSERT INTO bugreport(idUser, module, title, description, reportDate, type, status, statusDate, statusDescription) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			}else{
				stmt = conn.prepareStatement("UPDATE bugreport SET idUser=?, module=?, title=?, description=?, reportDate=?, type=?, status=?, statusDate=?, statusDescription=? WHERE idBugReport=?");
			}

			stmt.setInt(1, bug.getUser().getIdUser());
			stmt.setInt(2, bug.getModule().getValue());
			stmt.setString(3, bug.getTitle());
			stmt.setString(4, bug.getDescription());
			stmt.setDate(5, new java.sql.Date(bug.getReportDate().getTime()));
			stmt.setInt(6, bug.getType().getValue());
			stmt.setInt(7, bug.getStatus().getValue());
			if(bug.getStatus() == BugStatus.REPORTED){
				stmt.setNull(8, Types.DATE);
			}else{
				stmt.setDate(8, new java.sql.Date(bug.getStatusDate().getTime()));
			}
			stmt.setString(9, bug.getStatusDescription());

			if(!insert){
				stmt.setInt(10, bug.getIdBugReport());
			}

			stmt.execute();

			if(insert){
				rs = stmt.getGeneratedKeys();

				if(rs.next()){
					bug.setIdBugReport(rs.getInt(1));
				}
			}

			return bug.getIdBugReport();
		}finally{
			closeConn(conn,stmt,rs);
		}
	}

	
	private BugReport loadObject(ResultSet rs) throws SQLException{
		BugReport bug = new BugReport();
		
		bug.setIdBugReport(rs.getInt("idBugReport"));
		bug.setUser(new User());
		bug.getUser().setIdUser(rs.getInt("idUser"));
		bug.getUser().setName(rs.getString("name"));
		bug.setModule(Module.SystemModule.valueOf(rs.getInt("module")));
		bug.setTitle(rs.getString("title"));
		bug.setDescription(rs.getString("description"));
		bug.setReportDate(rs.getDate("reportDate"));
		bug.setType(BugReport.BugType.valueOf(rs.getInt("type")));
		bug.setStatus(BugReport.BugStatus.valueOf(rs.getInt("status")));
		bug.setStatusDate(rs.getDate("statusDate"));
		bug.setStatusDescription(rs.getString("statusDescription"));
		
		return bug;
	}

}
