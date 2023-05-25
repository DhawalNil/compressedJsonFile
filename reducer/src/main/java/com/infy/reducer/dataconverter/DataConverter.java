package com.infy.reducer.dataconverter;


public interface DataConverter<T> {
	
	T jsonToJavaObject(String jsonData ) throws Exception;
	String javaObjectToJson(Object t1) throws Exception ;
}
