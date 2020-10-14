package application;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class Program2 {

	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		
		System.out.println("### TEST1: Department insert ###");
		Department dep = new Department(null, "Food");
		departmentDao.insert(dep);
		System.out.println(dep);
		
		System.out.println("\n### TEST2: Department findById ###");
        dep = departmentDao.findById(12);
        System.out.println(dep);
        
        System.out.println("\n### TEST3: Department update ###");
        dep = departmentDao.findById(13);
        dep.setName("drinks");
        departmentDao.update(dep);
        
        System.out.println("\n### TEST4: Department delete ###");
        departmentDao.deleteById(14);
        
        System.out.println("\n### TEST5: Department findAll ###");
        List<Department> departmentList = departmentDao.findAll();
        for (Department d : departmentList) {
        	System.out.println(d.toString());
        }        
        
	}

}
