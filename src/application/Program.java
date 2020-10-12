package application;

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
		List<Seller> listSeller = sellerDao.findByDepartment(new Department(2, null));
		for(Seller obj : listSeller) {
			 System.out.println(obj);
		}
		
	}

}
