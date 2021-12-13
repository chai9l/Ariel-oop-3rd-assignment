package api;

public class myLocation implements GeoLocation{

    private double x,y,z;

    public myLocation(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public myLocation() {
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(GeoLocation g) {
        double dx = this.x - g.x();
        double dy = this.y - g.y();
        double dz = this.z - g.z();
        double ret = (dx * dx + dy * dy + dz * dz);
        return Math.sqrt(ret);
    }
}
