import java.util.LinkedList;
import edu.princeton.cs.algs4.BST;

import java.io.File;
import java.io.IOException;

public class DocumentBST {

	public static void main(String[] args) {
		//LinkedList<BST> BSTDocument = new LinkedList<BST>();
		
		//oneChapter = new BST();
		
		//BSTDocument.add(oneChapter); 
		
	
	}
	// Work on delete method

	public Chapter delete(BST root, String word) {
		if (root == null) {
			return null;
		}
		
		int cmp = word.compareTo(root.key);
        if      (cmp < 0) root.left  = delete(root.left,  word);
        else if (cmp > 0) root.right = delete(root.right, word);
        else {
            if (root.right == null) return root.left;
            if (root.left  == null) return root.right;
            Node t = root;
            root = min(t.right);
            root.right = deleteMin(t.right);
            root.left = t.left;
        }
        root.size = size(root.left) + size(root.right) + 1;
        return root;
	}
	
	@Override
	
	
	

}

