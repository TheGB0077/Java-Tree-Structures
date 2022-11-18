@SuppressWarnings({"unchecked", "rawtypes"})
public class BinarySearchTree {
	private BSTNode root;
	private int nodeCount = 0;

	//[Operations]
	public void add(Comparable info) {
		BSTNode newNode = new BSTNode(info);
		if (root == null) {
			root = newNode;
			nodeCount++;
		} else {
			BSTNode node = root;
			while (node != null) {
				int comp = info.compareTo(node.getInfo());
				if (comp > 0) {
					if (node.getRight() == null) {
						node.setRight(newNode);
						newNode.setParent(node);
						nodeCount++;
						break;
					} else {
						node = node.getRight();
					}
				} else if (comp < 0) {
					if (node.getLeft() == null) {
						node.setLeft(newNode);
						newNode.setParent(node);
						nodeCount++;
						break;
					} else {
						node = node.getLeft();
					}
				}
			}
		}
	}

	public void addRecusive(Comparable info) {
		this.root = insert(root, info, null);
		nodeCount++;
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

	public void delete(Comparable info) {
		BSTNode node = search(info);
		if (node != null) {
			delete(node);
			nodeCount--;
		}
	}

	private void delete(BSTNode node) {
		if (isLeaf(node)) {
			if (node.getParent().getLeft() == node) {
				node.getParent().setLeft(null);
			} else {
				node.getParent().setRight(null);
			}
			node.setParent(null);
		} else {
			if (node.getLeft() != null && node.getRight() != null) {
				BSTNode maxLCN = max(node.getLeft());
				delete(maxLCN);
				replace(node, maxLCN);
			} else {
				BSTNode child;
				if (node.getLeft() != null) {
					child = node.getLeft();
				} else {
					child = node.getRight();
				}
				BSTNode parent = node.getParent();
				if (parent.getLeft() == node) {
					parent.setLeft(child);
				} else {
					parent.setRight(child);
				}
				child.setParent(parent);
			}
			node.setParent(null);
			node.setLeft(null);
			node.setRight(null);
		}
	}

	//[InfoGetters]
	public int getNodeCount() {
		return nodeCount;
	}

	public int getDepth(Comparable info) {
		BSTNode node = search(info);
		return getDepth(node);
	}

	public int getDepth(BSTNode node) {
		int depth = 0;
		while (node != root) {
			node = node.getParent();
			depth++;
		}
		return depth;
	}

	public boolean isLeaf(BSTNode node) {
		return (node.getLeft() == null && node.getRight() == null);
	}

	public BSTNode sucessor(BSTNode node) {
		return sucessor(node, max());
	}

	private BSTNode sucessor(BSTNode node, BSTNode nodeMax) {
		BSTNode result = null;
		if (node.getRight() != null) {
			result = min(node.getRight());
		} else {
			if (node != root && node != nodeMax) {
				BSTNode parent = node.getParent();
				while (parent.getInfo().compareTo(node.getInfo()) < 0) {
					parent = parent.getParent();
				}
				result = parent;
			}
		}
		return result;
	}


	public BSTNode predecessor(BSTNode node) {
		return predecessor(node, min());
	}
	private BSTNode predecessor(BSTNode node, BSTNode nodeMin) {
		BSTNode result = null;
		if (node.getLeft() != null) {
			result = max(node.getLeft());
		} else {
			if (node != root && node != nodeMin) {
				BSTNode parent = node.getParent();
				while (parent.getInfo().compareTo(node.getInfo()) > 0) {
					parent = parent.getParent();
				}
				result = parent;
			}
		}
		return result;
	}

	//[Utils]
	public BSTNode findParent(Comparable info) {
		BSTNode rt = root;
		while (rt != null ) {
			int comp = info.compareTo(rt.getInfo());
			if (comp > 0 ) {
				rt = rt.getRight();
			}
			else if (comp < 0) {
				rt = rt.getLeft();
			}
			if(comp == 0) {
				return rt.getParent();
			}
		}
		return null;
	}

	private void replace(BSTNode oldNode, BSTNode newNode) {
		if (oldNode.getLeft() != newNode) {
			newNode.setLeft(oldNode.getLeft());
		}
		if (oldNode.getRight() != newNode) {
			newNode.setRight(oldNode.getRight());
		}
		if (oldNode != root){
			newNode.setParent(oldNode.getParent());
			BSTNode oldParent = oldNode.getParent();
			if (oldParent.getLeft() == oldNode) {
				oldParent.setLeft(newNode);
			} else {
				oldParent.setRight(newNode);
			}
		}
	}

	public BSTNode min() {
		return minBelow(root);
	}
	private BSTNode min(BSTNode node) {
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
		return maxBelow(root);
	}

	public BSTNode max(BSTNode node) {
		return maxBelow(node);
	}

	private BSTNode maxBelow(BSTNode node) {
		if (node != null) {
			while (node.getRight() != null) {
				node = node.getRight();
			}
		}
		return node;
	}

	public String printDescendingOrder() {
		return printDescendingOrder(max(), min());
	}

	private String printDescendingOrder(BSTNode maxNode, BSTNode minNode) {
		StringBuilder sb = new StringBuilder();
		BSTNode node = maxNode;
		sb.append(node).append(" → ");
		while (predecessor(node, minNode) != null) {
			node = predecessor(node, minNode);
			sb.append(node);
			if (node != minNode) {sb.append(" → ");}
		}
		return sb.toString();
	}

	public String printAscendingOrder() {
		return printAscendingOrder(max(), min());
	}

	private String printAscendingOrder(BSTNode maxNode, BSTNode minNode) {
		StringBuilder sb = new StringBuilder();
		BSTNode node = minNode;
		sb.append(node).append(" → ");
		while (sucessor(node, maxNode) != null) {
			node = sucessor(node, maxNode);
			sb.append(node);
			if (node != maxNode) {sb.append(" → ");}
		}
		return sb.toString();
	}
	

	public String toString() {
		StringBuilder sb = new StringBuilder();
		appendNodes(root, sb, 0);
		return sb.toString();
	}

	private void appendNodes(BSTNode node, StringBuilder sb, int level) {
		sb.append(node.getInfo());
		if (node.getLeft() != null) {
			sb.append("\n").append("  ".repeat(Math.max(0, level))).append("L ▶ ").append(getColor(level)).append("[").append("\u001B[0m");
			appendNodes(node.getLeft(), sb, level + 1);
			sb.append(getColor(level)).append("]").append("\u001B[0m");
		}
		if (node.getRight() != null) {
			sb.append("\n").append("  ".repeat(Math.max(0, level))).append("R ▶ ").append(getColor(level)).append("[").append("\u001B[0m");
			appendNodes(node.getRight(), sb, level + 1);
			sb.append(getColor(level)).append("]").append("\u001B[0m");
		}
	}

	private String getColor(int level) {
		String[] arrColors = {"\u001B[31m","\u001B[32m", "\u001B[33m", "\u001B[34m"};
		return arrColors[(int) (level - ((arrColors.length * Math.floor((double) level / arrColors.length))))];
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

	private void buildStringPostOrder(BSTNode node, StringBuilder sb) {
		if (node != null) {
			if (node.getRight() != null) {
				buildStringPostOrder(node.getRight(), sb);
			}

			sb.append(" ");
			sb.append(node.getInfo());
			sb.append(" ");

			if (node.getLeft() != null) {
				buildStringPostOrder(node.getLeft(), sb);
			}
		}
	}
}
