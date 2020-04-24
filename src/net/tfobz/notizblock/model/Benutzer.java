package net.tfobz.notizblock.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Ein Benutzer ist gleich einem anderen Benutzer wenn Benutzername und
 * Passwort gleich sind
 *
 */
@XmlRootElement
public class Benutzer implements Comparable
{
	protected Integer nummer = 0;
	protected String benutzername = null;
	protected String passwort = null;

	public Benutzer() {
	}
	public Benutzer(Integer nummer, String benutzername, String passwort) {
		this.nummer = nummer;
		this.benutzername = benutzername;
		this.passwort = passwort;
	}
	
	@Override
	public int compareTo(Object o) {
		int ret = -1;
		if (o instanceof Benutzer)
			ret = (benutzername + passwort).compareTo(
					((Benutzer)o).benutzername + ((Benutzer)o).passwort);
		return ret;
	}
	
	@Override
	public boolean equals(Object o) {
		boolean ret = false;
		if (o instanceof Benutzer)
			ret = this.compareTo(o) == 0;
		return ret;
	}
	
	@Override
	public String toString() {
		return nummer + " " + benutzername + " " + passwort;
	}
	
	@Override
	public Benutzer clone() {
		return new Benutzer(this.nummer, this.benutzername, this.passwort);
	}
	
	public Integer getNummer() {
		return nummer;
	}
	public void setNummer(Integer nummer) {
		this.nummer = nummer;
	}
	public String getBenutzername() {
		return benutzername;
	}
	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}
	public String getPasswort() {
		return passwort;
	}
	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}	
}
