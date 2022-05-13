package es.uniovi.pulso.colmena.model;

/**
 * Class that represents one error appeared in the system
 * 
 * @author Carlos Fernandez-Medina, PULSO Research Team, University of Oviedo
 * 
 */
public class ColmenaError {
	// The id, type and templateName
	private String id;
	private String name;
	private String firstFamily;
	private String secondFamily;
	private int familyComplement;
	private String problemReason;
	private int idProblemReason;
	private String message;
	
	
	public ColmenaError(String id, String name, String firstFamily,
			String secondFamily, int familyComplement, String problemReason,
			int idProblemReason, String message) {
		this.id = id;
		this.name = name;
		this.firstFamily = firstFamily;
		this.secondFamily = secondFamily;
		this.familyComplement = familyComplement;
		this.problemReason = problemReason;
		this.idProblemReason = idProblemReason;
		this.message = message;
	}


	public String getId() {
		return id;
	}


	public String getName() {
		return name;
	}


	public String getFirstFamily() {
		return firstFamily;
	}


	public String getSecondFamily() {
		return secondFamily;
	}


	public int getFamilyComplement() {
		return familyComplement;
	}


	public String getProblemReason() {
		return problemReason;
	}


	public int getIdProblemReason() {
		return idProblemReason;
	}


	public String getMessage() {
		return message;
	}

	


}
