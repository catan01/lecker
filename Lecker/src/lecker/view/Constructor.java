package lecker.view;



/**
 * Creates the General side and inherits the other elements.
 * 
 * @author LWagner
 *
 */
public class Constructor {
	public String getSide(String remoteAddr, AbstractSide side) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(side.getCode(remoteAddr));
		
		//TODO
		
		return buffer.toString();
	}
}
