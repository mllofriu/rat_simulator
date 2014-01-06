import javax.vecmath.Vector3f;

/*
 * SimulationItem.java
 * Este modulo representa operacion y el intante a realizarla sobre el entorno
 * Autor: Gonzalo Tejera
 * Fecha: 12 de agosto de 2010
 */

public class SimulationOperation {
	public static final String REMOVE = "remove";
	public static final String ADD = "add";
	public static final String MOVE = "move";

	private String operation;
	private String primitiveName;
	private String trialApply;
	private String pointName;
	
	public SimulationOperation(String operation, String primitiveName, String trialApply) {
		super();
		this.operation = operation;
		this.trialApply = trialApply;
		this.primitiveName=primitiveName;
	}
	
	public SimulationOperation(String operation, String primitiveName, String trialApply,String pointName) {
		this(operation,primitiveName,trialApply);
		this.pointName = pointName;
	}

	public String getOperation() {
		return operation;
	}

	public String getTrialApply() {
		return trialApply;
	}

	public String getPrimitiveName() {
		return primitiveName;
	}
	
	public String getPointName() {
		return pointName;
	}

}
