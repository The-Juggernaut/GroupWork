package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Book;
import model.Copy;
import model.DVD;
import model.Laptop;
import model.Librarian;
import model.Person;
import model.Resource;
import model.User;

public class ProfileController {

	@FXML
	private HBox resourceImages;

	@FXML
	private ScrollPane scrollPane;
	
	@FXML
	private VBox vResourceBox;
	
	@FXML
	private TextField searchTextBox;
	
	@FXML
	private Controller TextField;
	
	@FXML
	private Label userLabel;
	@FXML
	private Label fullnameLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label addressLabel;
	@FXML
	private Label postcodeLabel;
	@FXML
	private Label balanceLabel;
	
	@FXML
	private Label userLabel1;
	@FXML
	private Label fullnameLabel1;
	@FXML
	private Label phoneLabel1;
	@FXML
	private Label addressLabel1;
	@FXML
	private Label postcodeLabel1;
	@FXML
	private Label dateLabel1;
	@FXML
	private Label staffIDLabel1;
	
	@FXML
	private Tab userProfileTab;
	
	@FXML
	private Tab resourcesTab;
	
	@FXML
	private Tab transactionTab;
	
	@FXML
	private Tab staffProfileTab;
	
	@FXML
	private TabPane tabs;
	
	@FXML
	private Label accountBalance;
	
	@FXML
	private Button userEditProfileButton;
	
	@FXML
	private Button staffEditProfileButton;
	
	//check boxes
	@FXML
	private CheckBox dvdCheck;
	@FXML
	private CheckBox bookCheck;
	@FXML
	private CheckBox laptopCheck;
	
	//Copies Explorer
	@FXML
	private Button staffOverdueFilter;
	@FXML
	private Button staffRequestedFilter;
	@FXML
	private Button staffHistoryFind;
	@FXML
	private TextField staffCopyIDField;
	@FXML
	private TableView staffCopiesExplorerTable;
	
	//may remove fixed size resource images
	//when dealing with window resizing.
	private final int RES_IMG_WIDTH = 150;
	private final int RES_IMG_HEIGHT = 250;
	
	private final int COPY_IMG_WIDTH = 195;
	private final int COPY_IMG_HEIGHT = 325;
	
	private Person currentUser;
	private ArrayList<Resource> resources;

	/**
	 * Sets new scene on stage within program using fxml file provided.
	 * @param sceneFXML
	 */
	public void changeScene(MouseEvent event, String sceneFXML) {
		try {
			//create new scene object
			Parent root = FXMLLoader.load(getClass().getResource(sceneFXML));
			Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			stage.getScene().setRoot(root);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@FXML
	private void searchBarSwitch (MouseEvent event) {
		//tabPane.getSelectionModel().select(2);
	}
	
	@FXML  
    void searchThis(KeyEvent event) {
		tabs.getSelectionModel().select(resourcesTab);
		vResourceBox.getChildren().clear();
		HBox hbox = new HBox();
		hbox.setSpacing(5);
		vResourceBox.getChildren().add(hbox);
		loadResourceImages();
	}
	

	/**
	 * Called when logout link is clicked.
	 * @param event Passed when mouse event occurs
	 */
	@FXML
	public void logoutAction(MouseEvent event) {
		changeScene(event, "/fxml/loginScene.fxml");
	}
	
	/**
	 * Loads user information from Screen Manager class, so that
	 * it can be displayed within the UI.
	 */
	private void loadUserInformation() {
		if (ScreenManager.getCurrentUser() instanceof User) {
			//get all information in about user from ScreenManager class.
			String username = currentUser.getUsername();
			String fullname = currentUser.getFirstName() + " " + currentUser.getLastName();
			String address = currentUser.getAddress();
			String postcode = currentUser.getPostcode();
			String phoneNumber = currentUser.getPhoneNumber();
			
			//change text in labels to appropriate user information.
			userLabel.setText(username);
			fullnameLabel.setText(fullnameLabel.getText() + " " + fullname);
			addressLabel.setText(addressLabel.getText() + " " + address);
			postcodeLabel.setText(postcodeLabel.getText() + " " + postcode);
			phoneLabel.setText(phoneLabel.getText() + " " + phoneNumber);
			
			Double userBalance = ((User) currentUser).getAccountBalance();
			accountBalance.setText("�" + Double.toString(userBalance));
		}else {
			//get all information in about user from ScreenManager class.
			Librarian staff = (Librarian) currentUser;
			String fullname = staff.getFirstName() + " " + staff.getLastName();
			
			userLabel1.setText(staff.getUsername());
			fullnameLabel1.setText(fullnameLabel1.getText() + " " + fullname);
			addressLabel1.setText(addressLabel1.getText() + " " + staff.getAddress());
			phoneLabel1.setText(phoneLabel1.getText() + " " + staff.getPhoneNumber());
			postcodeLabel1.setText(postcodeLabel1.getText() + " " + staff.getPostcode());
			dateLabel1.setText(dateLabel1.getText() + " " + staff.getEmploymentDate());
			staffIDLabel1.setText(staffIDLabel1.getText() + " " + staff.getStaffID());
		}
	}
	
	/**
	 * Event handler that handles when a resource is clicked.
	 */
	final EventHandler<MouseEvent> enterHandler = event -> {
		StackPane currentPane = (StackPane) event.getSource();
		currentPane.getChildren().get(0).setOpacity(0.3);
		currentPane.getChildren().get(1).setVisible(true);
	};
	
	/**
	 * Event handler that handles when a resource is clicked.
	 */
	final EventHandler<MouseEvent> exitHandler = event -> {
		StackPane currentPane = (StackPane) event.getSource();
		currentPane.getChildren().get(0).setOpacity(1);
		currentPane.getChildren().get(1).setVisible(false);
		
	};
	
	final EventHandler<MouseEvent> clickHandler = event -> {
		
		int resourceId = Integer.parseInt((((StackPane) event.getSource()).getId()));
		ScreenManager.setCurrentResource(Resource.getResource(resourceId));
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/copyScene.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Resource Information");
            stage.setScene(new Scene(root1));  
            stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	};
	
	
	private StackPane createImage(int i, int width, int height) {
		
		StackPane imagePane = new StackPane();
		
		Text resourceText = new Text();
		resourceText.setFont(Font.font("Arial", 20));
		resourceText.setText("ID: " + resources.get(i).getUniqueID() + "\n" +
		resources.get(i).getTitle() + "\n" + resources.get(i).getYear());
		resourceText.setVisible(false);
		resourceText.setTextAlignment(TextAlignment.CENTER);
		
		//create new resource image to be added.
		ImageView image = new ImageView();
		image.setFitWidth(width);
		image.setFitHeight(height);
		image.setImage(resources.get(i).getThumbnail());
		
		imagePane.getChildren().add(image);
		imagePane.getChildren().add(resourceText);
		
		//set id of imagePane to it's index so it can be accessed
		//within the event handler.
		imagePane.setId(Integer.toString(i));
		
		return imagePane;
	}
	
	
	private boolean search(int i ) {
		//get the resource
		Resource r = resources.get(i);
		String searchText = searchTextBox.getText();
		
		if(bookCheck.isSelected() && r instanceof Book) {
			return r.contains(searchText);
		}
		if(dvdCheck.isSelected() && r instanceof DVD) {
			return r.contains(searchText);
		}
		if(laptopCheck.isSelected() && r instanceof Laptop) {
			return r.contains(searchText);
		}
		
		return false;

	}
	
	private void loadCopies() {
		if (ScreenManager.getCurrentUser() instanceof User) {
			
			//get user copies that they're currently borrowing.
			((User) currentUser).loadUserCopies();
			ArrayList<Copy> userCopies = ((User) currentUser).getBorrowedCopies();
			
			for(Copy currentCopy : userCopies) {
				Resource copyResource = currentCopy.getResource();
				StackPane imagePane = createImage(copyResource.getUniqueID(), COPY_IMG_WIDTH, COPY_IMG_HEIGHT);
				
				Rectangle colorOverlay = new Rectangle();
				colorOverlay.setFill(Color.LIGHTGREEN);
				colorOverlay.setWidth(COPY_IMG_WIDTH);
				colorOverlay.setHeight(COPY_IMG_HEIGHT);
				colorOverlay.setOpacity(0.5);
				colorOverlay.setBlendMode(BlendMode.HARD_LIGHT);
				imagePane.getChildren().add(colorOverlay);
				
				resourceImages.getChildren().add(imagePane);
				
				imagePane.setOnMouseEntered(enterHandler);
				imagePane.setOnMouseExited(exitHandler);
			}
		}
		//get user copies that they have requested.

	}
	
	
	/**
	 * Loads resource images from Resource class, so that they can
	 * be displayed within the UI.
	 */
	private void loadResourceImages() {
		if (ScreenManager.getCurrentUser() instanceof Librarian) {
			staffProfileTab.setDisable(false);
			userProfileTab.setDisable(true);
		} else {
			staffProfileTab.setDisable(true);
			userProfileTab.setDisable(false);
		}
		//get resources
		
		resources = Resource.getResources();
				
		
		System.out.println(resources.size());
		ScreenManager.setResources(resources);
		
		//for each resource in resources array
		for(int i = 0; i < resources.size(); i++) {
			if(search(i)) {
			StackPane imagePane = createImage(i, RES_IMG_WIDTH, RES_IMG_HEIGHT);
			
			//get last image in last resource HBox.
			HBox latestHBox = (HBox) vResourceBox.getChildren().get(vResourceBox.getChildren().size() - 1);
			
			//if there is at least one image in last resource HBox
			if(!latestHBox.getChildren().isEmpty()) {
				//if the number of resources in resource HBox is more than
				//the width of the resource VBox / the width of a resource image
				if(latestHBox.getChildren().size() > (vResourceBox.getPrefWidth()
				- RES_IMG_WIDTH) / RES_IMG_WIDTH) {
					//create new HBox below last HBox
					HBox hResourceBox = new HBox();
					hResourceBox.setSpacing(5);
					//add image to new HBox
					hResourceBox.getChildren().add(imagePane);
					vResourceBox.getChildren().add(hResourceBox);
				} else {
					latestHBox.getChildren().add(imagePane); //add new image to last HBox
				}
			} else {
				latestHBox.getChildren().add(imagePane); //add new image to last HBox
			}
			
			imagePane.setOnMouseEntered(enterHandler);
			imagePane.setOnMouseExited(exitHandler);
			imagePane.setOnMouseClicked(clickHandler);
			
		}
	}
		
	}

	@FXML
	 public void initialize() {
		
		currentUser = ScreenManager.getCurrentUser();
		resources = ScreenManager.getResources();
				
		loadResourceImages();
		loadUserInformation();
		loadCopies();
		
		scrollPane.setHvalue(0.5);
	
	 }
	
	//
	// Staff: Copies Explorer
	//
	
	@FXML
	private void displayOverdue() {
		
	}
	
	@FXML
	private void openProfileEditor(MouseEvent event) {
		System.out.println("Launch staff editing profile.");
		changeScene(event,"/fxml/StaffEdit.fxml");
	}

}
