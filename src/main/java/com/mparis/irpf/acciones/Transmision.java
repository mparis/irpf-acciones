package com.mparis.irpf.acciones;

import java.util.Calendar;
import java.util.Date;

public class Transmision {

	private int numTitulos;
	private Operacion compra;
	private Operacion venta;

	private boolean oneYearOrLess;
	private float compraLiquido;
	private float ventaLiquido;
	private float liquido;

	public int getNumTitulos() {
		return numTitulos;
	}

	public Operacion getCompra() {
		return compra;
	}

	public Operacion getVenta() {
		return venta;
	}

	public boolean isOneYearOrLess() {
		return oneYearOrLess;
	}

	public float getCompraLiquido() {
		return compraLiquido;
	}

	public float getVentaLiquido() {
		return ventaLiquido;
	}

	public float getLiquido() {
		return liquido;
	}

	public Transmision(int numTitulos, Operacion compra, Operacion venta) {
		this.numTitulos = numTitulos;
		this.compra = compra;
		this.venta = venta;

		oneYearOrLess = oneYearOrLessDiff(compra.getFecha(), venta.getFecha());
		compraLiquido = numTitulos * compra.getLiquido()
				/ compra.getNumTitulos();
		ventaLiquido = numTitulos * venta.getLiquido() / venta.getNumTitulos();
		liquido = ventaLiquido - compraLiquido;
	}

	@Override
	public String toString() {
		return numTitulos + "\t" + compraLiquido + "\t" + ventaLiquido + "\t"
				+ liquido + "\t"
				+ Operacion.TIME_FORMAT.format(compra.getFecha()) + "\t"
				+ oneYearOrLess;
	}

	private static boolean oneYearOrLessDiff(Date first, Date last) {
		Calendar a = getCalendar(first);
		Calendar b = getCalendar(last);

		int diffY = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);

		if (diffY == 0) {
			return true;
		}

		int diffM = b.get(Calendar.MONTH) - a.get(Calendar.MONTH);
		int diffD = b.get(Calendar.DAY_OF_MONTH) - a.get(Calendar.DAY_OF_MONTH);

		if (diffY == 1 && ((diffM < 0) || (diffM == 0 && diffD <= 0))) {
			return true;
		}

		return false;
	}

	private static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		return cal;
	}

}
