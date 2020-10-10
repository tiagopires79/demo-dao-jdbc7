package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import db.DB;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT se.*, dp.Name as DptoName "
			                           + "FROM coursejdbc.seller se, coursejdbc.department dp "
			                           + "WHERE se.DepartmentId = dp.Id "
			                           + "AND se.Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				Department dp = new Department(rs.getInt("DepartmentId"),rs.getString("DptoName"));			
				Seller se = new Seller(rs.getInt("Id"),rs.getString("Name"),rs.getString("Email"),rs.getDate("BirthDate"),rs.getDouble("BaseSalary"),dp);
				
				return se;
			}
			else return null;
		}
		catch(SQLException e) {
			throw new db.DbException(e.getMessage());
		}
		finally {
		    DB.closeStatement(ps);
		    DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}	

}
