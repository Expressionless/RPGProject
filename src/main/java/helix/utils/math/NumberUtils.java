package helix.utils.math;

public class NumberUtils {

	public static Number clamp(final Number val, final Number min, final Number max) {
		if(val.doubleValue() < min.doubleValue())
			return min;
		else if(val.doubleValue() > max.doubleValue())
			return max;
		else return val;
	}
	
	public static Number loop(final Number val, final Number min, final Number max) {
		if(val.doubleValue() < min.doubleValue())
			return max;
		else if(val.doubleValue() > max.doubleValue())
			return min;
		else return val;
	}
	
}
