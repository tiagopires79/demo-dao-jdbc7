package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Scanner sc = new Scanner(System.in);
		
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
		
		System.out.println("\n### TEST5: Seller update ###");
		seller = sellerDao.findById(1);
		System.out.println("Before update !");
		System.out.println(seller);
		seller.setName("Tiago Pires");
		seller.setEmail("tiago@gmail.com");
		sellerDao.update(seller);
		System.out.println("After update !");
		System.out.println(seller);
		
		System.out.println("\n### TEST6: Seller delete ###");
		System.out.printf("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Delete Completed !");
		
		System.out.println("\n### TEST6: Seller delete ###");
		System.out.println("\nEnter with interval id");
		System.out.printf("Enter id1: ");
		int id1 = sc.nextInt();
		System.out.printf("Enter id2: ");
		int id2 = sc.nextInt();
		sellerDao.deleteByIntervalId(id1, id2);
		System.out.println("Delete Interval Completed !");
		
		sc.close();
	}

}
