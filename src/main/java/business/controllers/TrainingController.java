package business.controllers;

import java.util.Calendar;
import java.util.List;

import business.wrapper.TrainingWrapper;

public class TrainingController {
	
	

	public boolean isValidStartTime(Calendar startTime) {
		return false;
	}
	
	public boolean createTraining(String username, TrainingWrapper trainingWrapper) {
		return false;
	}

	public boolean deleteTraining(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean existsTraining(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<TrainingWrapper> getAvailableTrainings() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean addPlayerToTraining(int trainingId, String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
