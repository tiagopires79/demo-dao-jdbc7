package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {		
		//Department department = new Department(1, "Books");		
		//Seller seller = new Seller(21, "Bob", "bob@gmail.com", new Date(), 3000.0, department);
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		System.out.println("### TEST1: Seller findById ###");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);

	}

}
