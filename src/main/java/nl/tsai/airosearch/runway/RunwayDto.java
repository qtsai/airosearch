package nl.tsai.airosearch.runway;

import lombok.Getter;

@Getter
public class RunwayDto {

    private final Integer id;
    private final Boolean lighted;
    private final Boolean closed;
    private final String surface;
    private final String lengthIdentifier;
    private final String heightIdentifier;

    public RunwayDto(Runway runway) {
        this.id = runway.getId();
        this.lighted = runway.getLighted();
        this.closed = runway.getClosed();
        this.surface = runway.getSurface();
        this.lengthIdentifier = runway.getLengthIdentifier();
        this.heightIdentifier = runway.getHeightIdentifier();
    }
}
