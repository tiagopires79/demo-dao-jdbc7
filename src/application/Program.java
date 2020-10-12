package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {		
		//Department department = new Department(1, "Books");		
		//Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, department);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println("### TEST1: Seller findById ###");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("\n### TEST2: Seller findByDepartment ###");
		Department department = new Department(2, null);
		List<Seller> listSeller = sellerDao.findByDepartment(department);
		for(Seller obj : listSeller) {
			 System.out.println(obj);
		}
		
		System.out.println("\n### TEST3: Seller findAll ###");
		listSeller = sellerDao.findAll();
		for(Seller obj : listSeller) {
			 System.out.println(obj);
		}
		
		System.out.println("\n### TEST4: Seller insert ###");
		Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 5000.0, sellerDao.findDepartmentById(department.getId()));
		sellerDao.insert(newSeller);
		System.out.println("Inserted ! new id = " + newSeller.getId());
		System.out.println(newSeller);	
	
		
	}

}
