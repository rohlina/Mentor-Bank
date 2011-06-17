package ru.mentorbank.backoffice.dao.stub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mentorbank.backoffice.dao.OperationDao;
import ru.mentorbank.backoffice.dao.exception.OperationDaoException;
import ru.mentorbank.backoffice.model.Account;
import ru.mentorbank.backoffice.model.Operation;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;
import ru.mentorbank.backoffice.test.AbstractSpringTest;

public class OperationDaoStubTest extends AbstractSpringTest {

	@Autowired
	public OperationDao operationDao;

	@Test
	public void getOperations() throws OperationDaoException {
		Set<Operation> operaions = operationDao.getOperations();
		assertOperationsAreInAskSequrityStatus(operaions);
	}

	private void assertOperationsAreInAskSequrityStatus(Set<Operation> operaions) {
		assertNotNull(operaions);
		for (Operation operation : operaions) {
			if (StopListStatus.ASKSECURITY != operation.getDstStoplistInfo()
					.getStatus()) {
				fail("Статус операций должен быть ASKSECURITY");
			}
		}
	}

	@Test
	public void saveOperation_save() throws OperationDaoException {
		Account acc = new Account();
		acc.setAccountNumber(OperationDaoStub.GOOD_ACCOUNT);
		Operation newOperation = new Operation();
		newOperation.setDstAccount(acc);
		operationDao.saveOperation(newOperation);
		Set<Operation> operaions = operationDao.getOperations();
		assertOperationSave(operaions);
	}

	private void assertOperationSave(Set<Operation> operaions) {
		assertNotNull(operaions);
		int counter = 0;
		for (Operation operation : operaions) {
			if (operation.getDstAccount() != null
					&& OperationDaoStub.GOOD_ACCOUNT.equals(operation
							.getDstAccount().getAccountNumber())) {
				counter++;
			}
		}
		if (counter == 0) {
			fail("Операция не сохранилась!");
		}
	}

	@Test
	public void saveOperation_fail() throws OperationDaoException {
		Account acc = new Account();
		acc.setAccountNumber(OperationDaoStub.BAD_ACCOUNT);
		Operation newOperation = new Operation();
		newOperation.setDstAccount(acc);
		try {
			operationDao.saveOperation(newOperation);
		} catch (OperationDaoException e) {
			assertEquals(OperationDaoStub.SAVE_ERROR_MESSAGE, e.getMessage());
		} finally {
			Set<Operation> operaions = operationDao.getOperations();
			assertOperationNotSave(operaions);
		}
	}

	private void assertOperationNotSave(Set<Operation> operaions) {
		assertNotNull(operaions);
		int counter = 0;
		for (Operation operation : operaions) {
			if (operation.getDstAccount() != null
					&& OperationDaoStub.BAD_ACCOUNT.equals(operation
							.getDstAccount().getAccountNumber())) {
				counter++;
			}
		}
		if (counter != 0) {
			fail("Oперация сохранилась - это неправильно!");
		}
	}
}
