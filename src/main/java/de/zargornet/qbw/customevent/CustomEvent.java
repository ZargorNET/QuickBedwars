package de.zargornet.qbw.customevent;

/**
 * CustomEvent
 */
public class CustomEvent {
    private String name;

    /**
     * Gets an events name
     *
     * @return Eventname
     */
    public String getName() {
        if (name == null)
            name = getClass().getSimpleName();
        return name;
    }
}
