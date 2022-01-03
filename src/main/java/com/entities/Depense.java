package com.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import com.Enum.TypeDepense;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Depense {
	
	@Id
    @GeneratedValue (strategy=GenerationType.AUTO)   
	private Integer id;
	private String reference;
	private String beneficiare;
	private String motif;
	private String pieces_jointe;
	private double montant;
	private String montant_lettre;
	private Date dateOperation=new Date();
	
	@Enumerated(EnumType.STRING)
	private TypeDepense typedepense=TypeDepense.CAISSE;
	
}
