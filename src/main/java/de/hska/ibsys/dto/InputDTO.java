package de.hska.ibsys.dto;

import de.hska.ibsys.input.Input;

/**
 *
 * @author p01
 */
public class InputDTO {
    /**
     * Note: This Class must be generated by the Java Architecture for XML Binding (JAXB).
     *       Please run maven if it does not exist.
     */
    private Input input;
    
    
    public InputDTO() {
    }
    
    public InputDTO(Input input) {
        this.input = input;
    }
    
    public Input getInput() {
        return input;
    }
    
    public void setInput(Input input) {
        this.input = input;
    }
}
