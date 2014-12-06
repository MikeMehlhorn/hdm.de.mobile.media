package de.hdm.reservierung.client;

import com.google.gwt.core.client.GWT;





public class ClientsideSettings  {

	
	 private static ServiceAsync service = null;
	 
	 
	  public static ServiceAsync getService() {
		    // Gab es bislang noch keine Raumverwalung-Instanz, dann...
		    if (service == null) {
		      // ZunÃ¤chst instantiieren wir BankAdministration
		    	service = GWT.create(Service.class);
		    }

		    // So, nun brauchen wir die BankAdministration nur noch zurÃ¼ckzugeben.
		    return service;
		  }
	
}
