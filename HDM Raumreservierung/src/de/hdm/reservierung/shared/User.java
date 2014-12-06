package de.hdm.reservierung.shared;


public class User extends BusinessObjects  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vorname, nachname, rolle, kuerzel, mail;
	/**
	 * @return the name
	 */
	public String getVorname() {
		return vorname;
	}
	/**
	 * @param name the name to set
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	/**
	 * @return the nachname
	 */
	public String getNachname() {
		return nachname;
	}
	/**
	 * @param nachname the nachname to set
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	/**
	 * @return the rolle
	 */
	public String getRolle() {
		return rolle;
	}
	/**
	 * @param rolle the rolle to set
	 */
	public void setRolle(String rolle) {
		this.rolle = rolle;
	}
	/**
	 * @return the kuerzel
	 */
	public String getKuerzel() {
		return kuerzel;
	}
	/**
	 * @param kuerzel the kuerzel to set
	 */
	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}
	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}
	
}