package es.uniovi.pulso.colmena.model.util;

public class ErrorTuple {
	private String userId;
	private int session;
	private int totalErrors;

	public ErrorTuple(String userId, int session, int totalErrors) {
		super();
		this.userId = userId;
		this.session = session;
		this.totalErrors = totalErrors;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getSession() {
		return session;
	}

	public void setSession(int session) {
		this.session = session;
	}

	public int getTotalErrors() {
		return totalErrors;
	}

	public void setTotalErrors(int totalErrors) {
		this.totalErrors = totalErrors;
	}

}
