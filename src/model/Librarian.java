package model;
/* for future implementation */
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.scene.image.Image;

/**This class represents a librarian of the library. A librarian is allowed to create and edit a new resource,
 * loan copy to a user, process any return copies, and authorize a fine payment to a user.
 * it has all the attributes from a person class with the extends of employment date and staff number.
 * @author leezhinghang
 *@version 1.0
 */
public class Librarian extends Person{

	/** The employment date of the librarian.*/
	private String employmentDate;
	
	/**
	 * Create a new librarian user from the given arguments.
	 * @param userName
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param address
	 * @param postcode
	 * @param avatar
	 * @param employmentDate
	 * @param staffNumber
	 */
	public Librarian (String userName, String firstName, String lastName, String phoneNumber, String address, String postcode, Image avatar, String employmentDate) {
		super(userName, firstName, lastName, phoneNumber, address, postcode, avatar);
		this.employmentDate = employmentDate;
	}
	
	/**
	 * Make a new resource
	 * @param resource Details of the resources
	 */
	public void createNewResources (Resource resource) {
		//Needs Clarification
	}
	
	/**
	 * make changes to the resource
	 * @param resource Things that needs to be change on the resource
	 */
	public void editResources (Resource resource) {
		//Needs Clarification
	}
	
	/**
	 * Loans a copy to the user
	 * @param copy Copy that a user has loaned
	 */
	public void loanCopy (Copy copy, User user) {
		copy.getResource().loanToUser(user);
	}
	
	/**
	 * handles the process of the return copies from the user
	 * @param user The user that returns the copy
	 * @param copy The copy that has been borrowed by the user
	 */
	public void processReturn (User user, Copy copy) {
		copy.getResource().processReturn(copy);
	}
	
	/**
	 * 
	 * @param user User that needs to make the payment
	 */
	public void authorizeFinePayment (User user) {
		
	}
	
	/**
	 * Return the date when the librarian was hired.
	 * @return Employment date of the librarian.
	 */
	public String getEmploymentDate() {
		return this.employmentDate;
	}
}
