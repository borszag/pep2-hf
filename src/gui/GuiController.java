package gui;

import gamelogic.GameType;
import gamelogic.TileType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static gamelogic.GameType.*;


public class GuiController {
    private Stage stage;
    @FXML
    private Slider timerSliderSingle;
    @FXML
    private Slider timerSliderMulti;
    @FXML
    private ToggleGroup startingColorTG;

    @FXML
    private ToggleGroup startingColorTGMulti;
    @FXML
    private ToggleGroup difficultyTG;
    @FXML
    private TextField multiNameText;

    @FXML
    private TextField multiIPAddrText;


    DrawerController drawerController;

    public ToggleGroup getStartingColorTG() {

        return startingColorTG;
    }

    public ToggleGroup getDifficultyTG() {
        return difficultyTG;
    }

    /**
     * Inicializál néhány FXML node-ot
     */
    @FXML
    public void initialize() {
        startingColorTG.getToggles().get(0).setUserData(TileType.LIGHT);
        startingColorTG.getToggles().get(1).setUserData(TileType.DARK);
        startingColorTGMulti.getToggles().get(0).setUserData(TileType.LIGHT);
        startingColorTGMulti.getToggles().get(1).setUserData(TileType.DARK);
        difficultyTG.getToggles().get(0).setUserData(0);
        difficultyTG.getToggles().get(1).setUserData(1);
        difficultyTG.getToggles().get(2).setUserData(2);
    }

    @FXML
    void startSingleGame(ActionEvent event) throws IOException {
        changeSceneToCanvas(event, SINGLE, this.timerSliderSingle.getValue()); //Előbb change scene aztán init
    }

    @FXML
    void loadSingleGame(ActionEvent event) throws IOException {
        changeSceneToCanvas(event, SINGLE, this.timerSliderSingle.getValue()); //Előbb change scene aztán init
    }

    @FXML
    void startLocalGame(ActionEvent event) throws IOException {
        String name = getMultiNameText();
        changeSceneToCanvas(event, LOCAL, this.timerSliderMulti.getValue()); //Előbb change scene aztán init
    }

    @FXML
    void startOnlineGame(ActionEvent event) throws IOException {
        String ipAddr = getMultiIPAddrText();
        String name = getMultiNameText();
        changeSceneToCanvas(event, ONLINE, this.timerSliderMulti.getValue()); //Előbb change scene aztán init
        //GameLoop game = new GameLoop(canvas_sb);
    }

    @FXML
    double getTimerSliderSingleVal(MouseEvent event) {
        return timerSliderSingle.getValue();
    }

    @FXML
    double getTimerSliderMultiVal(MouseEvent event) {
        return timerSliderMulti.getValue();
    }


    @FXML
    String getMultiNameText() {
        return multiNameText.getText();
    }

    @FXML
    String getMultiIPAddrText() {
        return multiIPAddrText.getText();
    }

    private void changeSceneToCanvas(ActionEvent event, GameType gameType, double timer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/canvasDrawer.fxml"));
        Parent canvasDrawerParent = loader.load();

        Scene canvasDrawerScene = new Scene(canvasDrawerParent, 700, 510);
        this.drawerController = loader.getController();

        // Kiválaszott szín
        TileType selectedColor = (TileType) startingColorTG.getSelectedToggle().getUserData();

        int diff = (int) difficultyTG.getSelectedToggle().getUserData();

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        GameOptions gameOptions = new GameOptions(gameType, timer, diff, getMultiIPAddrText(), getMultiNameText(), selectedColor);

        drawerController.initDrawerController(window.getScene(), gameOptions); //passing menu scene so drawer controller can change back

        window.setScene((canvasDrawerScene));
        window.show();
    }

}


