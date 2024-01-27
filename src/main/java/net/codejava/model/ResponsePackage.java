/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package net.codejava.model;

/**
 * This is a generic envelope for messages being passed between the client and server.  It contains 3 components:
 * <ol><li>A boolean indicating if the call was successful or not</li>
 * <li>An error message that may be supplied/referenced if there was a problem</li>
 * <li>Any data being returned from the server.</li></ol>
 * @author joe_d
 */
public class ResponsePackage {

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    private Object data;
    private boolean valid;
    private String errorMessage;
    
}
