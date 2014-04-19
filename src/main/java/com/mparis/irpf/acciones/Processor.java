package com.mparis.irpf.acciones;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mparis.irpf.acciones.Operacion.Tipo;

public class Processor {

	private static final Logger log = LoggerFactory.getLogger(Processor.class);

	public static void process2013(OperDataSet dataSet) {
		Map<Integer, List<Transmision>> transmisiones = new HashMap<Integer, List<Transmision>>();
		Queue<Operacion> compras = new LinkedList<Operacion>();
		List<Operacion> ventas = new ArrayList<Operacion>();

		for (Operacion op : dataSet.getOperaciones()) {
			if (Tipo.COMPRA.equals(op.getTipo())) {
				compras.add(op);
			} else if (Tipo.VENTA.equals(op.getTipo())) {
				ventas.add(op);
			}
		}

		Operacion c = compras.remove();
		int restTit = c.getNumTitulos();
		int currentYear = 0;
		float totalVentas = 0;
		float totalAllCompras = 0;
		List<Transmision> trans = new ArrayList<Transmision>();
		for (Operacion v : ventas) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(v.getFecha());

			int year = cal.get(Calendar.YEAR);
			if (year > currentYear) {
				log.info("{} {} {}", new Object[] { totalVentas,
						totalAllCompras, totalVentas + totalAllCompras });

				if (currentYear != 0) {
					transmisiones.put(currentYear, trans);
					trans = new ArrayList<Transmision>();
				}

				currentYear = year;
				totalVentas = 0;
				totalAllCompras = 0;
				log.info("");
				log.info("{}", year);
			}

			totalVentas += v.getLiquido();

			log.info("{} {} {}",
					new Object[] { Operacion.TIME_FORMAT.format(v.getFecha()),
							v.getNumTitulos(), v.getLiquido() });

			int vTit = v.getNumTitulos();
			float totalCompras = 0;
			do {
				int consumeTit = Math.min(restTit, vTit);
				vTit -= consumeTit;
				restTit -= consumeTit;

				Transmision t = new Transmision(consumeTit, c, v);
				trans.add(t);
				totalCompras -= t.getCompraLiquido();
				log.info("\t{}", t);

				if (restTit == 0) {
					try {
						c = compras.remove();
					} catch (NoSuchElementException e) {
						log.debug("No hay más compras");
						break;
					}
					restTit = c.getNumTitulos();
				}
			} while (vTit > 0);

			log.info("\t{} {} {}", new Object[] { v.getLiquido(), totalCompras,
					v.getLiquido() + totalCompras });

			totalAllCompras += totalCompras;
		}

		log.info("{} {} {}", new Object[] { totalVentas, totalAllCompras,
				totalVentas + totalAllCompras });
		transmisiones.put(currentYear, trans);

		/* Transmisiones */
		log.info("\n\n");
		List<Integer> keys = new ArrayList<Integer>(transmisiones.keySet());
		Collections.sort(keys);
		for (Integer i : keys) {
			float[] liquidosOneYearOrLess = new float[3];
			float[] liquidosMoreOneYear = new float[3];
			for (Transmision t : transmisiones.get(i)) {
				if (t.isOneYearOrLess()) {
					liquidosOneYearOrLess[0] -= t.getCompraLiquido();
					liquidosOneYearOrLess[1] += t.getVentaLiquido();
					liquidosOneYearOrLess[2] += t.getLiquido();
				} else {
					liquidosMoreOneYear[0] -= t.getCompraLiquido();
					liquidosMoreOneYear[1] += t.getVentaLiquido();
					liquidosMoreOneYear[2] += t.getLiquido();
				}
			}

			log.info("{}", i);
			log.info("Un año o menos");
			log.info("{}", liquidosOneYearOrLess);
			log.info("Más de un año");
			log.info("{}", liquidosMoreOneYear);
		}
	}

}
