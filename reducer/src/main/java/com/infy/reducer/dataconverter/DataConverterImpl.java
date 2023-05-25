package com.infy.reducer.dataconverter;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataConverterImpl<T> implements DataConverter<T> {
	
	public static final Properties properties = new Properties() ;
	static {
		try(InputStream inputStream = DataConverterImpl.class.getClassLoader().getResourceAsStream("application.properties")){
			properties.load(inputStream);
		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}

	private static ObjectMapper objectMapper = new ObjectMapper();

	private final Class<T> entityClass;

	public DataConverterImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public DataConverterImpl() {
		this.entityClass = null;
	}

	@Override
	public T jsonToJavaObject(String jsonData) throws Exception {
		return objectMapper.readValue(jsonData, entityClass);
	}

	@Override
	public String javaObjectToJson(Object object) throws Exception {
		
		String routes = properties.getProperty("jsonOutputFile");
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(routes), object);
		return objectMapper.writeValueAsString(object);
	}
}
