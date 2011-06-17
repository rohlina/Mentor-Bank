package ru.mentorbank.backoffice.dao.stub;

import java.util.HashSet;
import java.util.Set;

import ru.mentorbank.backoffice.dao.OperationDao;
import ru.mentorbank.backoffice.dao.exception.OperationDaoException;
import ru.mentorbank.backoffice.model.Operation;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;

public class OperationDaoStub implements OperationDao {
	public static final String GOOD_ACCOUNT = "111111";
	public static final String BAD_ACCOUNT = "222222";
	public static final String SAVE_ERROR_MESSAGE = "Операция не может быть сохранена";

	private Set<Operation> operations = new HashSet<Operation>();

	@Override
	public void saveOperation(Operation operation) throws OperationDaoException {
		if (operation.getDstAccount() != null
				&& BAD_ACCOUNT.equals(operation.getDstAccount()
						.getAccountNumber())) {
			throw new OperationDaoException(SAVE_ERROR_MESSAGE);
		}
		operations.add(operation);
	}

	@Override
	public Set<Operation> getOperations() throws OperationDaoException {
		Operation operation = new Operation();
		StopListInfo dstStoplistInfo = new StopListInfo();
		dstStoplistInfo.setStatus(StopListStatus.ASKSECURITY);
		operation.setDstStoplistInfo(dstStoplistInfo);
		saveOperation(operation);
		return operations;
	}

}
