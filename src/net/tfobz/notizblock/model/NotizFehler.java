package net.tfobz.notizblock.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Fehlerobjekt enthält die Fehlermeldungen für die einzelnen
 * Eigenschaften der Notiz
 *
 */
@XmlRootElement
public class NotizFehler
{
	protected String nummer = null;
	protected String ueberschrift = null;
	protected String text = null;
	protected String thema = null;
	protected String benutzer = null;
	
	public NotizFehler() {
	}
	public NotizFehler(
			String nummer,
			String ueberschrift,
			String text,
			String thema,
			String benutzer) {
		this.nummer = nummer;
		this.ueberschrift = ueberschrift;
		this.text = text;
		this.thema = thema;
		this.benutzer = benutzer;
	}
	
	@Override
	public String toString() {
		String ret = null;
		if (nummer != null && nummer.length() > 0)
			ret = nummer;
		if (ueberschrift != null && ueberschrift.length() > 0)
			if (ret == null)
				ret = ueberschrift;
			else
				ret = ret + " " + ueberschrift;
		if (text != null && text.length() > 0)
			if (ret == null)
				ret = text;
			else
				ret = ret + " " + text;
		if (thema != null)
			if (ret == null)
				ret = thema;
			else
				ret = ret + " " + thema;
		if (benutzer != null)
			if (ret == null)
				ret = benutzer;
			else
				ret = ret + " " + benutzer;
		return ret;
	}
	
	@Override
	public Object clone() {
		return new NotizFehler(nummer, ueberschrift, text, thema, benutzer);
	}
	
	public boolean hatFehler() {
		return ueberschrift != null && ueberschrift.length() > 0 ||
				text != null && text.length() > 0 ||
				thema != null && thema.length() > 0 ||
				benutzer != null && benutzer.length() > 0;
	}
	
	public String getNummer() {
		return nummer;
	}
	public void setNummer(String nummer) {
		this.nummer = nummer;
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
	public String getThema() {
		return thema;
	}
	public void setThema(String thema) {
		this.thema = thema;
	}
	public String getBenutzer() {
		return benutzer;
	}
	public void setBenutzer(String benutzer) {
		this.benutzer = benutzer;
	}
	
	
}
