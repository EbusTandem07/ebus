package de.htwg_konstanz.ebus.wholesaler.exceptions;

import de.htwg_konstanz.ebus.wholesaler.action.UploadAction;

public class NoFileChosenException extends Exception {

    /**
     * will be thrown if no file is chosen due {@link UploadAction}
     */
    private static final long serialVersionUID = -6956170229560974057L;

    public NoFileChosenException() {
        return;
    }

}
