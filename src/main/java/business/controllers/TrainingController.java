package business.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import business.wrapper.TrainingWrapper;
import data.daos.CourtDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Court;
import data.entities.Training;
import data.entities.User;

public class TrainingController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CourtDao courtDao;
	
	@Autowired
	private ReserveController reserveController;
	
	@Autowired
	private TrainingDao trainingDao;

	private void notBusyCourt(int courtId, Calendar startTime, Calendar endTime, String coachUsername) {
		for (Calendar datetime = startTime; datetime.compareTo(endTime) >= 0; datetime.add(Calendar.WEEK_OF_YEAR, 1)) {
			reserveController.notBusyCourt(courtId, datetime, coachUsername);
		}
	}
	
	public boolean isValidStartTime(Calendar startTime) {
		return false;
	}
	
	private boolean isAvailableCourt(int courtId, Calendar startDate, Calendar endDate) {
		for (Calendar datetime = startDate; datetime.compareTo(endDate) >= 0; datetime.add(Calendar.WEEK_OF_YEAR, 1)) {
			if (!reserveController.isAvailable(courtId, datetime)) {
				return false;
			}
		}
		return true;
	}
	
	public TrainingWrapper createTraining(String coachUsername, TrainingWrapper wrapper) {
		if (isAvailableCourt(wrapper.getCourtId(), wrapper.getStartTime(), wrapper.getEndTime())) {
			reserveCourt(wrapper.getCourtId(), wrapper.getStartTime(), wrapper.getEndTime(), coachUsername);		
		} else {
			return null;
		}
		User coach = userDao.findByUsernameOrEmail(coachUsername);
		Court court = courtDao.findById(wrapper.getCourtId());
		Training training = new Training(wrapper.getStartTime(), wrapper.getEndTime(), court, coach);
		trainingDao.save(training);
		return new TrainingWrapper(training);
	}

	private void reserveCourt(int courtId, Calendar startTime, Calendar endTime, String coachUsername) {
		// TODO Auto-generated method stub
		for (Calendar datetime = startTime; datetime.compareTo(endTime) >= 0; datetime.add(Calendar.WEEK_OF_YEAR, 1)) {
			reserveController.reserveCourt(courtId, datetime, coachUsername);
		}
	}

	public boolean deleteTraining(int id) {
		// TODO Auto-generated method stub
		if (!existsTraining(id)){
			return false;
		}
		Training training = trainingDao.findOne(id);
		notBusyCourt(training.getCourt().getId(), training.getStartTime(), training.getEndTime(), training.getCoach().getUsername());
		trainingDao.delete(id);
		return true;
	}

	public boolean existsTraining(int id) {
		return (trainingDao.exists(id));
	}

	public List<TrainingWrapper> getAvailableTrainings() {
		// TODO Auto-generated method stub
		List<Training> trainings = trainingDao.findAll();
		List<TrainingWrapper> availableTrainings = new ArrayList<TrainingWrapper>();
		
		for (Training training: trainings) {
			if (training.getStartTime().compareTo(Calendar.getInstance()) >= 0) {
				availableTrainings.add(new TrainingWrapper(training));
			}
		}
		return availableTrainings;
	}

	public boolean addPlayerToTraining(int trainingId, String username) {
		// TODO Auto-generated method stub
		Training training = trainingDao.findOne(trainingId);
		User player = userDao.findByUsernameOrEmail(username);
		if (!training.addPlayer(player)) {
			return false;
		}
		trainingDao.save(training);
		return true;
	}

}
