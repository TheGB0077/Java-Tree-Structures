package BSearchTree;

public class Program {

	public static void main(String[] args) {
		BinarySearchTree bst = new BinarySearchTree();
		
		bst.add(37);
		bst.add(20);
		bst.add(10);
		bst.add(80);
		bst.add(100);
		bst.addRecusive(90);
		bst.addRecusive(30);
		bst.addRecusive(180);
		bst.addRecusive(85);
		bst.addRecusive(5);
		
		System.out.println(bst);
		System.out.println("Buscando 30: " + bst.search(30));
		System.out.println("Minimo: " + bst.min());
		System.out.println("Máximo: " + bst.max());
		bst.delete(80);
		bst.search(30);
		bst.min();
		System.out.println(bst);
		System.out.println("Profundidade: " + bst.getDepth(90));

	}

}
