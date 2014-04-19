package com.mparis.irpf.acciones;

import java.util.List;

public class OperDataSet {

	private List<Operacion> operaciones;

	public OperDataSet(List<Operacion> operaciones) {
		this.operaciones = operaciones;
	}

	public List<Operacion> getOperaciones() {
		return operaciones;
	}

}
