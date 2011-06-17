package ru.mentorbank.backoffice.dao.exception;

public class OperationDaoException extends Exception {

	public String message;

	public OperationDaoException(String message) {
		this.message = message;
	}

	private static final long serialVersionUID = 1L;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
