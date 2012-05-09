package in.aaronmiller.raytrace;
public class RGBPixel
{
	private int r;
	private int g;
	private int b;

	public RGBPixel(int r, int g, int b)
	{
		if (r >= 0 && r <= 255)
			this.r = r;
		else
			this.r = 0;
		if (g >= 0 && g <= 255)
			this.g = g;
		else
			this.g = 0;
		if (b >= 0 && b <= 255)
			this.b = b;
		else
			this.b = 0;
	}

	public int getRed()
	{
		return r;
	}

	public int getGreen()
	{
		return g;
	}

	public int getBlue()
	{
		return b;
	}


	public void setRed(int red)
	{
		r = red;
	}

	public void setGreen(int green)
	{
		g = green;
	}

	public void setBlue(int blue)
	{
		b = blue;
	}
}
