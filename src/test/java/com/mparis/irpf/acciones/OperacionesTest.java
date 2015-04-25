package com.mparis.irpf.acciones;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class OperacionesTest {

	@Test
	public void operacionesTest() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("operaciones.txt").getFile());
		System.out.println(file.getAbsolutePath());

		Path path = Paths.get(file.getAbsolutePath());
		OperDataSet dataSet = OperLoader.loadOperDataset(path);
		Processor.process2013(dataSet);
	}

}
