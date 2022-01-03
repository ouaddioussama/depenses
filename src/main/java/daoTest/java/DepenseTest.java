package daoTest.java;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.dao.implement.DepenseDao;
import com.entities.Depense;

public class DepenseTest {

	public static void main(String[] args) {
		
	
		t1();
	}

	static void t1() {
		DepenseDao sdao= new DepenseDao();
		Depense d =new Depense();
		d.setBeneficiare("oussa");
		
		sdao.createInstance(d);
        System.out.println(sdao.findAll().size());
         
	}
}
