package de.htwg_konstanz.ebus.wholesaler.exceptions;

import de.htwg_konstanz.ebus.wholesaler.main.ImportDom;

public class SupplierNotExistsException extends Exception {

    /**
     * Just to know what Exception :) {@link ImportDom}
     */
    private static final long serialVersionUID = -7087986092312111600L;
    
    public SupplierNotExistsException() {
        return;
    }

}
