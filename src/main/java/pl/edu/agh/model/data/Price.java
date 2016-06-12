package pl.edu.agh.model.data;

public enum  Price {
    FREE(0),
    INEXPENSIVE(1),
    MODERATE(2),
    EXPENSIVE(3),
    VERY_EXPENSIVE(4);

    private final int level;

    Price(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean between(Price minPrice, Price maxPrice) {
        return maxPrice.ordinal() <= this.ordinal() && this.ordinal() <= maxPrice.ordinal();
    }
}
