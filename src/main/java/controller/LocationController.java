package controller;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import model.AccountLogged;
import service.FXMLService;
import service.TransitionService;

public class LocationController extends GeneralController {
	
    @FXML
    private JFXButton buttonSouth;
    
    @FXML
    private JFXButton buttonNorth;
    
    @FXML
    private BorderPane borderLocation;
    
    void Load() throws IOException
    {
        BorderPane border = FXMLLoaderInit(borderLocation, FXMLService.TRANSITION_SCREEN, true);
        
        /*
         * Check later 
         */
        final BorderPane borderSignup = FXMLLoaderInit(border, FXMLService.DUBLIN_SCREEN, false);
        
        TransitionService.PauseTransitionAndSetElement(border, borderSignup, 1);
    }
    
    @FXML
    protected void handleSouthButtonAction(ActionEvent event) throws IOException {
    	AccountLogged.accountLocation = 0;
    	
    	Load();
 
    }
    
    @FXML
    protected void handleNorthButtonAction(ActionEvent event) throws IOException {
    	AccountLogged.accountLocation = 1;
    	
    	Load();
    }


}
