package com.services;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.entities.Depense;
import com.entities.Employee;

import lombok.Data;

 

@ManagedBean(name = "loginService")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Service
@Data
public class LoginService {

	@Autowired
	private Employee EmployeetoLog;
	private Employee employeelogged;
	private String log_out_path = "/login";
	private boolean Logged = false;

 
	@Autowired
	protected Help help;

	public LoginService() {
	};

	public LoginService(Employee EmployeetoLog) {
		super();
		this.EmployeetoLog = EmployeetoLog;
	}

 

	public void login2() throws IOException {
		 
	 
		if (EmployeetoLog == null) {
			Help.error_msg = "Employee nom ou mot de passe incorrecte !";
			Help.error();
 		}


		// bloc admin global

		else if (EmployeetoLog.getNom().equals("ayoub") && EmployeetoLog.getPass_word().equals("12345")) {
			this.Logged = true;
			EmployeetoLog = new Employee();
			EmployeetoLog.setNom("Admin");
			accessOk(EmployeetoLog);
		
		} else {

			Help.error_msg = "veuillez contacter l'administrateur !";
			Help.error();
		}

	}

	public void accessOk(Employee EmployeetoLog) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedEmployee", EmployeetoLog);

		Help.msg = "Bienvenue Admin";
		help.goRefresh("/views/index");
	}


	@PostConstruct
	public void init() {
		EmployeetoLog = new Employee();

	}

	public String logOut() throws IOException {
		this.Logged = false;
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.invalidateSession();
		return "/login.xhtml?faces-redirect=true";
		// ec.redirect(ec.getRequestContextPath() + "/login.xhtml?faces-redirect=true");
		// FacesContext.getCurrentInstance().getExternalContext().invalidateSession().;
		// return log_out_path;
	}

	/**
	 * return true si la date d'ajourdhui est inferieur à la date de fin de licence
	 * logiciel
	 **/



	public Date addDays(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); // minus number would decrement the days
		return cal.getTime();
	}

}
