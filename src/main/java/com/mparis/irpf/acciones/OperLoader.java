package com.mparis.irpf.acciones;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mparis.irpf.acciones.Operacion.Tipo;

public class OperLoader {

	private static final Logger log = LoggerFactory.getLogger(OperLoader.class);

	public static OperDataSet loadOperDataset(Path file) throws IOException {
		String lineSeparator = System.getProperty("line.separator");

		try (Scanner scanner = new Scanner(file)) {
			scanner.useDelimiter(lineSeparator);

			List<Operacion> operaciones = new ArrayList<Operacion>();
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				Operacion op;
				try {
					op = createFromText(line);
				} catch (IllegalArgumentException e) {
					continue;
				}

				operaciones.add(op);
			}

			return new OperDataSet(operaciones);
		}
	}

	private static Operacion createFromText(String str) {
		log.debug("{}", str);
		String[] tokens = str.split("\t+");

		if (tokens.length != 9) {
			throw new IllegalArgumentException(
					"String does not contains all expected fields");
		}

		Operacion.Tipo tipo = Tipo.valueOf(tokens[0]);

		String[] fechaTokens = tokens[1].split("/");
		int month = Integer.valueOf(fechaTokens[0]) - 1;
		int day = Integer.valueOf(fechaTokens[1]);
		int year = Integer.valueOf(fechaTokens[2]);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		Date fecha = cal.getTime();

		int numTitulos = Math.abs(Integer.valueOf(tokens[2]));
		float cambio = Float.valueOf(tokens[3]);
		float bruto = Math.abs(Float.valueOf(tokens[4]));
		float canon = Float.valueOf(tokens[5]);
		float comision = Float.valueOf(tokens[6]);
		float gastosTotales = Float.valueOf(tokens[7]);
		float liquido = Math.abs(Float.valueOf(tokens[8]));

		Operacion.Builder b = new Operacion.Builder(tipo, liquido);
		b.fecha(fecha);
		b.numTitulos(numTitulos);
		b.cambio(cambio);
		b.bruto(bruto);
		b.canon(canon);
		b.comision(comision);
		b.gastosTotales(gastosTotales);

		return b.build();
	}

}
