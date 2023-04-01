package hr.java.vjezbe.entiteti;

/**
 * zbirka s kanonskim konstruktorom za varijable "x" i "y"
 * @param x
 * @param y
 */
public record Zbirka(double x, double y) {

    public Zbirka(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
