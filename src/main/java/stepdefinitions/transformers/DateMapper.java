package stepdefinitions.transformers;

import java.util.Date;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Assert;

import cucumber.api.Transformer;

/**
 * Utility class for converting dates specified in feature files into Date objects as
 * parameters for the implementation functions.
 */
public class DateMapper extends Transformer<Date>{

	    @Override
	    public Date transform(String date) {

	        try {
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
				return LocalDate.parse(date, formatter).toDate();
			} catch (Exception e) {
				Assert.fail("Wrong date specified in feature file, date '" + date + "' does not match the 'yyyy-MM-dd' pattern: " + e.getMessage());
				return null; // keep compiler happy, never reached.
			} 
	        
	   }

	}
