package main.launch;

public class BinaryTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		byte movement = 0x08;
		movement |= 0x04;
		System.out.println(Integer.toBinaryString(movement));
		System.out.println(Integer.toBinaryString(-1 * ~movement - 1));

	}

}
