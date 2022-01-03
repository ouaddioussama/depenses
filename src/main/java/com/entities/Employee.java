package com.entities;

import org.springframework.stereotype.Component;


@Component

public class Employee extends Person {
	
	private String pass_word;
	


	
	public Employee() {}
	


	public Employee(String pass_word ) {
		super();
		this.pass_word = pass_word;
	}



	public String getPass_word() {
		return pass_word;
	}



	public void setPass_word(String pass_word) {
		this.pass_word = pass_word;
	}

  
	
	
	

}
