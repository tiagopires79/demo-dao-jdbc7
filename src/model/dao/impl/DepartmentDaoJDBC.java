package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn = null;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO Department "
					                  +"(Name) "
					                  +"VALUES "
					                  +"(?)", Statement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, obj.getName());		
			int rowsInserted = ps.executeUpdate();			
			if (rowsInserted > 0) {
				ResultSet rs = ps.getGeneratedKeys();			
				if(rs.next()) {
				   obj.setId(rs.getInt(1));				   
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbException("Unexpected error ! No rows affected.");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}	
	}

	@Override
	public void update(Department obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("UPDATE Department "
					                  +"SET Name = ? "
					                  +"WHERE Id = ?");
			
			ps.setString(1, obj.getName());
			ps.setInt(2, obj.getId());
			
			int rowsUpdated = ps.executeUpdate();
			
			if (rowsUpdated > 0) {		
				System.out.println("Updated apply in " + rowsUpdated + " rows." + "Record " + obj.getId() + " updated.");
			}
			else {
				throw new DbException("Unexpected error ! No rows affected.");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE "
					                  +"FROM department "
					                  +"WHERE Id = ?");
			
			ps.setInt(1, id);
						
			int rowsUpdated = ps.executeUpdate();
			
			if (rowsUpdated > 0) {		
				System.out.println("Updated apply in " + rowsUpdated + " rows. Record " + id + " deleted.");
			}
			else {
				throw new DbException("Unexpected error ! No rows affected.");
			}
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * "
					                  +"FROM department "
					                  +"WHERE Id = ?");
			ps.setInt(1, id);
			
			rs = ps.executeQuery();
			Department department;
			if (rs.next()) {
				department = new Department(rs.getInt("Id"), rs.getString("Name"));				
			}
			else {
				throw new DbException("no records retrieved !");
			}			
			return department;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
		
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Department> departmentList = new ArrayList<>();
		try {
			ps = conn.prepareStatement("SELECT * "
					                  +"FROM department");
			rs = ps.executeQuery();
			while (rs.next()) {
				 departmentList.add(new Department(rs.getInt("Id"), rs.getString("Name")));				
			}
			return departmentList;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}	

}
