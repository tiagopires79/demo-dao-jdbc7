package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
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
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("INSERT INTO seller " 
		                             + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					                 + "VALUES " 
		                             + "(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error ! No rows affected.");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void update(Seller obj) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(
					"UPDATE coursejdbc.seller " 
			       +"SET Name=?, Email=?, BirthDate=?, BaseSalary = ?, DepartmentId=? " 
				   +"WHERE Id = ?");

			ps.setString(1, obj.getName());
			ps.setString(2, obj.getEmail());
			ps.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			ps.setDouble(4, obj.getBaseSalary());
			ps.setInt(5, obj.getDepartment().getId());
			ps.setInt(6, obj.getId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
			ps.setInt(1, id);
						
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 0) {
				throw new DbException("No affected rows !");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
	}
	
	@Override
	public void deleteByIntervalId(Integer id1, Integer id2) {
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM seller "
					                   + "WHERE Id > ? "
					                   + "AND Id < ? ");
			ps.setInt(1, id1);
			ps.setInt(2, id2);
			//ps.executeUpdate();
			int rowsAffected = ps.executeUpdate();
			if (rowsAffected == 0) {
				throw new DbException("No affected rows !");
			}
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(ps);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					        "SELECT se.*, dp.Name as DptoName " 
			                + "FROM coursejdbc.seller se, coursejdbc.department dp "
							+ "WHERE se.DepartmentId = dp.Id "
			                + "AND se.Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				Department dp = instanteateDepartment(rs);
				Seller se = instanteateSeller(rs, dp);

				return se;
			} else
				return null;
		} catch (SQLException e) {
			throw new db.DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT se.*, dp.Name as DptoName "
					+ "FROM coursejdbc.seller se, coursejdbc.department dp " + "WHERE se.DepartmentId = dp.Id "
					// +"AND se.DepartmentId = ? "
					+ "ORDER BY Name");
			       // ps.setInt(1, department.getId());

			rs = ps.executeQuery();

			List<Seller> sellerList = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				// Verificar se departamento já foi instanciado. Caso já tenha nao instancia
				// novamente pq vai constar no map.
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instanteateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				// Lista de vendedores para apenas uma instancia de departamento
				sellerList.add(instanteateSeller(rs, dep));
			}
			return sellerList;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(
					"SELECT se.*, dp.Name as DptoName " + "FROM coursejdbc.seller se, coursejdbc.department dp "
							+ "WHERE se.DepartmentId = dp.Id " + "AND se.DepartmentId = ? " + "ORDER BY Name");
			ps.setInt(1, department.getId());

			rs = ps.executeQuery();

			List<Seller> sellerList = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>();

			while (rs.next()) {
				// Verificar se departamento já foi instanciado. Caso já tenha nao instancia
				// novamente pq vai constar no map.
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instanteateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				// Lista de vendedores para apenas uma instancia de departamento
				sellerList.add(instanteateSeller(rs, dep));
			}
			return sellerList;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public Department findDepartmentById(Integer id) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT * " + "FROM coursejdbc.department " + "WHERE Id = ?");
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.first();
			Department dp = instanteateDepartmentById(rs);
			return dp;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
		}
	}

	private Seller instanteateSeller(ResultSet rs, Department dp) throws SQLException {
		Seller se = new Seller(rs.getInt("Id"), rs.getString("Name"), rs.getString("Email"), rs.getDate("BirthDate"),
				rs.getDouble("BaseSalary"), dp);
		return se;
	}

	private Department instanteateDepartment(ResultSet rs) throws SQLException {
		Department dp = new Department(rs.getInt("DepartmentId"), rs.getString("DptoName"));
		return dp;
	}

	private Department instanteateDepartmentById(ResultSet rs) throws SQLException {
		Department dp = new Department(rs.getInt("Id"), rs.getString("Name"));
		return dp;
	}
	
}