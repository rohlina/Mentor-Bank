package ru.mentorbank.backoffice.services.stoplist;

import ru.mentorbank.backoffice.model.stoplist.JuridicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.PhysicalStopListRequest;
import ru.mentorbank.backoffice.model.stoplist.StopListInfo;
import ru.mentorbank.backoffice.model.stoplist.StopListStatus;

public class StopListServiceStub implements StopListService {

	public static final String INN_FOR_OK_STATUS = "1111111111111";
	public static final String INN_FOR_STOP_STATUS = "22222222222222";
	public static final String INN_FOR_ASKSECURITY_STATUS = "33333333333333";

	public static final String LASTNAME_FOR_OK_STATUS = "Петров";
	public static final String LASTNAME_FOR_ASKSECURITY_STATUS = "Иванов";
	public static final String SERIES_FOR_STOP_STATUS = "0000";
	public static final String NUMBER_FOR_STOP_STATUS = "000000";

	@Override
	public StopListInfo getJuridicalStopListInfo(
			JuridicalStopListRequest request) {
		StopListInfo stopListInfo = new StopListInfo();
		stopListInfo.setComment("Комментарий");
		if (INN_FOR_OK_STATUS.equals(request.getInn())) {
			stopListInfo.setStatus(StopListStatus.OK);
		} else if (INN_FOR_STOP_STATUS.equals(request.getInn())) {
			stopListInfo.setStatus(StopListStatus.STOP);
		} else {
			stopListInfo.setStatus(StopListStatus.ASKSECURITY);
		}
		return stopListInfo;
	}

	@Override
	public StopListInfo getPhysicalStopListInfo(PhysicalStopListRequest request) {
		StopListInfo stopListInfo = new StopListInfo();
		stopListInfo.setComment("Комментарий");
		if (LASTNAME_FOR_ASKSECURITY_STATUS.equals(request.getLastname())) {
			if (SERIES_FOR_STOP_STATUS.equals(request.getDocumentSeries())
					&& NUMBER_FOR_STOP_STATUS.equals(request
							.getDocumentNumber())) {
				stopListInfo.setStatus(StopListStatus.STOP);
			} else {
				stopListInfo.setStatus(StopListStatus.ASKSECURITY);
			}

		} else {
			stopListInfo.setStatus(StopListStatus.OK);
		}
		return stopListInfo;
	}

}
