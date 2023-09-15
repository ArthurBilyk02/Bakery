package com.example.demo4;

import Models.*;
import Resources.LinkedList;
import Resources.Node;
import Resources.Save;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import java.io.File;

public class HelloController {
    public LinkedList<BakedGoods> bakedGoodsLinkedList = new LinkedList<>();
    public LinkedList<Ingredients> ingredientsLinkedList = new LinkedList<>();

    public LinkedList<Recipe> recipesLinkedList = new LinkedList<>();

    public Save save = new Save();
    @FXML
    private ImageView imageView;
    @FXML
    private TextField searchTextField;
    @FXML
    private RadioButton nameRadioBtn;
    @FXML
    private RadioButton regionRadioBtn;
    @FXML
    private TableView<BakedGoods> bakedGoodsSearchTable;
    @FXML
    private TableColumn<BakedGoods, String> bakedGoodsSearchNameColumn1;
    @FXML
    private TableColumn<BakedGoods, String> bakedGoodsSearchOriginColumn1;
    @FXML
    private TableColumn<BakedGoods, String> bakedGoodsSearchDescriptionColumn1;
    @FXML
    private ImageView BakedGoodsSearchImage;
    @FXML
    private ImageView bakedGoodsTableImageView;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField originTextField;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private ComboBox<BakedGoods> bakedGoodsComboBox;
    @FXML
    private ComboBox<Ingredients> ingredientsComboBox;
    @FXML
    private TextArea stepsTextArea;
    @FXML
    private HBox container;
    @FXML
    private TableView<BakedGoods> bakedGoodsTableView;
    @FXML
    private TableColumn<BakedGoods, String> bakedGoodsNameColumn;
    @FXML
    private TableColumn<BakedGoods, String> bakedGoodsOriginColumn;
    @FXML
    private TableColumn<BakedGoods, String> bakedGoodsDescriptionColumn;
    @FXML
    private TableView<Ingredients> ingredientsTableView;
    @FXML
    private TableColumn<Ingredients, String> ingredientNameColumn;
    @FXML
    private TableColumn<Ingredients, String> ingredientDescriptionColumn;
    @FXML
    private TableColumn<Ingredients, String> ingredientCaloriesColumn;
    @FXML
    private TextField ingredientNameTextField;
    @FXML
    private TextArea ingredientDescriptionTextArea;
    @FXML
    private TextField ingredientCaloriesTextField;
    @FXML
    private TextField IngredientModifyTextFieldName;
    @FXML
    private TextArea IngredientModifyTextFieldDesc;
    @FXML
    private TextField IngredientModifyTextFieldcalories;
    @FXML
    private Button modifyIngredientButton;
    @FXML
    private TextField originModifyTextField;
    @FXML
    private TextField nameModifyTextField;
    @FXML
    private TextArea descriptionModifyTextArea;
    @FXML
    private Button selectmodifyimageBtn1;

    private String selectedImageURL;

    @FXML
    private void initialize() {
        // Set up the listener for the bakedGoodsTableView selection change event
        bakedGoodsSearchTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            showSearchedBakedGoodImage(newValue);
            if (newValue != null) {
                nameModifyTextField.setText(newValue.getGoodsName());
                descriptionModifyTextArea.setText(newValue.getGoodsDesc());
                originModifyTextField.setText(newValue.getOriginCountry());
            }
        });

        bakedGoodsTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSelectedBakedGoodImage(newValue)
        );

        ingredientsTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selectIngredient()
        );
        save.loadDataFromTextFiles(bakedGoodsLinkedList, ingredientsLinkedList, recipesLinkedList);
        bakedGoodsNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGoodsName()));
        bakedGoodsOriginColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOriginCountry()));
        bakedGoodsDescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGoodsDesc()));

        bakedGoodsSearchNameColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGoodsName()));
        bakedGoodsSearchOriginColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOriginCountry()));
        bakedGoodsSearchDescriptionColumn1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGoodsDesc()));

        ingredientNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue() != null ? cellData.getValue().getIngName() : ""));
        ingredientDescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue() != null ? cellData.getValue().getIngDesc() : ""));
        ingredientCaloriesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue() != null ? String.valueOf(cellData.getValue().getCalories()) : ""));
        refreshTableViews();
        // Populate the ingredient and baked goods ComboBoxes
        populateIngredientComboBox();
        populateBakedGoodsComboBox();
        populateIngredientTableView();
    }
    @FXML
    private void onSave(){
        save.saveDataToTextFiles(bakedGoodsLinkedList, ingredientsLinkedList, recipesLinkedList);
    }

    @FXML
    private void searchBakedGoods() {
        // Retrieve the search keyword from the UI component
        String searchKeyword = searchTextField.getText();

        // Create a new list to store the search results
        LinkedList<BakedGoods> searchResults = new LinkedList<>();
        System.out.println("Searching");
        // Perform the search based on the selected search criteria
        if (nameRadioBtn.isSelected()) {
            // Search by name
            for (int i = 0; i < bakedGoodsLinkedList.numNodes(); i++) {
                BakedGoods bakedGood = bakedGoodsLinkedList.get(i);
                if (bakedGood.getGoodsName().equalsIgnoreCase(searchKeyword)) {
                    searchResults.add(bakedGood);
                }
            }
        } else if (regionRadioBtn.isSelected()) {
            // Search by region
            for (int i = 0; i < bakedGoodsLinkedList.numNodes(); i++) {
                BakedGoods bakedGood = bakedGoodsLinkedList.get(i);
                if (bakedGood.getOriginCountry().equalsIgnoreCase(searchKeyword)) {
                    searchResults.add(bakedGood);
                }
            }
        }

        // Clear the existing items in the TableView
        bakedGoodsSearchTable.getItems().clear();

        // Create a new ObservableList to store the search results
        ObservableList<BakedGoods> observableSearchResults = FXCollections.observableArrayList();

        // Add the search results to the ObservableList
        Node current = searchResults.getHead();
        while (current != null) {
            observableSearchResults.add((BakedGoods) current.getData());
            current = current.getNext();
        }

        // Set the ObservableList as the items of the TableView
        bakedGoodsSearchTable.setItems(observableSearchResults);

        // Clear the selected image in the ImageView
        imageView.setImage(null);
    }

    private void showSearchedBakedGoodImage(BakedGoods bakedGood) {
        if (bakedGood != null) {
            String imageURL = bakedGood.getURL();
            if (imageURL != null) {
                Image image = new Image(imageURL);
                BakedGoodsSearchImage.setImage(image);
            }
        } else {
            BakedGoodsSearchImage.setImage(null);
        }
    }
    private void populateIngredientTableView() {
        // Clear existing items from the TableView
        ingredientsTableView.getItems().clear();

        // Retrieve all ingredients from the linked list
        Node current = ingredientsLinkedList.getHead();
        while (current != null) {
            Ingredients ingredient = (Ingredients) current.getData();

            // Add the ingredient to the TableView
            ingredientsTableView.getItems().add(ingredient);

            current = current.getNext();
        }
    }

    @FXML
    private void sortIngredientsListByName() {
        ingredientsLinkedList.sortIngredientsByName();
        populateIngredientTableView();
    }

    @FXML
    private void sortIngredientsListByCalories() {
        ingredientsLinkedList.sortIngredientsByCalories();
        populateIngredientTableView();
    }

    private void showSelectedBakedGoodImage(BakedGoods bakedGood) {
        if (bakedGood != null) {
            String imageURL = bakedGood.getURL();
            if (imageURL != null) {
                Image image = new Image(imageURL);
                bakedGoodsTableImageView.setImage(image);
            }
        } else {
            bakedGoodsTableImageView.setImage(null);
        }
    }

    @FXML
    private void addBakedGood() {
        // Retrieve the necessary information from the UI components
        String name = nameTextField.getText();
        String origin = originTextField.getText();
        String description = descriptionTextArea.getText();
        String imageURL = selectedImageURL;

        // Create a new BakedGoods object
        BakedGoods bakedGood = new BakedGoods(name, origin, description, imageURL);

        // Add the BakedGoods object to the linked list
        bakedGoodsLinkedList.add(bakedGood);

        // Clear the input fields or perform any other necessary actions
        nameTextField.clear();
        originTextField.clear();
        descriptionTextArea.clear();
        selectedImageURL = null;
        imageView.setImage(null);

        // Refresh the TableView to reflect the changes
        bakedGoodsTableView.getItems().add(bakedGood);
        populateBakedGoodsComboBox();
    }

    @FXML
    private void selectIngredient() {
        // Get the selected ingredient from the TableView
        Ingredients selectedIngredient = ingredientsTableView.getSelectionModel().getSelectedItem();
        IngredientModifyTextFieldName.setText(selectedIngredient.getIngName());
        IngredientModifyTextFieldDesc.setText(selectedIngredient.getIngDesc());
        IngredientModifyTextFieldcalories.setText(String.valueOf(selectedIngredient.getCalories()));
    }
    @FXML
    private void RemoveIngredient(){
        Ingredients selectedIngredient = ingredientsTableView.getSelectionModel().getSelectedItem();
        for(int i = 0; i< ingredientsLinkedList.numNodes(); i++){
            if(ingredientsLinkedList.get(i) == selectedIngredient){
                System.out.println("Successfully Removed Object");
                ingredientsLinkedList.deleteNode(i);
                refreshTableViews();
                break;
            }
        }
    }
    @FXML
    private void RemoveBakedGood(){
        System.out.println("Removing");
        BakedGoods SelectedBakedGood = bakedGoodsTableView.getSelectionModel().getSelectedItem();
        for(int i = 0; i< bakedGoodsLinkedList.numNodes(); i++){
            if(bakedGoodsLinkedList.get(i) == SelectedBakedGood){
                System.out.println("Successfully Removed Object");
                bakedGoodsLinkedList.deleteNode(i);
                refreshTableViews();
                break;
            }
        }
    }
    private void refreshTableViews(){
        for(int i = 1; i< bakedGoodsLinkedList.numNodes();i++){
            bakedGoodsTableView.getItems().add(bakedGoodsLinkedList.get(i));
        }
        for(int i = 1; i< ingredientsLinkedList.numNodes();i++){
            ingredientsTableView.getItems().add(ingredientsLinkedList.get(i));
        }
    }
    @FXML
    private void addIngredient() {
        // Retrieve the information from the text fields
        String name = ingredientNameTextField.getText();
        String description = ingredientDescriptionTextArea.getText();
        int calories = Integer.parseInt(ingredientCaloriesTextField.getText());

        // Create a new ingredient object
        Ingredients newIngredient = new Ingredients(name, description, calories);

        // Add the new ingredient to the linked list
        ingredientsLinkedList.add(newIngredient);
        System.out.print(ingredientsLinkedList.numNodes());

        // Clear the text fields
        ingredientNameTextField.clear();
        ingredientDescriptionTextArea.clear();
        ingredientCaloriesTextField.clear();

        System.out.println("Successfully added ingredient");

        // Refresh the TableView to reflect the changes
        ingredientsTableView.refresh();
        populateIngredientTableView();
        populateIngredientComboBox();
    }
    @FXML
    private void modifyBakedGood() {
        // Get the selected baked good from the TableView
        BakedGoods selectedGood = bakedGoodsTableView.getSelectionModel().getSelectedItem();

        // Retrieve the modified details from the text fields
        String modifiedName = nameModifyTextField.getText();
        String modifiedDescription = descriptionModifyTextArea.getText();
        String modifiedRegion = originModifyTextField.getText();

        // Update the selected baked good with the modified details
        if (selectedGood != null) {
            selectedGood.setGoodsName(modifiedName);
            selectedGood.setGoodsDesc(modifiedDescription);
            selectedGood.setOriginCountry(modifiedRegion);

            // Refresh the TableView to reflect the changes
            bakedGoodsTableView.refresh();
        }

        // Clear the text fields
        nameModifyTextField.clear();
        descriptionModifyTextArea.clear();
        originModifyTextField.clear();
    }

    @FXML
    private void selectImage() {
        BakedGoods selectedGood = bakedGoodsTableView.getSelectionModel().getSelectedItem();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            String imagePath = selectedFile.toURI().toString();

            // You can store the imagePath in the BakedGoods object or use it as needed.
            // For example, you can set it as the URL for the BakedGoods image:
            if (selectedGood != null) {
                selectedGood.setURL(imagePath);
            }
        }
    }

    @FXML
    private void modifyIngredient() {
        // Get the selected ingredient from the TableView
        Ingredients selectedIngredient = ingredientsTableView.getSelectionModel().getSelectedItem();
        // Retrieve the modified details from the text fields
        String modifiedName = IngredientModifyTextFieldName.getText();
        String modifiedDescription = IngredientModifyTextFieldDesc.getText();
        int modifiedCalories = Integer.parseInt(IngredientModifyTextFieldcalories.getText());

        // Update the selected ingredient with the modified details
        if (selectedIngredient != null) {
            selectedIngredient.setIngName(modifiedName);
            selectedIngredient.setIngDesc(modifiedDescription);
            selectedIngredient.setCalories(modifiedCalories);

            // Refresh the TableView to reflect the changes
            ingredientsTableView.refresh();
        }

        // Clear the text fields
        ingredientNameTextField.clear();
        ingredientDescriptionTextArea.clear();
        ingredientCaloriesTextField.clear();

        populateIngredientTableView();
    }

    @FXML
    private void choosePhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Photo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            selectedImageURL = selectedFile.toURI().toString();
        }
    }

    @FXML
    private void addRecipe() {
        // Retrieve the information from the UI components
        String recipeGoods = bakedGoodsComboBox.getValue().getGoodsName();
        String recipeSteps = stepsTextArea.getText();
        Ingredients selectedIngredient = ingredientsComboBox.getValue();

        if (selectedIngredient != null) {
            // Create a new LinkedList to store the recipe ingredients
            LinkedList<Ingredients> recipeIngredients = new LinkedList<>();

            // Add the selected ingredient to the recipe ingredients list
            recipeIngredients.add(selectedIngredient);

            // Create a new Recipe object
            Recipe newRecipe = new Recipe(recipeGoods, recipeSteps, recipeIngredients);
            recipesLinkedList.add(newRecipe);
            stepsTextArea.clear();
        }
    }
    private void populateIngredientComboBox() {
        ingredientsComboBox.getItems().clear(); // Clear existing items from the ComboBox

        for (int i = 0; i < ingredientsLinkedList.numNodes(); i++) {
            Ingredients ingredient = ingredientsLinkedList.get(i);
            // Add the ingredient object to the ComboBox
            ingredientsComboBox.getItems().add(ingredient);
        }
    }

    private void populateBakedGoodsComboBox() {
        bakedGoodsComboBox.getItems().clear(); // Clear existing items from the ComboBox

        for (int i = 0; i < bakedGoodsLinkedList.numNodes(); i++) {
            BakedGoods bakedGood = bakedGoodsLinkedList.get(i);
            // Add the baked good object to the ComboBox
            bakedGoodsComboBox.getItems().add(bakedGood);
        }
    }
}