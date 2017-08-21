package util;

import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
  
public class Md5Util {  
  
    //获取MD5密文
    public static String getMd5(String password) {  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            md.update(password.getBytes());  
            byte b[] = md.digest();  
  
            int i;  
  
            StringBuffer buf = new StringBuffer("");  
            for (int offset = 0; offset < b.length; offset++) {  
                i = b[offset];  
                if (i < 0)  
                    i += 256;  
                if (i < 16)  
                    buf.append("0");  
                buf.append(Integer.toHexString(i));  
            }  
            //32密文 
            return buf.toString();  
            // 16密文  
            //return buf.toString().substring(8, 24);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
            return null;  
        }  
  
    }  
      
/*    public static void main(String[] args) {      
    	System.out.println(Md5Util.getMd5("hello"));  
    }  */
    
}

