package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * 注册码工具类
 * @author Administrator
 *
 */
public class RegisterUtil {
    
	/**
	 * 获取主板序列号
	 * @return
	 */
	  public static String getMotherboardSN() {
		  String result = "";
		  try {
		   File file = File.createTempFile("realhowto", ".vbs");
		   file.deleteOnExit();
		   FileWriter fw = new java.io.FileWriter(file);

		   String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
		     + "Set colItems = objWMIService.ExecQuery _ \n"
		     + "   (\"Select * from Win32_BaseBoard\") \n"
		     + "For Each objItem in colItems \n"
		     + "    Wscript.Echo objItem.SerialNumber \n"
		     + "    exit for  ' do the first cpu only! \n" + "Next \n";

		   fw.write(vbs);
		   fw.close();
		   Process p = Runtime.getRuntime().exec(
		     "cscript //NoLogo " + file.getPath());
		   BufferedReader input = new BufferedReader(new InputStreamReader(p
		     .getInputStream()));
		   String line;
		   while ((line = input.readLine()) != null) {
		    result += line;
		   }
		   input.close();
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return result.trim();
		 }
	
	/**
	 * 获取硬盘号
	 * @param drive
	 * @return
	 */
    public static String getSerialNumber(String drive) {  
        String result = "";  
        try {  
            File file = File.createTempFile("damn", ".vbs");  
            file.deleteOnExit();  
            FileWriter fw = new java.io.FileWriter(file);  
            String vbs = "Set objFSO = CreateObject(\"Scripting.FileSystemObject\")\n"  
                    + "Set colDrives = objFSO.Drives\n"  
                    + "Set objDrive = colDrives.item(\""  
                    + drive  
                    + "\")\n"  
                    + "Wscript.Echo objDrive.SerialNumber"; // see note  
            fw.write(vbs);  
            fw.close();  
            Process p = Runtime.getRuntime().exec(  
                    "cscript //NoLogo " + file.getPath());  
            BufferedReader input = new BufferedReader(new InputStreamReader(  
                    p.getInputStream()));  
            String line;  
            while ((line = input.readLine()) != null) {  
                result += line;  
  
            }  
            input.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result.trim();  
    }
    
    /**
     * 获取CPU序列号
     * @return 
     */
	public static String getCpu() {
    	String serial = null; 
    	Scanner sc =null;
    	try {

			  Process process = Runtime.getRuntime().exec(
			  new String[] { "wmic", "cpu", "get", "ProcessorId" });

			  process.getOutputStream().close();

			  sc = new Scanner(process.getInputStream());
			  
			  sc.next();
			  
			  serial = sc.next();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return serial;

   }
    
    public static String getLocalCode(){
    	String str = getCpu().substring(getCpu().length()-2, getCpu().length()) 
    			+ getMotherboardSN().substring(getMotherboardSN().length()-2, getMotherboardSN().length()) 
    			+ getSerialNumber("C").substring(getSerialNumber("C").length()-2, getSerialNumber("C").length());
    	return str;
    }
    
    public static String getRegistrationCode(){
    	String str = Md5Util.getMd5(getLocalCode());
    	char[] arr = str.toCharArray();
    	String code = "";
    	for (int i = 0; i < arr.length; i++) {
			if(i%2==0){
				continue;
			}
			code += arr[i];
		}
    	code = code.substring(8, 16);
    	return code;
    }
    
    public static void main(String[] args) {
		System.out.println(getRegistrationCode());
	}

}
