package com.mparis.irpf.acciones;

import java.util.Date;

public class Operacion {

	/* @formatter:off */
	public enum Tipo {
		COMPRA, VENTA;
	}

	/* @formatter:on */

	private final Tipo tipo;
	private Date fecha;
	private int numTitulos;
	private float cambio;
	private float bruto;
	private float canon;
	private float comision;
	private float gastosTotales;
	private float liquido;

	public Tipo getTipo() {
		return tipo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getNumTitulos() {
		return numTitulos;
	}

	public void setNumTitulos(int numTitulos) {
		this.numTitulos = numTitulos;
	}

	public float getCambio() {
		return cambio;
	}

	public void setCambio(float cambio) {
		this.cambio = cambio;
	}

	public float getBruto() {
		return bruto;
	}

	public void setBruto(float bruto) {
		this.bruto = bruto;
	}

	public float getCanon() {
		return canon;
	}

	public void setCanon(float canon) {
		this.canon = canon;
	}

	public float getComision() {
		return comision;
	}

	public void setComision(float comision) {
		this.comision = comision;
	}

	public float getGastosTotales() {
		return gastosTotales;
	}

	public void setGastosTotales(float gastosTotales) {
		this.gastosTotales = gastosTotales;
	}

	public float getLiquido() {
		return liquido;
	}

	public void setLiquido(float liquido) {
		this.liquido = liquido;
	}

	private Operacion(Builder b) {
		this.tipo = b.tipo;
		this.fecha = b.fecha;
		this.numTitulos = b.numTitulos;
		this.cambio = b.cambio;
		this.bruto = b.bruto;
		this.canon = b.canon;
		this.comision = b.comision;
		this.gastosTotales = b.gastosTotales;
		this.liquido = b.liquido;
	}

	public static class Builder {

		private Tipo tipo;
		private Date fecha;
		private int numTitulos;
		private float cambio;
		private float bruto;
		private float canon;
		private float comision;
		private float gastosTotales;
		private float liquido;

		public Builder(Tipo tipo, float liquido) {
			this.tipo = tipo;
			this.liquido = liquido;
		}

		public Builder fecha(Date fecha) {
			this.fecha = fecha;
			return this;
		}

		public Builder numTitulos(int numTitulos) {
			this.numTitulos = numTitulos;
			return this;
		}

		public Builder cambio(float cambio) {
			this.cambio = cambio;
			return this;
		}

		public Builder bruto(float bruto) {
			this.bruto = bruto;
			return this;
		}

		public Builder canon(float canon) {
			this.canon = canon;
			return this;
		}

		public Builder comision(float comision) {
			this.comision = comision;
			return this;
		}

		public Builder gastosTotales(float gastosTotales) {
			this.gastosTotales = gastosTotales;
			return this;
		}

		public Builder liquido(float liquido) {
			this.liquido = liquido;
			return this;
		}

		public Operacion build() {
			return new Operacion(this);
		}
	}

}
