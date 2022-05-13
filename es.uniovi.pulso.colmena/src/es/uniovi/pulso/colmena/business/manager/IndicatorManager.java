package es.uniovi.pulso.colmena.business.manager;

import java.util.ArrayList;
import java.util.List;

import es.uniovi.pulso.colmena.model.enumeration.IndicatorType;
import es.uniovi.pulso.colmena.model.indicators.ColmenaIndicator;

public class IndicatorManager {

	private List<ColmenaIndicator> indicatorList;
	private static IndicatorManager instance;
//	private DAOFactory factory;
//	private PluginManager pm;

	 private ColmenaIndicator mostCommonErrors;
	 private ColmenaIndicator mostCommonWarning;	
	 private ColmenaIndicator totalUserErrors;
	 private ColmenaIndicator totalUserWarnings;
	 private ColmenaIndicator competenceLevel;
	 
	 private ColmenaIndicator errorDifference;
	 private ColmenaIndicator warningDifference;


	private IndicatorManager() {
//		factory = new JdbcDAOFactory();
//		pm = PluginManager.getInstance();
		initilizeIndicators();
	}

	private void initilizeIndicators() {
		indicatorList = new ArrayList<ColmenaIndicator>();
		mostCommonErrors = new ColmenaIndicator("aMost common error");		
		mostCommonErrors.setType(IndicatorType.GENERAL_INFORMATION);
		mostCommonWarning = new ColmenaIndicator("bMost common warning");
		mostCommonWarning.setType(IndicatorType.GENERAL_INFORMATION);
		totalUserErrors = new ColmenaIndicator("cTotal user errors");
		totalUserErrors.setType(IndicatorType.GENERAL_INFORMATION);
		totalUserWarnings = new ColmenaIndicator("dTotal user warnings");
		totalUserWarnings.setType(IndicatorType.GENERAL_INFORMATION);
		competenceLevel = new ColmenaIndicator("eCompetence Level of user");		
		competenceLevel.setType(IndicatorType.GENERAL_INFORMATION);
		errorDifference = new ColmenaIndicator("fError difference");
		errorDifference.setType(IndicatorType.USER_AVERAGE_LESS);
		warningDifference = new ColmenaIndicator("gWarning difference");
		warningDifference.setType(IndicatorType.USER_AVERAGE_LESS);
		
	}

	public void loadIndicators() {
		indicatorList.clear();
		createUserIndicators();
		
		indicatorList.add(mostCommonErrors);
		indicatorList.add(mostCommonWarning);
		indicatorList.add(totalUserErrors);
		indicatorList.add(totalUserWarnings);
		indicatorList.add(competenceLevel);
		indicatorList.add(errorDifference);
		indicatorList.add(warningDifference);

	}

	private void createUserIndicators() {
		mostCommonErrors.setValue("% cannot be resolved to a type");
		mostCommonWarning.setValue("The import % is never used");
		totalUserErrors.setValue("15");
		totalUserWarnings.setValue("30");
		competenceLevel.setValue("Medium (some errors and some warnings)");
		errorDifference.setValue("-10");
		warningDifference.setValue("-20");

	}



	public List<ColmenaIndicator> getIndicatorList() {
		return indicatorList;
	}

	public static IndicatorManager getInstance() {
		if (instance == null) {
			instance = new IndicatorManager();
		}
		return instance;
	}
}
