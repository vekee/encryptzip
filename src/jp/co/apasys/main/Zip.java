package jp.co.apasys.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class Zip {
	/**
	 * args[0] filePath
	 *  ・・・
	 * args[n-1] zipFilePath
	 * args[n] password
	 * */
	public static void main(String[] args) {
		
		//　引数
		List<File> filesToAdd = new ArrayList<File>();
		String zipFilePath = "";
		String password = "";
		
		if (args == null || args.length < 2) {
			System.exit(1);
		}
		
		password = args[args.length-1];
		zipFilePath = args[args.length-2];
        
        try {
    		ZipParameters zipParameters = new ZipParameters();
    		
    		for (int i=0;i<=args.length-3;i++) {
    			filesToAdd.add(new File(args[i]));
    		}
    		
    		ZipFile zipFile = null;
    		if (password != null && !password.isEmpty()) {
        		zipParameters.setEncryptFiles(true);
        		zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
    			zipFile = new ZipFile(zipFilePath, password.toCharArray());
    		} else {
    			zipParameters.setEncryptFiles(false);
    			zipFile = new ZipFile(zipFilePath);
    		}
    		zipFile.addFiles(filesToAdd, zipParameters);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}
        
        System.exit(0);
	}
	
}
