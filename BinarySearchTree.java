package BSearchTree;

public class BinarySearchTree {
	private BSTNode root;

	// https://favtutor.com/blogs/binary-search-tree-java
	public void add(Comparable info) {
		BSTNode newNode = new BSTNode(info);
		if (root == null) {
			root = newNode;
		} else {
			BSTNode node = root;
			while (node != null) {
				int comp = info.compareTo(node.getInfo());
				if (comp > 0) {
					if (node.getRight() == null) {
						node.setRight(newNode);
						newNode.setParent(node);
						break;
					} else {
						node = node.getRight();
					}
				} else if (comp < 0) {
					if (node.getLeft() == null) {
						node.setLeft(newNode);
						newNode.setParent(node);
						break;
					} else {
						node = node.getLeft();
					}
				}
			}
		}
	}

	public int getDepth(Comparable info) {
		BSTNode node = search(info);
		int depth = 0;
		while (node != root) {
			node = node.getParent();
			depth++;
		}
		return depth;
	}

	public void addRecusive(Comparable info) {
		this.root = insert(root, info, null);
	}

	public BSTNode insert(BSTNode node, Comparable info, BSTNode parent) {
		if (node == null) {

			node = new BSTNode(info);
			node.setParent(parent);
			return node;
		}
		else if (node.getInfo().compareTo(info) > 0 ) {
			node.setLeft(insert(node.getLeft(), info, node)) ;
		} else  if (node.getInfo().compareTo(info) < 0 ) {
			node.setRight(insert(node.getRight(), info, node));
		}
		return node;
	}

	public BSTNode findParent(Comparable info) {
		BSTNode rt = root;
		while (rt != null ) {
			int comp = info.compareTo(rt.getInfo());
			if (comp > 0 && (rt.getRight() == null)) {
				return rt;
			} else if (comp > 0 ) {
				rt = rt.getRight();
			}

			if (comp < 0 && (rt.getLeft() == null)) {
				return rt;
			} else if (comp < 0) {
				rt = rt.getLeft();
			}
		}
		return null;
	}

	public BSTNode search(Comparable info) {
		BSTNode node = root;
		while (node != null && !node.getInfo().equals(info)) {
			int comp = info.compareTo(node.getInfo());
			if (comp > 0) {
				node = node.getRight();
			} else if (comp < 0) {
				node = node.getLeft();
			}
		}
		return node;
	}

	public BSTNode min() {
		return minBelow(root);
	}

	public BSTNode min(BSTNode node) {
		return minBelow(node);
	}
	private static BSTNode minBelow(BSTNode node) {
		if (node != null) {
			while (node.getLeft() != null) {
				node = node.getLeft();
			}
		}
		return node;
	}

	public BSTNode max() {
		BSTNode node = root;
		if (node != null) {
			while (node.getRight() != null) {
				node = node.getRight();
			}
		}
		return node;
	}

	void delete(Comparable info) {
		root = deleteRecursive(root, info);
	}

	BSTNode deleteRecursive(BSTNode node, Comparable info)  {

		if(info.equals(node.getInfo()) && isLeaf(node)) {
			//pai tem esse node na equerda ou direita?
			node = null;
		}
		if (node == null) { return node; }

		if(isLeaf(node) && !(info.equals(node.getInfo()))) {
			return node;
		}
		else {
			if (info.compareTo(node.getInfo()) < 0 )
				node.setLeft(deleteRecursive(node.getLeft(), info));
			else if (info.compareTo(node.getInfo()) > 0 )
				node.setRight(deleteRecursive(node.getRight(), info));
			else  {
				if (node.getLeft() == null)
					return node.getRight();
				else if (node.getRight() == null)
					return node.getLeft();

				// duas childs
				node.setInfo(min(node.getRight()).getInfo());
				node.setRight(deleteRecursive(node.getRight(), node.getInfo())); ;
			}
		}
		return node;
	}

	 public boolean isLeaf(BSTNode node) {
		return (node.getLeft() == null && node.getRight() == null);
	 }

	public String toString() {
		StringBuilder sb = new StringBuilder();
//		buildStringInOder(root, sb);
		buildStringPreOrder(root, sb, 1);
		return sb.toString();
	}

	private void buildStringInOrder(BSTNode node, StringBuilder sb) {
		if (node != null) {
			if (node.getLeft() != null) {
				buildStringInOrder(node.getLeft(), sb);
			}

			sb.append(" ");
			sb.append(node.getInfo());
			sb.append(" ");

			if (node.getRight() != null) {
				buildStringInOrder(node.getRight(), sb);
			}
		}
	}

	private void buildStringPreOrder(BSTNode node, StringBuilder sb, int level) {
		sb.append("\n");
		sb.append("  ".repeat(Math.max(0, level)));
		sb.append(node.getInfo());
		if (node.getLeft() != null) {
			buildStringPreOrder(node.getLeft(), sb, level + 1);
		}
		if (node.getRight() != null) {
			buildStringPreOrder(node.getRight(), sb, level + 1);
		}
	}

}
