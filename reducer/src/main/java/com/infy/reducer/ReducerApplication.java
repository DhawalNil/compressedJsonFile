package com.infy.reducer;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.infy.reducer.datacompressor.DataCompressor;
import com.infy.reducer.datacompressor.DataCompressorImpl;
import com.infy.reducer.dataconverter.DataConverter;
import com.infy.reducer.dataconverter.DataConverterImpl;
import com.infy.reducer.file.CompressedFile;

@SpringBootApplication
public class ReducerApplication {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(ReducerApplication.class, args);

		try {
			Properties properties = new Properties();
			try (InputStream inputStream = ReducerApplication.class.getClassLoader()
					.getResourceAsStream("application.properties")) {
				properties.load(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}

//          <----------------Fetching Class From application.properties file-------------------->

			String ENTITY_NAME = properties.getProperty("entity_name");
			Class<?> entityClass = Class.forName(ENTITY_NAME);

			DataConverter<?> dataConverter = new DataConverterImpl<>(entityClass);
			DataCompressor<?> dataCompressor = new DataCompressorImpl<>(dataConverter);

//			<-------------------Json to Java Object--------------------------------------------->

			String path = properties.getProperty("jsonInputFile");
//			System.out.println(path);

			String json = readFileAsString(path);

			Object convertedObject = dataConverter.jsonToJavaObject(json);

			byte[] compressedData = dataCompressor.compress(convertedObject);

//			<---------------------Compressed File----------------------------------------------->

			CompressedFile.bytetoFile(compressedData);

//          <-------------------------JavaObject to Json --------------------------------------->

			Object decompressedData = dataCompressor.decompress(compressedData);

			String result = dataConverter.javaObjectToJson(decompressedData);

			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String readFileAsString(String path) throws Exception {
		return new String(Files.readAllBytes(Paths.get(path)));
	}

}
