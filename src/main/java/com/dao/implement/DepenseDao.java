package com.dao.implement;

import org.springframework.stereotype.Repository;

import com.dao.interfaces.InterfDepenseDao;
import com.entities.Depense;


@Repository
public class DepenseDao extends GenericDao<Depense> implements InterfDepenseDao{
	
	public DepenseDao() 
	{
		super(Depense.class);
	}
	
}

	

