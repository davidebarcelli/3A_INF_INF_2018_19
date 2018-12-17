class serialLibTest
{
	static void SOP(String s)
	{
		System.out.println(s);
	}
	static {
		System.loadLibrary("serialLib"); // will load libhello.so
	}
	public static native int apriSeriale(String port);
	public static native void leggiDaSeriale();
	public static native int prendiValoreLetto(int pos);
	public static native void chiudiSeriale();
	
	public static void main(String[] args)
	{
		String port = "/dev/ttyUSB0";
		apriSeriale(port);
		SOP("Seriale aperta");
		int i;
		for(i=0;i<10;i=i+1)
		{
			int a,b,c;
			leggiDaSeriale();
			a = prendiValoreLetto(0);
			b = prendiValoreLetto(1);
			c = prendiValoreLetto(2);
			SOP("a="+a+", b="+b+", c="+c);
		}
		chiudiSeriale();
		SOP("Seriale chiusa");		
	}	
}
