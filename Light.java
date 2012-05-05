public class Light extends Point{
	
	double diffuse;
	double specular;
	
	public Light(double diffuse, double specular, double x, double y, double z, double h){
		this.diffuse = diffuse;
		this.specular = specular;
		super(x,y,z,h);
	}
	
}