package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {

    private  List<Intersectable> geometries;

    public Geometries() {
        this.geometries = new LinkedList<>();
    }

    public Geometries(Intersectable... geometries){

        this.geometries= List.of(geometries);
    }

    public void add(Intersectable... geometries){
        this.geometries.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (this.geometries.isEmpty())
            return null;
        List<Point> result = null, points;
        for (Intersectable geometry : this.geometries) {
            points = geometry.findIntersections(ray);
            if (points!=null)
                if (result == null)
                    result = points;
                else
                    result.addAll(points);
        }
        return result;
    }
}
