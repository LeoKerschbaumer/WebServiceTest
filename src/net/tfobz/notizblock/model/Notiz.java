package net.tfobz.notizblock.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Eine Notiz ist gleich einer anderen Notiz wenn deren Nummern gleich sind.
 * Die Nummer einer Notiz kann nicht geändert werden sondern wird beim
 * Speichern automatisch vergeben. nummer = -1 bedeutet dass Notiz noch 
 * nicht gespeichert wurde.
 * Erstellungs- und Änderungsdatum können nicht geändert werden
 *
 */
@XmlRootElement
public class Notiz implements Comparable
{
	protected Integer nummer = -1;
	protected String ueberschrift = null;
	protected String text = null;
	protected String erstellungsdatum = null;
	protected String aenderungsdatum = null;
	protected Boolean gelesen = false;
	protected Thema thema = null;
	protected Benutzer benutzer = null;
	protected NotizFehler fehler = null;
	
	public Notiz() {
	}
	public Notiz(String ueberschrift, String text,
		String erstellungsdatum, String aenderungsdatum, boolean gelesen,
		Thema thema, Benutzer benutzer, NotizFehler fehler) {
		this.ueberschrift = ueberschrift;
		this.text = text;
		this.erstellungsdatum = erstellungsdatum;
		this.aenderungsdatum = aenderungsdatum;
		this.gelesen = gelesen;
		this.thema = thema;
		this.benutzer = benutzer;
		this.fehler = fehler;
	}
	
	public void validiere() {
		fehler = null;
		if (ueberschrift == null || ueberschrift.length() == 0) {
			fehler = new NotizFehler();
			fehler.setUeberschrift("Überschrift nicht gesetzt");
		}
		if (text == null || text.length() == 0) {
			if (fehler == null)
				fehler = new NotizFehler();
			fehler.setText("Text nicht gesetzt");
		}
		if (thema == null) {
			if (fehler == null)
				fehler = new NotizFehler();
			fehler.setThema("Thema nicht gesetzt");
		}
	}
	
	@Override
	public int compareTo(Object o) {
		int ret = -1;
		if (o instanceof Notiz)
			ret = nummer - ((Notiz)o).nummer;
		return ret;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if (o instanceof Notiz)
			ret = this.compareTo(o) == 0;
		return ret;
	}
	
	@Override
	public String toString() {
		return nummer + " " + ueberschrift + " " + text + " " +
				erstellungsdatum + " " + aenderungsdatum + " " +
				gelesen + " (" + thema + "), (" + benutzer + "), (" + fehler + ')';
	}
	
	@Override
	public Notiz clone() {
		Notiz ret = new Notiz(
				this.ueberschrift,
				this.text,
				this.erstellungsdatum,
				this.aenderungsdatum,
				this.gelesen,
				this.thema,
				this.benutzer,
				this.fehler);
		ret.nummer = this.nummer;
		return ret;
	}
	
	public NotizFehler getFehler() {
		return this.fehler;
	}
	public void setFehler(NotizFehler fehler) {
		this.fehler = fehler;
	}
	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}
	public Integer getNummer() {
		return nummer;
	}
	public String getUeberschrift() {
		return ueberschrift;
	}
	public void setUeberschrift(String ueberschrift) {
		this.ueberschrift = ueberschrift;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getErstellungsdatum() {
		return erstellungsdatum;
	}
	public String getAenderungsdatum() {
		return aenderungsdatum;
	}
	public boolean getGelesen() {
		return gelesen;
	}
	public void setGelesen(boolean gelesen) {
		this.gelesen = gelesen;
	}
	public Thema getThema() {
		return thema;
	}
	public void setThema(Thema thema) {
		this.thema = thema;
	}
	public Benutzer getBenutzer() {
		return benutzer;
	}
	public void setBenutzer(Benutzer benutzer) {
		this.benutzer = benutzer;
	}
}
