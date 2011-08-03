package com.safeng.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.itcast.service.FileService;
import android.test.AndroidTestCase;

public class MyTest extends AndroidTestCase{

	public void testDB() throws Throwable{
		Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();
		data.put(1, 123);
		FileService service = new FileService(getContext());
		service.save("asdasd",data);
		Map<Integer, Integer> data1 = service.getData("asdasd");
		System.out.println(""+data1.get(1));
	}
	
	
	
}
