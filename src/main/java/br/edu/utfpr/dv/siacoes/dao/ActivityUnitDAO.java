package br.edu.utfpr.dv.siacoes.dao;

import br.edu.utfpr.dv.siacoes.log.UpdateEvent;
import br.edu.utfpr.dv.siacoes.model.ActivityUnit;
import br.edu.utfpr.dv.siacoes.model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ActivityUnitDAO extends TemplateMethod {

	@Override
	ActivityUnit findByIdActivity(int id, String dbSYntax) throws SQLException {
		try(Connection conn, Statement stmt, ResultSet rs ){
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.prepareStatement("SELECT * FROM activityunit WHERE idActivityUnit=?");

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
	List<ActivityUnit> listAllActivity(boolean onlyActive) throws SQLException {
		try(Connection conn = null; Statement stmt = null; ResultSet rs = null){
			conn = ConnectionDAO.getInstance().getConnection();
			stmt = conn.createStatement();

			rs = stmt.executeQuery("SELECT * FROM activityunit ORDER BY description");

			List<ActivityUnit> list = new ArrayList<ActivityUnit>();

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
		try(Connection conn = null; Statement stmt = null; ResultSet rs = null; boolean insert = (unit.getIdActivityUnit() == 0)){
			conn = ConnectionDAO.getInstance().getConnection();

			if(insert){
				stmt = conn.prepareStatement("INSERT INTO activityunit(description, fillAmount, amountDescription) VALUES(?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			}else{
				stmt = conn.prepareStatement("UPDATE activityunit SET description=?, fillAmount=?, amountDescription=? WHERE idActivityUnit=?");
			}

			stmt.setString(1, unit.getDescription());
			stmt.setInt(2, (unit.isFillAmount() ? 1 : 0));
			stmt.setString(3, unit.getAmountDescription());

			if(!insert){
				stmt.setInt(4, unit.getIdActivityUnit());
			}

			stmt.execute();

			if(insert){
				rs = stmt.getGeneratedKeys();

				if(rs.next()){
					unit.setIdActivityUnit(rs.getInt(1));
				}

				new UpdateEvent(conn).registerInsert(idUser, unit);
			} else {
				new UpdateEvent(conn).registerUpdate(idUser, unit);
			}

			return unit.getIdActivityUnit();
		}finally{
			closeConn(conn,stmt,rs);
		}
	}

	public ActivityUnit loadObject(ResultSet rs) throws SQLException{
		ActivityUnit unit = new ActivityUnit();
		
		unit.setIdActivityUnit(rs.getInt("idActivityUnit"));
		unit.setDescription(rs.getString("Description"));
		unit.setFillAmount(rs.getInt("fillAmount") == 1);
		unit.setAmountDescription(rs.getString("amountDescription"));
		
		return unit;
	}

}
