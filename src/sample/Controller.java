package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller {

    @FXML
    private WebView webView;

    @FXML
    private Button open;

    private String viewerHTMLURIString;

    public Controller() {

        final Path path = Paths.get("pdfjs", "web", "viewer.html");
        final File viewerHTMLFile = path.toFile();
        if (viewerHTMLFile.exists())
            this.viewerHTMLURIString = viewerHTMLFile.toURI().toString();
    }

    @FXML
    private void initialize() {

        this.webView.getEngine().setJavaScriptEnabled(true);
        this.open.setOnAction(event -> openPDF());
    }

    private void openPDF() {

        if (!this.viewerHTMLURIString.isEmpty()) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open PDF File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF", "*.pdf"));

            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {

                final String selectedFileURLString = String.format("%s?file=%s", this.viewerHTMLURIString, selectedFile.toURI());
                this.webView.getEngine().load(selectedFileURLString);
            }
        }
    }
}
