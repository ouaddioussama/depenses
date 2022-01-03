package com.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.springframework.stereotype.Service;

import com.Enum.TypeDepense;
import com.dao.implement.DepenseDao;
import com.entities.Depense;
import com.github.royken.converter.FrenchNumberToWords;

import lombok.Data;

@ManagedBean(name = "depenseService")
@ViewScoped
@Service
@Data
public class DepenseService extends ObjectService<Depense> implements Serializable {

	List<Depense> listdepenseCaisse = new ArrayList<>();
	List<Depense> listdepenseGarde = new ArrayList<>();

	List<Depense> listdepenseCaisseFiltered = new ArrayList<>();
	List<Depense> listdepenseGardeFiltered = new ArrayList<>();

	Integer year;
	Depense objectToSelectCaisse = new Depense();
	Depense objectToSelectGarde = new Depense();
	private List<Depense> selectedDepensesCaisse;
	private List<Depense> selectedDepensesGarde;
 
	@PostConstruct
	public void init() {
		daoObject = new DepenseDao();
		objectToInsert = new Depense();
		
		initList();

		initYear();

	}

	public void calcmontantLettre() {

		if (objectToInsert == null) {
			System.out.println("null");
		}

		double number = objectToInsert.getMontant();
		int entier = (int) Math.floor(number);
		int decimal = (int) Math.floor((number - entier) * 100.0f);
		String resultat = FrenchNumberToWords.convert(entier) + " DIRHAM ";
		resultat += decimal > 0 ? " ET " + FrenchNumberToWords.convert(decimal) + " Centime" : "";
		// String resultat = FrenchNumberToWords.convert(entier) ;

		objectToInsert.setMontant_lettre(resultat.toUpperCase());

	}

	public void create(TypeDepense  typeDepense) throws Exception {
		System.out.println("inside create");
           
		try {			
			
			objectToInsert.setTypedepense(typeDepense);

			
			if(TypeDepense.CAISSE.equals(typeDepense)) {
				objectToInsert.setReference(listdepenseCaisse.size()+1+"/"+this.year);
				listdepenseCaisse.add(objectToInsert);
				listdepenseCaisseFiltered.add(objectToInsert);
				
			}else if(TypeDepense.GARDE.equals(typeDepense)) {
				objectToInsert.setReference(listdepenseGarde.size()+1+"/"+this.year);
				listdepenseGarde.add(objectToInsert);
				listdepenseGardeFiltered.add(objectToInsert);
			}

			daoObject.createInstance(objectToInsert);
			listObjects.add(objectToInsert);

			Help.msg = "insere avec Succes";			
			objectToInsert=new Depense();
			
		} catch (Exception e) {
			Help.error_msg2 = "Error !";
			throw new Exception("objectToInsert can not be null");
		}
		 
		
	}

	public void delete(Depense c) throws Exception {

		try {
			
			daoObject.deleteInstance(c);
			listObjects.remove(c);
			listObjects_filtered.remove(c);
	
			if(TypeDepense.CAISSE.equals(c.getTypedepense())) {
				listdepenseCaisse.remove(c);
				listdepenseCaisseFiltered.remove(c);
			}else if(TypeDepense.GARDE.equals(c.getTypedepense())) {
				listdepenseGarde.remove(c);
				listdepenseGardeFiltered.remove(c);
			}
 
			Help.msg = "supprime avec Succes";
			

		} catch (Exception e) {
			Help.error_msg2 = "Error !";
			e.printStackTrace();
		}

	}
	
	public void onRowEdit(RowEditEvent event) throws IOException {

		Depense editedModele = (Depense) event.getObject();
		try {
			daoObject.updateIstance(editedModele);
			Help.msg = "mise e jour faite avec Succes";
			

		} catch (Exception e) {
			Help.error_msg2 = "Error !";
			e.printStackTrace();
		}

	}
	
	
	public void createDepenseCaisse() throws Exception {
		create(TypeDepense.CAISSE);
	}

	public void createDepenseGarde() throws Exception {
		create(TypeDepense.GARDE);
	}
	
	private void initYear() {
		this.year = Calendar.getInstance().get(Calendar.YEAR);

	}
	
	public void onSelect(SelectEvent event) throws IOException {
		System.out.println("insideOnselect");

		Depense editedModele = (Depense) event.getObject();


		System.out.println("+++" + objectToSelectGarde);
		System.out.println("+++" + editedModele);

		if (objectToSelectGarde != null) {
			System.out.println("+++" + objectSelected);
			// System.out.println("+---+"+objectSelected.getProduit().getDesignation_prod());
 
		}

	}
	
	public void initList() {
		listObjects = new ArrayList<>(daoObject.findAll());
		listObjects_filtered = new ArrayList<>(daoObject.findAll());

		listdepenseCaisse = listObjects.stream().filter(x->TypeDepense.CAISSE.equals(x.getTypedepense())).collect(Collectors.toList());
		listdepenseGarde = listObjects.stream().filter(x->TypeDepense.GARDE.equals(x.getTypedepense())).collect(Collectors.toList());

		listdepenseCaisseFiltered = listObjects.stream().filter(x->TypeDepense.CAISSE.equals(x.getTypedepense())).collect(Collectors.toList());
		listdepenseGardeFiltered = listObjects.stream().filter(x->TypeDepense.GARDE.equals(x.getTypedepense())).collect(Collectors.toList());
	}

}
