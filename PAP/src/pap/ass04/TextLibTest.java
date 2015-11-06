package pap.ass04;

public class TextLibTest {

	public static void main(String[] args) {
		/*
		 * java -cp ./Documents/workspace/PAP/bin/ pap.ass04.TextLibTest
		 */

		TextLib lib = TextLibFactory.getInstance();
		 lib.cls();
		 lib.writeAt(10, 5, "*");
		 lib.writeAt(3, 2, "*", 2);
		 lib.writeAt(5, 7, "*", 5);
		 lib.writeAt(13, 12, "*", 6);
		 
		 //L'interfaccia della mia shell è 100x28
		 //resetto la posizione di fine rimettendo anche il cursore bianco (ho lo sfondo azzurro)
		 lib.writeAt(0, 28, "\n", 7);

	}
}
