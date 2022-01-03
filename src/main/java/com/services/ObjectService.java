package com.services;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.dao.interfaces.InterfDao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ManagedBean(name = "ObjectBean")
@ViewScoped
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectService<T> implements Serializable {

	private Class<T> classG;

	protected T objectToInsert;
	protected T objectSelected;
	protected InterfDao<T> daoObject;

	protected List<T> listObjects = new LinkedList<T>();
	protected List<T> listObjects_filtered = new ArrayList<T>();
	protected String indice_path = "";
	protected String path = "/views/" + indice_path + "/index";
	protected LazyDataModel<T> dataModel;
	protected List<String> listErrors=new ArrayList<>();
 

	@Autowired
	protected Help help;

  
 
 

	@PostConstruct
	public void init() {
         
	 

	}

	public void create() throws Exception {
		System.out.println("inside create");

		if (objectToInsert != null) { 
			daoObject.createInstance(objectToInsert);
			
			listObjects.add(objectToInsert);
			listObjects_filtered.add(objectToInsert);


			Help.msg = "insere avec Succes";
			
			objectToInsert=(T) objectToInsert.getClass().newInstance();
		} else {
			Help.error_msg2 = "Error !";
			throw new Exception("objectToInsert can not be null");
		}
	}

	public void update() throws Exception {
		daoObject.updateIstance(objectSelected);

	}

	public void delete(T objectSelected ) throws Exception {
		if (objectSelected != null) {
			daoObject.deleteInstance(objectSelected);
			listObjects.remove(objectSelected);
			Help.msg = "supprimé avec Succès";
			Help.goTo(path);

		} else {
			throw new Exception("objectSelected can not be null");
		}
	}

	public void onRowEdit(RowEditEvent event) throws IOException, Exception {

		T editedModele = (T) event.getObject();

		if (editedModele != null) {
			daoObject.updateIstance(editedModele);
			RequestContext requestContext = RequestContext.getCurrentInstance();
			Help.msg = "mise à jour faite avec Succès";
			// Help.goTo(path);

		} else {
			System.out.println("objectToInsert is null !");
		}

	}

	public void callMe() {
		System.out.println("callMe");
	}
	/*
	 * public void onRowSelect(SelectEvent event) throws IOException {
	 * 
	 * Client editedModele = (Client) event.getObject();
	 * Help.msg="Client : "+editedModele.getNom_client()+"selectionée";
	 * anotherDisplay(); System.out.println(editedModele.getNom_client());
	 * 
	 * 
	 * 
	 * }
	 */

	public void onRowSelect(SelectEvent event) throws IOException {

		T editedModele = (T) event.getObject();
		System.out.println(editedModele.toString());

	}

	public void resetListObjects() {
		if (listObjects.isEmpty()) {
			listObjects = new ArrayList<>();
			listObjects_filtered = new ArrayList<>();
		}
	}

	/**
	 * @return the dataModel
	 */
	public LazyDataModel<T> getDataModel() {
		return dataModel;
	}

	/**
	 * @param dataModel the dataModel to set
	 */
	public void setDataModel(LazyDataModel<T> dataModel) {
		this.dataModel = dataModel;
	}

}
