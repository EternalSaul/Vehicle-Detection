package sample;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    private static final ObservableList<Object> ITEMS =
            FXCollections.observableArrayList("Yolo v3 四类", "Yolo v2 四类", new Separator(), "Yolo v2 单类", "Yolo v3 单类");
    private static final FileChooser.ExtensionFilter RESOURCE_FILES =
            new FileChooser.ExtensionFilter("resource files", "*.txt", "*.mp4", "*.jpg", "png", "*.avi");
    @FXML
    Button resourceBtn;
    @FXML
    Button saveBtn;
    @FXML
    Button detectBtn;
    @FXML
    TextField resourceText;
    @FXML
    TextField saveText;
    @FXML
    TextArea console;

    @FXML
    ChoiceBox<Object> detectorSelector;
    private Properties properties;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        console.setEditable(false);
        detectorSelector.setItems(ITEMS);
        detectorSelector.setValue(ITEMS.get(4));
        detectorSelector.setTooltip(new Tooltip("请选择你需要的检测器"));
        properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void BtnAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().add(RESOURCE_FILES);
        if (actionEvent.getSource().equals(resourceBtn)) {
            File file = fileChooser.showOpenDialog(resourceBtn.getContextMenu());
            resourceText.setText(file.toString());
        }
        if (actionEvent.getSource().equals(saveBtn)) {
            File file = fileChooser.showOpenDialog(saveBtn.getContextMenu());
            saveText.setText(file.toString());
        }
    }

    @FXML
    private void detectAction(ActionEvent actionEvent) throws IOException {
        String cmd = getCommand(
                Arrays.stream(
                        Objects.requireNonNull(
                                Optional.of(
                                        getPath(detectorSelector.getValue())
                                ).orElse(null)
                        )
                ).map(key -> properties.get(key)).collect(Collectors.toList())
        );
        console.clear();
        Process process = Runtime.getRuntime().exec(cmd);
        if (resourceText.getText().endsWith(".avi") || resourceText.getText().endsWith(".mp4")){
            new Thread(Waiter.doWaitFor(process, null)).start();
            console.setText("检测日志已保存到：videoLog.txt");
        }
        else
            new Thread(Waiter.doWaitFor(process, console)).start();
        System.out.println("hellpo");
    }

    private String getCommand(List<Object> collect) {
        String res = resourceText.getText();
        String save = saveText.getText();
        String darknet = properties.getProperty("darknet");
        String cmd = null;
        if (res.endsWith(".txt")) {
            cmd = String.format("cmd /c %s detector test %s %s %s < %s", darknet, collect.get(0), collect.get(2), collect.get(1), res);
            if (save.endsWith(".txt")) cmd += String.format(" -dont_show  >%s", save);
            return cmd;
        }
        if (res.endsWith(".avi") || res.endsWith(".mp4")) {
            cmd = String.format("cmd /c %s detector demo %s %s %s %s > videoLog.txt", darknet, collect.get(0), collect.get(2), collect.get(1), res);
            if (save.endsWith(".avi")) cmd += String.format(" -dont_show -out_filename %s", save);
            return cmd;
        }
        if (res.endsWith("jpg") || res.endsWith("png")) {
            cmd = String.format("cmd /c %s detector test %s %s %s %s", darknet, collect.get(0), collect.get(2), collect.get(1), res);
            if (save.endsWith(".jpg") || save.endsWith("png"))
                cmd += String.format(" -dont_show&&move predictions.jpg %s", save);
            return cmd;
        }
        return null;
    }

    private String[] getPath(Object value) {
        if (value.equals(detectorSelector.getItems().get(0))) {
            return new String[]{"fourdata", "v3four", "v3fourcfg"};
        }
        if (value.equals(detectorSelector.getItems().get(1))) {
            return new String[]{"fourdata", "v2four", "v2fourcfg"};
        }
        if (value.equals(detectorSelector.getItems().get(3))) {
            return new String[]{"singledata", "v2single", "v2singlecfg"};
        }
        if (value.equals(detectorSelector.getItems().get(4))) {
            return new String[]{"singledata", "v3single", "v3singlecfg"};
        }
        return null;
    }
}
