package nl.tsai.airosearch.airport;

public enum AirportType {
    BALLOON_PORT("balloonport"),
    HELIPORT("heliport"),
    LARGE_AIRPORT("large_airport"),
    MEDIUM_AIRPORT("medium_airport"),
    SMALL_AIRPORT("small_airport"),
    SEAPLANE_BASE("seaplane_base"),
    CLOSED("closed");

    private final String label;

    AirportType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
