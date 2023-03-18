/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extractedit;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.JFrame;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 *
 * @author NATHAN
 */
public class FXMLDocumentController implements Initializable {

    FileChooser fileChooser = new FileChooser();
    
    Webcam webcam = Webcam.getDefault();
    
    
    @FXML
    private HTMLEditor textEdit;


    @FXML
    private Button btnSave;

    @FXML
    void erase(ActionEvent event) {
        textEdit.setHtmlText("");
    }
    
    @FXML
    void closes(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void helps(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("helps.fxml"));
        Scene s = new Scene(root);
        Stage st = new Stage();
        st.setTitle("Page Helps");
        st.setScene(s);
        st.show();
    }

    @FXML
    void btnSaveAction(ActionEvent event) {
        File files = fileChooser.showSaveDialog(new Stage());
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Fichier text", "*.txt"));
        if (files != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(files));
                writer.write(textEdit.getHtmlText());
                writer.close();
                System.out.println("Fichier enregistre avec success");
            } catch (Exception e) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    @FXML
    void imagess(ActionEvent event) throws FileNotFoundException {
        File file = fileChooser.showOpenDialog(new Stage());
        try {
            Scanner sc = new Scanner(file);
            StringBuilder stringBuilder = new StringBuilder();
            ITesseract iTesseract= new Tesseract();
            iTesseract.setDatapath("");
            while (sc.hasNextLine()) {                
                stringBuilder.append(sc.nextLine()).append("\n");
            }
            ITesseract instance = new Tesseract();
            instance.setDatapath("C:\\Users\\NATHAN\\Documents\\NetBeansProjects\\ExtractEdit\\src\\image\\tessdata");
            String result = instance.doOCR(file);
            textEdit.setHtmlText(result);
        } catch (TesseractException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    void webss(ActionEvent event) {
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        
        WebcamPanel win = new WebcamPanel(webcam);
        win.setFPSDisplayed(true);
        win.setDisplayDebugInfo(true);
        win.setImageSizeDisplayed(true);
        win.setMirrored(true);
        
        JFrame jFrame=new JFrame();
        jFrame.setResizable(true);
        jFrame.setPreferredSize(new Dimension(500,400));
        jFrame.dispose();
        jFrame.setLocationRelativeTo(null);
        jFrame.pack();
        jFrame.add(win);
        jFrame.setVisible(true);
        
    }

    @FXML
    void fermer(ActionEvent event) {
        webcam.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fileChooser.setInitialDirectory(new File("C:\\Users\\NATHAN\\OneDrive\\Documents"));
    }

}
