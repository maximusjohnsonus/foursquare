interface Trans {
	public double[][] transatlantic(double[][] pts);
}
interface Shift {
	public double[] shift(double[] pt);
	/* USAGE:
	s = p -> {
		double[] b=new double[p.length];
		for(int i=0; i<p.length; i++){
				b[i]=2*p[i];
		}
		return b;
	};
	Fourbject f = <definition>
	f.shift(s);	
	 */
}
