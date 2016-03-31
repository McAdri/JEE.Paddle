package business.api;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreInvocationAttribute;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.InvalidDateException;
import business.api.exceptions.InvalidTrainingException;
import business.api.exceptions.NotFoundCourtIdException;
import business.api.exceptions.NotFoundPlayerException;
import business.api.exceptions.NotFoundTrainingException;
import business.api.exceptions.TrainingNotAvailableException;
import business.controllers.CourtController;
import business.controllers.TrainingController;
import business.wrapper.TrainingWrapper;
import data.entities.User;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAININGS)
public class TrainingResource {

	
	private CourtController courtController;
	private TrainingController trainingController;
	
	@Autowired
	public void setCourtController(CourtController courtController){
		this.courtController = courtController;
	}
	
	@Autowired
	public void setTrainingController(TrainingController trainingController){
		this.trainingController = trainingController;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void createTraining(@AuthenticationPrincipal User activeUser, 
		@RequestBody TrainingWrapper wrapper) throws
	 	NotFoundCourtIdException, InvalidDateException, InvalidTrainingException {
		if (!courtController.exist(wrapper.getCourtId())) {
			throw new NotFoundCourtIdException();
		}
	 	if (!trainingController.isValidStartTime(wrapper.getStartTime())) {
	 		throw new InvalidDateException("Fecha no valida");
	 	}
	 	if (!trainingController.createTraining(activeUser.getUsername(), wrapper)) {
	 		throw new InvalidTrainingException("Reserva no posible");
		}
	 }
	
	private void validateTimes(Calendar startTime, Calendar endTime) throws InvalidDateException {
		Calendar today = Calendar.getInstance();
		if (endTime.compareTo(today) < 0) {
			throw new InvalidDateException("fecha erronea");
		}
		if (startTime.compareTo(today) < 0) {
			throw new InvalidDateException("fecha erronea");
			    	
		}
		if (startTime.compareTo(endTime) >= 0) {
			throw new InvalidDateException("fechas OK");
		}
		
	}
	
		 
		@RequestMapping(value = Uris.ID, method = RequestMethod.DELETE)
		public void deleteTraining(@PathVariable int id) throws NotFoundTrainingException {
			if (!trainingController.deleteTraining(id)) {
				throw new NotFoundTrainingException();
			}
			
			
		 
	@RequestMapping(value = Uris.ID + Uris.PLAYERS + Uris.PLAYER_ID, method = RequestMethod.DELETE)
	public void deletePlayerFromTraining(@PathVariable int id, @PathVariable int player_id) throws NotFoundTrainingException, NotFoundPlayerException
	{
		if (!trainingController.existsTraining(id)) {
			throw new NotFoundTrainingException();
		}   
		if (!trainingController.deletePlayerFromTraining(id, player_id)) {
			throw new NotFoundPlayerException();
		}
	}		
	
	@RequestMapping(method = RequestMethod.GET)
	public List<TrainingWrapper> showTrainings() {
		return trainingController.getAvailableTrainings();
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = Uris.ID + Uris.PLAYERS)
	public void addPlayerToTraining(@AuthenticationPrincipal User activeUser, @PathVariable int trainingId) throws NotFoundTrainingException, TrainingNotAvailableException {
		if (!trainingController.existsTraining(trainingId)) {
			throw new NotFoundTrainingException();
		}
		if (!trainingController.addPlayerToTraining(trainingId, activeUser.getUsername())) {
			throw new TrainingNotAvailableException();
		}
	}
	
}
