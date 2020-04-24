package net.tfobz.notizblock.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

/**
 * Implementiert die Notizenverwaltung auf die mehrere
 * Benutzer zugreifen können. Es werden nur die benötigten Methoden implementiert.
 * Auch werden die Daten nicht in einer Datenbank abgelegt
 */
public final class NotizblockIntern
{
	protected static HashMap<String, Benutzer> benutzer = new HashMap<String, Benutzer>();
	protected static HashMap<Integer, Thema> themen = new HashMap<Integer, Thema>();
	protected static HashMap<Integer, Notiz> notizen = new HashMap<Integer, Notiz>();
	protected static HashMap<Integer, ArrayList<Integer>> nichtGelesen = 
			new HashMap<Integer, ArrayList<Integer>>();
	
	/**
	 * Initialisiert die Listen so dass immer zwei Benutzer und vier Themen vorhanden sind
	 */
	static {
		init();
	}
	
	protected static void init() {
		benutzer.put("sepp", new Benutzer(1, "sepp", "sepp"));
		benutzer.put("rudi", new Benutzer(2, "rudi", "rudi"));
		
		themen.put(1, new Thema(1, "Bananen"));
		themen.put(2, new Thema(2, "Birnen"));
		themen.put(3, new Thema(3, "Kiwi"));
		themen.put(4, new Thema(4, "Bohnen"));
	}
	
	/**
	 * Holt alle Themen sortiert nach Beschreibung. Nur vorhandene
	 * Benutzer können Themen beziehen
	 * @param benutzername
	 * @param passwort
	 * @return null falls keine Themen gefunden werden konnten
	 */
	public ArrayList<Thema> getThemen(String benutzername, String passwort) {
		ArrayList<Thema> ret = null;
		if (benutzername != null && benutzername.length() > 0 &&
				passwort != null && passwort.length() > 0 
				&& getBenutzer(benutzername, passwort) != null) {
			if (themen != null && themen.size() > 0) {
				ret = new ArrayList<Thema>(themen.values());
				Collections.sort(ret);
			}
		}
		return ret;
	}
	
	/**
	 * Das Thema mit der übergebenen Nummer wird zurückgeliefert. Der übergebene
	 * Benutzer muss existieren
	 * Dabei wird das Originalthema geklont
	 * @param nummer
	 * @return null falls Thema nicht gefunden werden konnte
	 */
	public Thema getThema(String benutzername, String passwort, Integer nummer) {
		Thema ret = null;
		if (benutzername != null && benutzername.length() > 0 &&
				passwort != null && passwort.length() > 0 &&
				getBenutzer(benutzername, passwort) != null && 
				nummer != null && themen.get(nummer) != null)
			ret = themen.get(nummer).clone();
		return ret;
	}
	
	/**
	 * Holt den Benutzer mit dem übergebenen Passwort
	 * @param benutzername
	 * @param passwort
	 * @return null falls Benutzer mit diesem Passwort nicht gefunden
	 * werden konnte
	 */
	public Benutzer getBenutzer(String benutzername, String passwort) {
		Benutzer ret = null;
		if (benutzername != null && benutzername.length() > 0 &&
				passwort != null && passwort.length() > 0) {
			ret = benutzer.get(benutzername);
			if (ret == null || !ret.getPasswort().equals(passwort))
				ret = null;
		}
		return ret;
	}
	
	/**
	 * Alle Notizen werden zurück geliefert. Die Reihenfolge entspricht jener
	 * nach Themen. Dabei werden die Notizen abhängig vom übergebenen 
	 * Benutzernamen als gelesen bzw. nicht gelesen markiert. Der Benutzer
	 * muss mit Benutzername und korrektem Passwort existieren
	 * @param benutzername
	 * @param passwort
	 * @return null falls keine Notizen gefunden werden konnten
	 * @see getNotizen(benutzer, passwort, sortierung)
	 */
	public ArrayList<Notiz> getNotizen(String benutzername, String passwort) {
		return getNotizen(benutzername, passwort, "thema");
	}
	
	/** Die möglichen Sortierreihenfolgen lauten: 
	 * "thema" aufsteigend nach Thema absteigend nach Erstellungsdatum
	 * "datum" absteigend nach Erstellungsdatum und aufsteigend nach Thema
	 * "thema nicht gelesen" wie "thema" zeigt nur die nicht gelesenen
	 * "datum nicht gelesen" wie "datum" zeigt nur die nicht gelesenen
	 * @param benutzername
	 * @param passwort
	 * @param sortierung
	 * @return null falls keine Notizen gefunden werden konnten
	 */
	public ArrayList<Notiz> getNotizen(String benutzername, String passwort, String sortierung) {
		ArrayList<Notiz> ret = null;
		if (benutzername != null && benutzername.length() > 0 &&
				passwort != null && passwort.length() > 0 && 
				sortierung != null && (
				sortierung.equals("thema") || sortierung.equals("datum") || 
				sortierung.equals("thema nicht gelesen") || 
				sortierung.equals("datum nicht gelesen"))) {
			Benutzer benutzer = getBenutzer(benutzername, passwort);
			if (benutzer != null) {
				ret = new ArrayList<Notiz>(notizen.values());
				// Kontrolliere für jede Notiz ob sie nicht gelesen wurde
				for (Notiz n: ret) {
					ArrayList<Integer> ng = nichtGelesen.get(n.getNummer());
					if (ng != null && ng.contains(benutzer.nummer))
						n.setGelesen(false);
					else
						n.setGelesen(true);
				}
				if (sortierung.endsWith("nicht gelesen")) {
					// Streiche die gelesenen Notizen 
					int i = ret.size() - 1;
					while (i >= 0) {
						if (ret.get(i).gelesen)
							ret.remove(i);
						i--;
					}
				}
				// Sortierung wählen
				if (sortierung.startsWith("thema")) {
					// Soriterung nach Thema aufsteigend, Erstellungsdatum absteigend
					Collections.sort(ret, 
						new Comparator<Notiz>() {
							@Override
							public int compare(Notiz o1, Notiz o2) {
								int ret = o1.getThema().getBeschreibung().compareTo(o2.getThema().getBeschreibung());
								if (ret == 0)
									ret = (int)(getTime(o2.getErstellungsdatum()) - 
											getTime(o1.getErstellungsdatum()));
								return ret;
							}
						}
				  );
				} else {
					// Sortierung nach Datum absteigend, Thema aufsteigend
					Collections.sort(ret, 
						new Comparator<Notiz>() {
							@Override
							public int compare(Notiz o1, Notiz o2) {
								int ret = (int)(getTime(o2.getErstellungsdatum()) - 
									getTime(o1.getErstellungsdatum()));
								if (ret == 0)
									ret = o1.getThema().getBeschreibung().compareTo(o2.getThema().getBeschreibung());
								return ret;
							}
						}
				  );
				}
				if (ret != null && ret.size() == 0)
					ret = null;
			}
		}
		return ret;
	}
	
	/**
	 * Holt die Notiz mit der übergebenen Nummer. Dabei muss der übergebene
	 * Benutzer exisiteren. Es wird in der Notiz eingetragen dass der Benutzer
	 * die gefundene Notiz gelesen hat
	 * @param benutzername
	 * @param passwort
	 * @param nummer
	 * @return null falls Notiz nicht gefunden werden konnte
	 */
	public Notiz getNotiz(String benutzername, String passwort, Integer nummer) {
		Notiz ret = null;
		Benutzer benutzer = getBenutzer(benutzername, passwort);
		if (benutzer != null && nummer != null && notizen.get(nummer) != null) {
			ret = notizen.get(nummer).clone();
			// Kontrolliere ob Notiz nicht gelesen wurde
			ArrayList<Integer> ng = nichtGelesen.get(ret.getNummer());
			if (ng != null && ng.contains(benutzer.nummer))
				ret.setGelesen(false);
			else
				ret.setGelesen(true);
		}
		return ret;
	}
	
	/**
	 * Fügt neue Notiz hinzu. Dabei wird als Besitzer der Notiz der übergebene Benutzer eingetragen. 
	 * Der Besitzer muss existieren. Die Notiz wird validiert. Ist die Notiz fehlerhaft, dann wird 
	 * die Notiz mit den entsprechenden Fehlern zurückgeliefert. Die Nummer der übergebenen Notiz
	 * darf nicht schon vergeben worden sein. Konnte die Notiz erfolgreich 
	 * hinzugefügt werden, so wird die Notiz mit ihrer neuen Nummer, dem akutellen Ertellungsdatum
	 * und ohne Fehler zurück geliefert. Weiters wird diese Notiz für alle anderen Benutzer außer
	 * dem Besitzer als nicht gelesen markiert
	 * @param benutzer
	 * @param passwort
	 * @param notiz
	 * @return
	 */
	public Notiz hinzufuegenNotiz(String benutzername, String passwort, Notiz notiz) {
		Notiz ret = null;
		if (benutzername != null && benutzername.length() > 0 &&
				passwort != null && passwort.length() > 0 &&
				notiz != null) {
			ret = notiz.clone();
			ret.validiere();
			Benutzer benutzer = getBenutzer(benutzername, passwort);
			if (benutzer == null) {
				if (ret.fehler == null)
					ret.fehler = new NotizFehler();
				ret.fehler.setBenutzer("Besitzer existiert nicht");
			} else
				ret.setBenutzer(benutzer);
			if (ret.nummer != -1 && NotizblockIntern.notizen.containsKey(ret.nummer)) {
				if (ret.fehler == null)
					ret.fehler = new NotizFehler();
				ret.fehler.setNummer("Notiz mit dieser Nummer existiert bereits");
			}
			if (ret.fehler == null) {
				ret.erstellungsdatum = getZeitstempel();
				int nummer = 0;
				do {
					nummer = notiz.hashCode() + (int)(Math.random() * 1000);
				} while (notizen.containsKey(nummer));
				ret.nummer = nummer;
				ret.gelesen = true;
				notizen.put(nummer, ret);
				// Notiz wird für andere Benutzer als nicht gelesen markiert
				setNichtGelesen(benutzer, ret);
				ret = ret.clone();
			}
		}
		return ret;
	}
	
	/**
	 * Es wird nur dann die Notiz geändert, wenn diese vorher existiert hat.
	 * Dabei wird das Änderungsdatum automatisch aktualisiert und die 
	 * geänderte Notiz zurück geliefert. Wenn die Änderung nicht durchgeführt
	 * werden konnte, wird die Notiz mit dem Fehler zurück geliefert. Eine 
	 * geänderte Notiz wird als nicht gelesen markiert. Nur 
	 * der Ersteller der Notiz darf diese ändern
	 * @param benutzername
	 * @param passwort
	 * @param notiz
	 * @return null falls Notiz nicht geändert werden konnte
	 */
	public Notiz aendernNotiz(String benutzername, String passwort, Notiz notiz) {
		Notiz ret = null;
		if (benutzername != null && benutzername.length() > 0 &&
				passwort != null && passwort.length() > 0 && notiz != null) {
			ret = notiz.clone();
			ret.validiere();
			Benutzer benutzer = getBenutzer(benutzername, passwort);
			if (benutzer == null || ret.getBenutzer() == null ||
					ret.getBenutzer() != null &&
					!ret.getBenutzer().getBenutzername().equals(benutzername)) {
				ret.fehler = new NotizFehler();
				ret.fehler.setBenutzer("Besitzer existiert nicht oder wurde geändert");
			} else {
				if (notiz.nummer == -1 || notizen.get(notiz.nummer) == null) {
					if (ret.fehler == null)
						ret.fehler = new NotizFehler();
					ret.fehler.nummer = "Notiz mit dieser Nummer exisitert nicht";
				}
			}
			if (ret.fehler == null) {
				ret.aenderungsdatum = getZeitstempel();
				notizen.put(ret.nummer, ret);
				setNichtGelesen(benutzer, ret);
				ret = ret.clone();
			}
		}
		return ret;
	}

	/**
	 * Die gelöschte Notiz wird zurück geliefert. Konnte die Notiz nicht 
	 * gelöscht werden, wird null zurück geliefert. Nur der Ersteller einer
	 * Notiz darf diese löschen
	 * @param benutzername
	 * @param passwort
	 * @param nummer
	 * @return null falls Notiz nicht gefunden oder nicht gelöscht werden konnte
	 */
		public Notiz loeschenNotiz(String benutzername, String passwort, Integer nummer) {
		Notiz ret = null;
		if (benutzername != null && benutzername.length() > 0 &&
				passwort != null && passwort.length() > 0 &&
				nummer != null) {
			Benutzer benutzer = getBenutzer(benutzername, passwort);
			if (benutzer != null) {
				ret = notizen.get(nummer);
				if (ret != null &&  benutzer.getNummer() == ret.getBenutzer().getNummer()) {
					notizen.remove(nummer);
					nichtGelesen.remove(nummer);
				} else
					ret = null;
			}
		}
		return ret;
	}
	

	/**
	 * Es wird für die Notiz abgespeichert, dass der Benutzer diese gelesen 
	 * hat. Konnte der Benutzer oder die Notiz nicht gefunden werden, so wird null zurück
	 * geliefert ansonsten die geänderte Notiz
	 * @param benutzername
	 * @param passwort
	 * @param nummer
	 * @return
	 */
	public Notiz setGelesenNotiz(String benutzername, String passwort, Integer nummer) {
		Notiz ret = null;
		if (benutzername != null && benutzername.length() > 0 &&
				passwort != null && passwort.length() > 0 &&
				nummer != null) {
			Benutzer benutzer = getBenutzer(benutzername, passwort);
			if (benutzer != null && notizen.get(nummer) != null) {
				ret = notizen.get(nummer).clone();
				ret.setGelesen(true);
				nichtGelesen.get(nummer).remove(benutzer.nummer);
			}
		}
		return ret;
	}
	
	/*
	 * Hilfsmethoden
	 */
	/**
	 * Für alle Benutzer außer dem übergebenen wird die Notiz als nicht gelesen
	 * markiert
	 * @param benutzer
	 * @param notiz
	 */
	protected void setNichtGelesen(Benutzer benutzer, Notiz notiz) {
		ArrayList<Integer> intListe = new ArrayList<Integer>();
		for (Benutzer b: NotizblockIntern.benutzer.values()) {
			if (!b.equals(benutzer))
				intListe.add(b.nummer);
		}
		nichtGelesen.put(notiz.nummer, intListe);
	}
	
	protected static String getZeitstempel() {
		String ret = null;
		DateFormat df = SimpleDateFormat.getDateTimeInstance();
		ret = df.format(new Date().getTime());
		return ret;
	}
	
	static protected long getTime(String s) {
		long ret = 0;
		DateFormat df = SimpleDateFormat.getDateTimeInstance();
		try {
			ret = df.parse(s).getTime();
		} catch (ParseException e) {
			System.out.println("Konvertierungsfehler Zeitstempel");
		}
		return ret;
	}
}
