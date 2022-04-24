package geometries;

import primitives.*;

/**
 *
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    public abstract Vector getNormal(Point point);
    private Material material = new Material();

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Double3 getkD() {
         return material.kD;
    }
    public  Double3 getkS(){
        return material.kS;
    }

    public int getShininess(){
        return material.nShininess;
    }



}
