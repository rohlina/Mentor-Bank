package ru.mentorbank.backoffice.services.stoplist;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ru.mentorbank.backoffice.model.stoplist.JuridicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.PhysicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;
import ru.mentorbank.backoffice.test.AbstractSpringTest;

public class StopListServiceStubTest extends AbstractSpringTest {

	@Autowired
	private StopListServiceStub stopListService;
	private JuridicalStopListRequest stopListRequest;
	private PhysicalStopListRequest physStopListRequest;

	@Before
	public void setUp() {
		stopListRequest = new JuridicalStopListRequest();
		physStopListRequest = new PhysicalStopListRequest();
	}

	@Test
	public void getJuridicalStopListInfo_OK() {
		// setup SUT
		stopListRequest.setInn(StopListServiceStub.INN_FOR_OK_STATUS);
		// Call SUT
		StopListInfo info = stopListService
				.getJuridicalStopListInfo(stopListRequest);
		// Validate SUT
		assertNotNull("���������� ������ ���� ���������", info);
		assertEquals(StopListStatus.OK, info.getStatus());
	}

	@Test
	public void getJuridicalStopListInfo_STOP() {
		stopListRequest.setInn(StopListServiceStub.INN_FOR_STOP_STATUS);
		StopListInfo info = stopListService
				.getJuridicalStopListInfo(stopListRequest);
		assertNotNull("���������� ������ ���� ���������", info);
		assertEquals(StopListStatus.STOP, info.getStatus());
	}

	@Test
	public void getPhysicalStopListInfo_OK() {
		physStopListRequest
				.setLastname(StopListServiceStub.LASTNAME_FOR_OK_STATUS);
		StopListInfo info = stopListService
				.getPhysicalStopListInfo(physStopListRequest);
		assertNotNull("���������� ������ ���� ���������", info);
		assertEquals(StopListStatus.OK, info.getStatus());
	}

	@Test
	public void getPhysicalStopListInfo_STOP() {
		physStopListRequest
				.setLastname(StopListServiceStub.LASTNAME_FOR_ASKSECURITY_STATUS);
		physStopListRequest
				.setDocumentSeries(StopListServiceStub.SERIES_FOR_STOP_STATUS);
		physStopListRequest
				.setDocumentNumber(StopListServiceStub.NUMBER_FOR_STOP_STATUS);
		StopListInfo info = stopListService
				.getPhysicalStopListInfo(physStopListRequest);
		assertNotNull("���������� ������ ���� ���������", info);
		assertEquals(StopListStatus.STOP, info.getStatus());
	}

	@Test
	public void getPhysicalStopListInfo_ASKSECURITY() {
		physStopListRequest
				.setLastname(StopListServiceStub.LASTNAME_FOR_ASKSECURITY_STATUS);
		StopListInfo info = stopListService
				.getPhysicalStopListInfo(physStopListRequest);
		assertNotNull("���������� ������ ���� ���������", info);
		assertEquals(StopListStatus.ASKSECURITY, info.getStatus());
	}
}
