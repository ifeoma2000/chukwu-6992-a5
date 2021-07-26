package ucf.assignments;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class InventoryController implements Initializable {

    // GUI controls defined in FXML and used by the controller's code
    @FXML
    public TextField Type_Value;

    @FXML
    public ChoiceBox cbox;

    @FXML
    private Button clear;

    @FXML
    private Button remove;

    @FXML
    private Button add_item;

    @FXML
    public TextField Type_SerialNumber;

    @FXML
    public TextField Type_Name;

    @FXML
    private Button load;

    @FXML
    private Button save;

    @FXML
    private javafx.scene.control.CheckBox toggle_completed_items;

    @FXML
    private TextField itemName;

    @FXML
    private Button edit;
    @FXML
    private ListView inventoryList;
    final ObservableList<InventoryItem> inventoryItems = FXCollections.observableArrayList();

    // called by FXMLLoader to initialize the controller
    public void initialize(URL location, ResourceBundle resources) {
        inventoryList.setItems(inventoryItems);
        cbox.setItems(FXCollections.observableArrayList("TSV", "HTML", "JSON"));
    }

    @FXML
    public void click() {
        int selectedItem = inventoryList.getSelectionModel().getSelectedIndex();
        Type_Name.setText(inventoryItems.get(selectedItem).name);
        Type_Value.setText(inventoryItems.get(selectedItem).value);
        Type_SerialNumber.setText(inventoryItems.get(selectedItem).serialNumber);
    }


    @FXML
    //method that edits each item on the list
    public void edit() {
        int selectedItem = inventoryList.getSelectionModel().getSelectedIndex();

        inventoryItems.get(selectedItem).name = Type_Name.getText();

        int count = 0;
        for (int i = 0; i < inventoryItems.size(); i++) {
            if (inventoryItems.get(i).serialNumber.equals(Type_SerialNumber.getText()))
                count++;
        }
        if (count == 0)
            inventoryItems.get(selectedItem).serialNumber = Type_SerialNumber.getText();

        inventoryItems.get(selectedItem).value = Type_Value.getText();
    }

    @FXML
    //method that allows the user to add an item to the list
    public void addAction() {
        if (!Type_SerialNumber.getText().equals("")) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Error");
            error.setContentText("Serial Number must be Empty");
            error.showAndWait();
        } else
            inventoryItems.add(new InventoryItem(Type_Name.getText(), Type_Value.getText()));
    }

    @FXML
    //method that alows the user to delete an item from the list
    public void deleteAction() {
        int selectedItem = inventoryList.getSelectionModel().getSelectedIndex();
        inventoryItems.remove(selectedItem);
    }

    @FXML
    public void sortByName() {
        TreeMap<String, InventoryItem> nameMap = new TreeMap<>();

        for (InventoryItem it : inventoryItems) {
            nameMap.put(it.name, it);
        }
        ObservableList<InventoryItem> temp = FXCollections.observableArrayList();
        for (Map.Entry<String, InventoryItem> entry : nameMap.entrySet()) {
            temp.add(entry.getValue());
        }
        inventoryItems.clear();
        inventoryItems.addAll(temp);
    }

    @FXML
    public void sortBySerialNumber() {
        TreeMap<String, InventoryItem> nameMap = new TreeMap<>();

        for (InventoryItem it : inventoryItems) {
            nameMap.put(it.serialNumber, it);
        }
        ObservableList<InventoryItem> temp = FXCollections.observableArrayList();
        for (Map.Entry<String, InventoryItem> entry : nameMap.entrySet()) {
            temp.add(entry.getValue());
        }
        inventoryItems.clear();
        inventoryItems.addAll(temp);
    }

    @FXML
    public void sortByValue() {
        TreeMap<String, InventoryItem> nameMap = new TreeMap<>();

        for (InventoryItem it : inventoryItems) {
            nameMap.put(it.value, it);
        }
        ObservableList<InventoryItem> temp = FXCollections.observableArrayList();
        for (Map.Entry<String, InventoryItem> entry : nameMap.entrySet()) {
            temp.add(entry.getValue());
        }
        inventoryItems.clear();
        inventoryItems.addAll(temp);
    }


    private ArrayList<ArrayList<String>> initSave() {
        //initilaize an array list of array lists of strings
        ArrayList<ArrayList<String>> saveArray = new ArrayList<>();
        //for loop that iterates through the entire array list
        for (int i = 0; i < inventoryItems.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            temp.add(inventoryItems.get(i).name);
            temp.add(inventoryItems.get(i).serialNumber);
            temp.add(inventoryItems.get(i).value);
            saveArray.add(temp);
        }
        return saveArray;
    }

    @FXML
    private void save() {
        //method that gives the user the option to select which way to save
        String text = cbox.getSelectionModel().getSelectedItem().toString();
        if (text.equals("TSV")) {
            saveTSV();
        } else if (text.equals("HTML")) {
            saveHTML();
        } else if (text.equals("JSON")) {
            saveJSON();
        }
    }


    @FXML
    //method that saves the list
    private void saveTSV() {
        ArrayList<ArrayList<String>> saveArray = initSave();
        try {
            //writes the list to a text file
            File saveFile = new File("src/main/resources/ucf/assignments/save.txt");
            FileWriter writer = new FileWriter("src/main/resources/ucf/assignments/save.txt");

            String saveText = "";
            for (ArrayList<String> arr : saveArray) {
                saveText += arr.get(0) + "\t" + arr.get(1) + "\t" + arr.get(2) + "\n";
            }
            writer.write(saveText);
            writer.close();
        } catch (IOException e) {
            ;
        }
    }

    @FXML
    //method that saves the list
    private void saveHTML() {
        ArrayList<ArrayList<String>> saveArray = initSave();
        try {
            //writes the list to an HTML file
            File saveFile = new File("src/main/resources/ucf/assignments/save.html");
            FileWriter writer = new FileWriter("src/main/resources/ucf/assignments/save.html");

            String saveText = "<html><table><tr><th>Name</th><th>Serial Number</th><th>Value</th></tr>";
            for (ArrayList<String> arr : saveArray) {
                saveText += "<tr><th>%s</th><th>%s</th><th>%s</th></tr>".formatted(arr.get(0), arr.get(1), arr.get(2));
            }
            saveText += "</table></html>";
            writer.write(saveText);
            writer.close();
        } catch (IOException e) {
            ;
        }
    }

    @FXML
    //method that saves the list
    private void saveJSON() {
        ArrayList<ArrayList<String>> saveArray = initSave();
        try {
            //writes the list to a JSON file
            File saveFile = new File("src/main/resources/ucf/assignments/save.json");
            FileWriter writer = new FileWriter("src/main/resources/ucf/assignments/save.json");

            String saveText = "{";
            for (ArrayList<String> arr : saveArray) {
                saveText += "\"Object\":{\"Name\":\"%s\",\"SerialNumber\":\"%s\",\"Value\":\"%s\"},".formatted(arr.get(0), arr.get(1), arr.get(2));
            }
            saveText = saveText.substring(0, saveText.length() - 1);
            saveText += "}";
            writer.write(saveText);
            writer.close();
        } catch (IOException e) {
            ;
        }
    }


    @FXML
    //method that gives the user the option to select which file to load
    private void load() {
        String text = cbox.getSelectionModel().getSelectedItem().toString();
        if (text.equals("TSV")) {
            loadTSV();
        } else if (text.equals("HTML")) {
            loadHTML();
        } else if (text.equals("JSON")) {
            loadJSON();
        }
    }

    @FXML
    //method that loads an existing JSON file
    private void loadJSON() {
        try {
            //calls the file that was made
            File saveFile = new File("src/main/resources/ucf/assignments/save.json");
            //reads from the file
            Scanner reader = new Scanner(saveFile);
            String loadRaw = "";
            //while loop that reads the entire file
            while (reader.hasNextLine()) {
                loadRaw += reader.nextLine();
            }
            ArrayList<String[]> loadFile = new ArrayList<>();
            System.out.println(loadRaw);
            for (int i = 1; i < loadRaw.length(); i++) {
                if (loadRaw.charAt(i) == '{') {
                    String temp = "";
                    i++;
                    while (loadRaw.charAt(i) != '}') {
                        temp += loadRaw.charAt(i);
                        i++;
                    }
                    String[] tArray = temp.replaceAll("\"", "").replaceAll("Value:", "").replaceAll("Name:", "").replaceAll("SerialNumber:", "").split(",");
                    loadFile.add(tArray);

                }
            }
            inventoryItems.clear();
            //reads through each item in the file by its name, value, and serial number
            for (int i = 0; i < loadFile.size(); i++) {
                inventoryItems.add(new InventoryItem(loadFile.get(i)[0], loadFile.get(i)[1], loadFile.get(i)[2]));
            }

        } catch (IOException e) {
            ;
        }
    }


    @FXML
    //method that loads an existing HTML file
    private void loadHTML() {
        try {
            //calls the file that was made
            File saveFile = new File("src/main/resources/ucf/assignments/save.html");
            //reads from the file
            Scanner reader = new Scanner(saveFile);
            String loadRaw = "";
            //while loop that reads the entire file
            while (reader.hasNextLine()) {
                loadRaw += reader.nextLine();
            }
            ArrayList<String[]> loadFile = new ArrayList<>();
            for (int i = 4; i < loadRaw.length(); i++) {
                if (loadRaw.substring(i - 4, i).equals("<tr>")) {
                    String temp = "";
                    while (!loadRaw.substring(i - 5, i).equals("</tr>")) {
                        temp += loadRaw.charAt(i);
                        i++;
                    }
                    //uses the .split method to distinguish each list item which is seperated by a <th>
                    temp = temp.replaceAll("</th>", "");
                    String[] tArray = temp.substring(4, temp.length() - 5).split("<th>");
                    loadFile.add(tArray);

                }
            }
            inventoryItems.clear();

            for (int i = 0; i < loadFile.size(); i++) {
                inventoryItems.add(new InventoryItem(loadFile.get(i)[0], loadFile.get(i)[1], loadFile.get(i)[2]));
            }

        } catch (IOException e) {
            ;
        }
    }

    @FXML
    //method that loads an existing list
    private void loadTSV() {
        try {
            //calls the file that was made
            File saveFile = new File("src/main/resources/ucf/assignments/save.txt");
            //reads from the file
            Scanner reader = new Scanner(saveFile);
            String loadRaw = "";
            //while loop that reads the entire file
            while (reader.hasNextLine()) {
                loadRaw += reader.nextLine();
                loadRaw += "\n";
            }
            ArrayList<String[]> loadFile = new ArrayList<>();
            //for loop that reads the entire file starting from the left hand bracket to the right hand bracket
            System.out.println(loadRaw);
            for (int i = 0; i < loadRaw.length(); i++) {
                String temp = "";
                i++;
                while (loadRaw.charAt(i) != '\n') {
                    temp += loadRaw.charAt(i);
                    i++;
                }
                //uses the .split method to distinguish each list item which is seperated by a \t
                String[] tArray = temp.split("\t");
                loadFile.add(tArray);

            }
            inventoryItems.clear();
            //reads through each item in the file by its name, value, and serial number
            for (int i = 0; i < loadFile.size(); i++) {
                inventoryItems.add(new InventoryItem(loadFile.get(i)[0], loadFile.get(i)[1], loadFile.get(i)[2]));
            }

        } catch (IOException e) {
            ;
        }
    }

    @FXML
    public void searchByName(ActionEvent actionEvent) {
        TreeMap<String, InventoryItem> nameMap = new TreeMap<>();

        for (InventoryItem it : inventoryItems) {
                nameMap.put(it.name, it);
        }
        for (InventoryItem it : nameMap.values()) {
            if (it.name.equals((itemName.getText())))
            {
                inventoryItems.clear();
                inventoryItems.add(it);
                break;
            }
        }
        inventoryItems.setAll(inventoryItems);
    }


    public void searchByValue(MouseEvent mouseEvent) {
    }
}










