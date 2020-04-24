package net.tfobz.notizblock.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Ein Thema ist gleich einem anderen Thema wenn die Beschreibungen
 * gleich sind
 *
 */
@XmlRootElement
public class Thema implements Comparable
{
	protected Integer nummer = 0;
	protected String beschreibung = null;
	
	public Thema() {
	}
	public Thema(Integer nummer, String beschreibung) {
		this.nummer = nummer;
		this.beschreibung = beschreibung;
	}
	
	@Override
	public int compareTo(Object o) {
		int ret = -1;
		if (o instanceof Thema)
			ret = (beschreibung).compareTo(((Thema)o).beschreibung);
		return ret;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if (o instanceof Thema)
			ret = (beschreibung).compareTo(((Thema)o).beschreibung) == 0;
		return ret;
	}
	
	@Override
	public String toString() {
		return nummer + " " + beschreibung;
	}
	
	@Override
	public Thema clone() {
		return new Thema(this.nummer, this.beschreibung);
	}
	
	public Integer getNummer() {
		return nummer;
	}
	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}
	public String getBeschreibung() {
		return beschreibung;
	}
	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}
}
