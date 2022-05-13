package es.uniovi.pulso.colmena.model.indicators;

import es.uniovi.pulso.colmena.model.enumeration.IndicatorType;

public class ColmenaIndicator {

	private String name;
	private String value;
	private IndicatorType type;

	public ColmenaIndicator(String name, String value, IndicatorType type) {
		super();
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public ColmenaIndicator(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public ColmenaIndicator(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public IndicatorType getType() {
		return type;
	}

	public void setType(IndicatorType type) {
		this.type = type;
	}

}
