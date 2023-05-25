package com.infy.reducer.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;


@Service
public class CompressedFile {
	public static final Properties properties = new Properties() ;
	static {
		try(InputStream inputStream = CompressedFile.class.getClassLoader().getResourceAsStream("application.properties")){
			properties.load(inputStream);
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}
	public static void bytetoFile(byte[] jsonData) {
		try {
			
			String FILEPATH = properties.getProperty("compressedFile") ;
			 File file = new File(FILEPATH) ;
			OutputStream os = new FileOutputStream(file) ;
			os.write(jsonData) ;
			os.close() ;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
