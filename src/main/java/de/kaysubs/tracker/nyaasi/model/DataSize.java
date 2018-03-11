package de.kaysubs.tracker.nyaasi.model;

public class DataSize {
    private final int value;
    private final DataUnit unit;

    public enum DataUnit {
        BYTE("Bytes"),
        KILOBYTE("KiB"),
        MEGABYTE("MiB"),
        GIGABYTE("GiB"),
        TERABYTE("TiB");

        private final String unitName;

        DataUnit(String unitName) {
            this.unitName = unitName;
        }
        public String getUnitName() {
            return unitName;
        }
    }

    public DataSize(int value, DataUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public int getValue() {
        return value;
    }

    public DataUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return value + " " + unit.unitName;
    }
}
