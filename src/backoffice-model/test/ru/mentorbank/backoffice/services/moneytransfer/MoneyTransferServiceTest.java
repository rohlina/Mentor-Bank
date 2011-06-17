package ru.mentorbank.backoffice.services.moneytransfer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mentorbank.backoffice.dao.OperationDao;
import ru.mentorbank.backoffice.dao.exception.OperationDaoException;
import ru.mentorbank.backoffice.dao.stub.OperationDaoStub;
import ru.mentorbank.backoffice.model.Operation;
import ru.mentorbank.backoffice.model.stoplist.JuridicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.PhysicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;
import ru.mentorbank.backoffice.model.transfer.JuridicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.PhysicalAccountInfo;
import ru.mentorbank.backoffice.model.transfer.TransferRequest;
import ru.mentorbank.backoffice.services.accounts.AccountService;
import ru.mentorbank.backoffice.services.accounts.AccountServiceBean;
import ru.mentorbank.backoffice.services.moneytransfer.exceptions.TransferException;
import ru.mentorbank.backoffice.services.stoplist.StopListService;
import ru.mentorbank.backoffice.services.stoplist.StopListServiceStub;
import ru.mentorbank.backoffice.test.AbstractSpringTest;

public class MoneyTransferServiceTest extends AbstractSpringTest {

	@Autowired
	private MoneyTransferServiceBean moneyTransferService;

	private TransferRequest transferRequest;

	private JuridicalAccountInfo dstAccount;

	private StopListInfo stopListInfo;

	private PhysicalAccountInfo srcAccount;

	@Before
	public void setUp() {
		dstAccount = new JuridicalAccountInfo();
		dstAccount.setAccountNumber("1");
		dstAccount.setInn(StopListServiceStub.INN_FOR_OK_STATUS);
		srcAccount = new PhysicalAccountInfo();
		srcAccount.setAccountNumber("2");
		srcAccount.setLastname(StopListServiceStub.LASTNAME_FOR_OK_STATUS);

		stopListInfo = new StopListInfo();
		stopListInfo.setStatus(StopListStatus.OK);

		transferRequest = new TransferRequest();
		transferRequest.setDstAccount(dstAccount);
		transferRequest.setSrcAccount(srcAccount);
	}

	@Test
	public void transfer() throws TransferException, OperationDaoException {
		StopListService mockedStopListService = mock(StopListServiceStub.class);
		AccountService mockedAccountService = mock(AccountServiceBean.class);
		OperationDao mockedOperationDao = mock(OperationDaoStub.class);

		when(mockedAccountService.verifyBalance(dstAccount)).thenReturn(true);
		when(
				mockedStopListService
						.getJuridicalStopListInfo(any(JuridicalStopListRequest.class)))
				.thenReturn((StopListInfo) stopListInfo);
		when(
				mockedStopListService
						.getPhysicalStopListInfo(any(PhysicalStopListRequest.class)))
				.thenReturn((StopListInfo) stopListInfo);

		moneyTransferService.setAccountService(mockedAccountService);
		moneyTransferService.setOperationDao(mockedOperationDao);
		moneyTransferService.setStopListService(mockedStopListService);
		moneyTransferService.transfer(transferRequest);

		verify(mockedOperationDao).saveOperation(any(Operation.class));
		verify(mockedStopListService).getJuridicalStopListInfo(
				any(JuridicalStopListRequest.class));
		verify(mockedStopListService).getPhysicalStopListInfo(
				any(PhysicalStopListRequest.class));
		verify(mockedAccountService).verifyBalance(dstAccount);
	}

}
