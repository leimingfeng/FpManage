package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.ProgressFrom;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import task.ExcelTask;
import task.TaskListener;
import util.PropertyUtil;

public class FpMainController {

    @FXML
    private MenuItem openFile;
    
    @FXML
    private MenuItem closeFile;

    @FXML
    private Font x3;

    @FXML
    private Color x4;
    
    /**
     * openFile-->打开xls文件操作
     * @param event
     */
    @FXML
    void openFileAction(ActionEvent event) {
		//文件选择框
		FileChooser fileChooser = new FileChooser();
		//文件筛选
		List<String> filter = new ArrayList<String>();
		filter.add("*.xls");
		filter.add("*.xlsx");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("EXCEL files (*.xls,*.xlsx)", filter);
        fileChooser.getExtensionFilters().add(extFilter);
        
        //获取上次设置的excel路径
        String excelPath = PropertyUtil.getKeyValue("excelPath");
        if(!excelPath.equals("")&&new File(excelPath).exists()){
        	fileChooser.setInitialDirectory(new File(excelPath));
        }
        File file = fileChooser.showOpenDialog(openFile.getParentPopup().getOwnerWindow());
		//路径回显
        if(file!=null){
        	//将excel文档设置路径存入config文件中
        	PropertyUtil.writeProperties("excelPath", file.getParent());
        	ExcelTask excelTask = new ExcelTask(file);
        	excelTask.valueProperty().addListener(new TaskListener());
    		
    		ProgressFrom progressFrom = new ProgressFrom(excelTask,openFile.getParentPopup().getOwnerWindow());
    		progressFrom.activateProgressBar();
        	//读取当前excel文件内容
        	//ObservableList<TxtFile> list = configTxtTable(file.getAbsolutePath());
        	//tv_txtFile.setItems(list);
        }
    }
    
    /**
     * closeFile --> 关闭文件操作
     * @param event
     */
    @FXML
    void closeFileAction(ActionEvent event) {

    }


}
