package jp.co.apasys.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Zip {
	/**
	 * 引数jsonの格納場所
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * */
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		
		//　引数
		String zipFilePath = "";
		String zipFilePassword = "";
		String zipListRootPath = "";
		List<File> addFileList = new ArrayList<File>();
		
		JSONParser parser = new JSONParser();
		
		JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(new File(args[0])));
		
		zipFilePath = (String) jsonObject.get("zip_file_path");
		zipFilePassword = (String) jsonObject.get("zip_file_password");
		JSONArray zipListJsonArray = (JSONArray)jsonObject.get("zip_list");
		Object[] zipListIterator = (Object[]) zipListJsonArray.toArray();
        for (Object zipFile : zipListIterator) {
        	addFileList.add(new File(zipFile.toString()));
		}
        zipListRootPath = (String) jsonObject.get("zip_list_root_path");
		
        try {
    		ZipParameters zipParameters = new ZipParameters();
    		ZipFile zipFile = null;
    		if (zipFilePassword != null && !zipFilePassword.isEmpty()) {
        		zipParameters.setEncryptFiles(true);
        		zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
    			zipFile = new ZipFile(zipFilePath, zipFilePassword.toCharArray());
    		} else {
    			zipParameters.setEncryptFiles(false);
    			zipFile = new ZipFile(zipFilePath);
    		}
    		for (File file : addFileList) {
    			zipParameters.setFileNameInZip(file.getAbsolutePath().replace(zipListRootPath, ""));
    			zipFile.addFile(file, zipParameters);
    			
			}
    		
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(2);
		}
        
        System.exit(0);
	}
	
}
