package api;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.TrainingCreateWrapper;
import business.wrapper.TrainingWrapper;
import business.wrapper.UserWrapper;

public class TrainingResourceFunctionalTesting {

	private RestService restService = new RestService();
	private int courtId = 10;
	private String coachToken;
	
	@Before
	public void before() {
		restService.deleteAll();
		restService.createCourt(courtId + "");
		coachToken = restService.registerAndLoginPlayer();
	}
	
	@Test
	public void testCreateTraining() {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		Calendar aMonthLater = (Calendar) tomorrow.clone();
		aMonthLater.add(Calendar.MONTH, 1);
		TrainingCreateWrapper wrapper = new TrainingCreateWrapper(courtId, tomorrow, aMonthLater);
		TrainingWrapper response = new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper.class).body(wrapper).post().build();
		try {
			wrapper.setStartTime(aMonthLater);
			wrapper.setEndTime(tomorrow);
			new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper.class).body(wrapper).post().build();
			fail();
		} catch (HttpClientErrorException httpError) {
			assertEquals(httpError.getStatusCode(), HttpStatus.BAD_REQUEST);
		}
		try {
			wrapper.setCourtId(-1);
			new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper.class).body(wrapper).post().build();
			fail();
		} catch (HttpClientErrorException httpError) {
			assertEquals(httpError.getStatusCode(), HttpStatus.NOT_FOUND);			
		}
	}
	
	
	@Test
	public void testDeleteTraining() {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		Calendar aMonthLater = (Calendar) tomorrow.clone();
		aMonthLater.add(Calendar.MONTH, 1);
		TrainingCreateWrapper wrapper = new TrainingCreateWrapper(courtId, tomorrow, aMonthLater);
	    TrainingWrapper response = new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper.class).body(wrapper).post().build();
	    new RestBuilder<>(RestService.URL).path(Uris.TRAININGS + "/" + response.getCourtId()).basicAuth(coachToken, "").delete().build();	
		try {
			new RestBuilder<>(RestService.URL).path(Uris.TRAININGS + "/999999").basicAuth(coachToken, "").delete().build();	
			fail();
		} catch (HttpClientErrorException httpError) {
			assertEquals(httpError.getStatusCode(), HttpStatus.NOT_FOUND);
			
		}
	}
	
	@Test
	public void testShowTrainings() {
	    List<TrainingWrapper> list = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper[].class).get().build());
	    
	    assertTrue(list.isEmpty());
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		Calendar aMonthLater = (Calendar) tomorrow.clone();
		aMonthLater.add(Calendar.MONTH, 1);
		TrainingCreateWrapper wrapper = new TrainingCreateWrapper(courtId, tomorrow, aMonthLater);
		new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper.class).body(wrapper).post().build();
		
		list = Arrays.asList(new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper[].class).get().build());
		assertEquals(list.size(), 1);
	}
	

	@Test
	public void testAddPupilToTraining() {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		Calendar aMonthLater = (Calendar) tomorrow.clone();
		aMonthLater.add(Calendar.MONTH, 1);
		TrainingCreateWrapper wrapper = new TrainingCreateWrapper(courtId, tomorrow, aMonthLater);
		TrainingWrapper response = new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper.class).body(wrapper).post().build();
		new RestBuilder<>(RestService.URL).path(Uris.TRAININGS + "/" + response.getCourtId() + Uris.PLAYERS).basicAuth(coachToken, "").post().build();
	}

	@Test
	public void testDeletePupilFromTraining() {
		Calendar tomorrow = Calendar.getInstance();
		tomorrow.add(Calendar.DAY_OF_MONTH, 1);
		Calendar aMonthLater = (Calendar) tomorrow.clone();
		aMonthLater.add(Calendar.MONTH, 1);
		TrainingCreateWrapper wrapper = new TrainingCreateWrapper(courtId, tomorrow, aMonthLater);

		TrainingWrapper response = new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(coachToken, "").clazz(TrainingWrapper.class).body(wrapper).post().build();		

		new RestBuilder<>(RestService.URL).path(Uris.TRAININGS + "/" + response.getCourtId() + Uris.PLAYERS).basicAuth(coachToken, "").post().build();

		List<UserWrapper> players = Arrays.asList(new RestBuilder<UserWrapper[]>(RestService.URL).path(Uris.TRAININGS + "/" + response.getCourtId() + Uris.PLAYERS).basicAuth(coachToken, "").clazz(UserWrapper[].class).get().build());
	    
		new RestBuilder<>(RestService.URL).path(Uris.TRAININGS + "/" + response.getCourtId() + Uris.PLAYERS + "/" + players.get(0).getId()).basicAuth(coachToken, "").delete().build();

	}
	
	@After
	public void deleteAll() {
		new RestService().deleteAll();
	}
}
