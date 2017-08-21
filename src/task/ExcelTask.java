package task;


import java.io.File;

import javafx.concurrent.Task;
import util.PropertyUtil;

public class ExcelTask extends Task<String> {

	private File file;

	public ExcelTask(File file) {
		this.file = file;
	}
	
	@Override
	protected String call() throws Exception {
		// TODO Auto-generated method stub
		if(!file.exists()){
			//文件为空
			return "文件不存在";
		}else{
			//解析Excel文件
			parseExcel(file);
		}
		//执行成功
		return "导入成功";
	}

}
