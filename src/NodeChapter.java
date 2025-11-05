import edu.princeton.cs.algs4.BST;

public class NodeChapter {
	private String title;
	private BST chapter;
	private int height;
	private int words;
	private NodeChapter nextChapter;
	
	public NodeChapter(String title, BST chapter, int height, int words, NodeChapter nextChapter) {
		this.title = title;
		this.chapter = chapter;
		this.height = height;
		this.words = words;
		this.nextChapter = nextChapter;
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public BST getChapter() {
		return chapter;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWords() {
		return words;
	}
	
	public NodeChapter getNextChapter() {
		return nextChapter;
	}
	
}
