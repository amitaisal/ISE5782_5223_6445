package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements  Intersectable {

    private  List<Intersectable> geometries;

    public Geometries() {
        this.geometries = new ArrayList<>();
    }

    public Geometries(Intersectable... geometries){
        add(geometries);
    }

    public void add(Intersectable... geometries){
        for (Intersectable geometry:geometries){
            this.geometries.add(geometry);

        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {

        for (Intersectable geometry:geometries){

        }
    }
}
