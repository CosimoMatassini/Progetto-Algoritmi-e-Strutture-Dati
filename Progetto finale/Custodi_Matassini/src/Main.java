/*
 * Custodi Alessandro, 	Matricola 7084103
 * Matassini Cosimo, 	Matricola 7083831
 */

public class Main
{
	public static void main(String[] args)
	{
		DoppiaCatenaCircolare<Integer> catena = new DoppiaCatenaCircolare<>();
		catena.add(2,2);
		catena.add(7, 5);
		catena.add(8,8);
		catena.add(9, 1);
		catena.add(7, 8);
		catena.add(1, 3);
		System.out.println(catena.toString());
		System.out.println(catena.sorted());
	}
}