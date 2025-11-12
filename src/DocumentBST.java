//Saim Sultan, Ismail Abu-Shanab, John Diprospero
//11/7/2025
//Assignment 6

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//Saim

class Node {   //Has to be based on sedgewick, we need frequency, left right node, and size, and word 
    String word;
    int frequency;
    Node left;
    Node right;
    int size;

    Node(String word1) {
        word = word1;
        frequency = 1;
        left = null;
        right = null;
        size = 1;
    }
}

class BinarySearchTree {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    // from book
    public Node get(Node x, String key) {
        if (x == null){

         return null;
        }
        int cmp = key.compareTo(x.word);

        if (cmp < 0){
             return get(x.left, key);
        }
        else if (cmp > 0){
             return get(x.right, key);
        }
        else return x;
    }

    public Node get(String key) {
        return get(root, key);
    }

    
    //put method in book
    public Node put(Node x, String key) {
        if (x == null){
             return new Node(key);
        }
        int cmp = key.compareTo(x.word);

        if (cmp < 0){
             x.left = put(x.left, key);
        }
        else if (cmp > 0){
             x.right = put(x.right, key);
        }
        else x.frequency++;

        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    public void put(String key) {
        root = put(root, key);
    }

    public int size(Node x) {
        if (x == null){
             return 0;
        }
        return x.size;
    }

    public int height(Node x) {
        if (x == null) {
             return -1;
        }
        //Math.max is used to compare the height of the 2 trees left and right and return the higher value.
        return 1 + Math.max(height(x.left), height(x.right));
    }


    public int countWord(Node x) {
        if (x == null){ 
            return 0;
        }
        return 1 + countWord(x.left) + countWord(x.right);
    }

    public String preorderBST(Node x) {
        if (x == null){ 
             return "";
        }
        String s = x.word;

        if (x.frequency > 1){
       
        s += "(" + x.frequency + ")";
        }
        s += " ";
        s += preorderBST(x.left);
        s += preorderBST(x.right);
        return s;
    }

   // John
    public Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    
    public Node delMin(Node x) {
        if (x.left == null) return x.right;

         x.left = delMin(x.left);
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    
    public Node delete(Node x, String key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.word);
        if (cmp < 0) x.left = delete(x.left, key);
             else if (cmp > 0) x.right = delete(x.right, key);
             else {
             if (x.right == null) return x.left;
             if (x.left == null) return x.right;
             Node t = x;
             x = min(t.right);
             x.right = delMin(t.right);
             x.left = t.left;
        }
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(String key) {
        root = delete(root, key);
    }
}

// Ismail Abu-Shanab
// 11/5/2025
// I built a linked list to hold chapters form the files .
// Each chapter will eventually connect to its own 
//BST that stores words and how often they show up.
//////////////////////////////////////////////////////////////
// 11/6/2025
// I used recursion to add chapters one by one based on the input file.
// It is truly so smart that way  I don’t need to know how many chapters ahead of time itll use recursion to add them in.
// this is great becasue we dont know what all the other test will have so having this allows it to be modular and work with all 
// I also used recursion to print out all the chapter info, so it flows through the list naturally.

class ChapterNode {
    String chapterTitle;
    BinarySearchTree bst;
    int height;
    int distinctWords;
    ChapterNode next;

    public ChapterNode(String title) {
        chapterTitle = title;
        bst = new BinarySearchTree();
        height = 0;
        distinctWords = 0;
        next = null;
    }
}

class ChapterLinkedList {
    ChapterNode head;

    public ChapterLinkedList() {
        head = null;
    }

    public void addChapter(String title)
    {
        head = addChapterRec(head, title);
    }

 public void addChapterNode(ChapterNode chap) 
 {
    if (head == null)
     {
        head = chap;
    } else {
        ChapterNode current = head;
        while (current.next != null){
             current = current.next;
        }
        current.next = chap;
    }
}

    private ChapterNode addChapterRec(ChapterNode current, String title)
     {
        if (current == null)
        { 
            return new ChapterNode(title);
        }

        current.next = addChapterRec(current.next, title);
        return current;
    }

    public ChapterNode findChapter(String title) {
        return findChapterRec(head, title);
    }

    private ChapterNode findChapterRec(ChapterNode current, String title) {
        if (current == null)
        {
             return null;
        }

        if (current.chapterTitle.equals(title))
        {
             return current;
        }

        return findChapterRec(current.next, title);
    }

    public void printAll() 
    {
        printAll(head);
    }

    private void printAll(ChapterNode current) 
    {
        if (current == null)
        {
             return;
        }

        System.out.println("Chapter: " + current.chapterTitle);


        printAll(current.next);
    }

    public void updateInfo() {
        ChapterNode current = head;
        while (current != null) {
            current.height = current.bst.height(current.bst.root);
            current.distinctWords = current.bst.countWord(current.bst.root);
            current = current.next;
        }
    }
}


//This is the main class DocumentBST to format and print out the result and read the file, made by all of us together
public class DocumentBST {

    public static void main(String[] args) throws Exception{ //we need an exception just in case for the files, originally it was not picking up the files
        String[] files = {"file1.txt", "file2.txt", "file3.txt"};
        ChapterLinkedList chapters = new ChapterLinkedList();

        //this is so we can read the file1.txt
        Scanner File1 = new Scanner(new File(files[0]));
        int numberOfChapters = 0;
        if (File1.hasNextInt()) {
            numberOfChapters = File1.nextInt(); // first integer is number of chapters
        }

        while (File1.hasNextLine()) {
            String line = File1.nextLine();

            if (line.length() == 0) {
              
            } else {

                //this lineScan will read the individual words in the chapter
                Scanner lineScan = new Scanner(line);

                String chapterName = "";
                if (lineScan.hasNext()) 
                
                {
                    chapterName = lineScan.next(); //for the chapter name
                }

                int numWords = 0;
                if (lineScan.hasNextInt()) {
                    numWords = lineScan.nextInt(); //this will read the number of words in chapter 
                }

                if (chapterName.length() > 0) {//chapter is added to the link list
   
                    chapters.addChapter(chapterName);

                    ChapterNode chapter = chapters.findChapter(chapterName);

                    while (lineScan.hasNext()) { //adding words to the BST for chapter

                        String word = lineScan.next();
                        if (word.length() > 0) 
                         {

                            chapter.bst.put(word);
                        }
                        }
                      }
                lineScan.close();
               }
               }
              File1.close();
//using update info method, update the data for height and distcint words
                chapters.updateInfo();


        //This is where we actually print the info of the chapters, distinct words and height 
        ChapterNode current = chapters.head;
        while (current != null)
        
        {

            System.out.println(current.chapterTitle + " : " + current.distinctWords +
                    " distinct words and tree height is " + current.height + ".");


            System.out.println(current.bst.preorderBST(current.bst.root));
            
            current = current.next;
        }

       
        //we need a array list to hold the words we will search from file 2
        List<String> searchF2 = new ArrayList<>();

        Scanner File2 = new Scanner(new File(files[1]));

        int wordsF2 = 0;

        if (File2.hasNextInt()) {
            wordsF2 = File2.nextInt();   //first int is the number of words
        }

        for (int i = 0; i < wordsF2; i++) {
            if (File2.hasNext())
             {
                String w = File2.next(); 

                if (w.length() > 0) 
                {
                    searchF2.add(w);
                }
             }
        }
        File2.close();

        //this will search the words from file 2 in file 1
        System.out.println("\nSEARCHING:");
        for (int i = 0; i < searchF2.size(); i++) {

            String w = searchF2.get(i);

             System.out.println(w + ":");

             current = chapters.head;

            boolean found = false;

            while (current != null) {
                Node n = current.bst.get(w);

                if (n != null) 
                {
                    System.out.println(current.chapterTitle + "(" + n.frequency + " times)");
                    found = true;
                }
                current = current.next;
            }
            if (!found) {
                System.out.println("NOT FOUND");
            }
        }

        //we will also need a list for the words to delete contained in file3
        List<String> deleteF3 = new ArrayList<>();

        Scanner File3 = new Scanner(new File(files[2]));

        int numDelete = 0;

        if (File3.hasNextInt()) {
            numDelete = File3.nextInt(); //the int is the number of words in the file
        }

        for (int i = 0; i < numDelete; i++) 
        {
            if (File3.hasNext()) 
            
            {
                String w = File3.next();
                if (w.length() > 0) 
                {
                    deleteF3.add(w);
                }
                
        }
    }
        File3.close();
      
        //This can delete the words from file 3 in file1
        System.out.println("\nDELETION:");
        for (int i = 0; i < deleteF3.size(); i++) 
        
        {
            String w = deleteF3.get(i);
            System.out.println(w + ":");
            current = chapters.head;
            boolean wordFound = false;


            //this is where we traverse the chapters and delete word if it is there
            while (current != null)
             {
                Node n = current.bst.get(w);
                if (n != null)
                 {
                    wordFound = true;

                    //this is to try and delete only the first instance of the word
                    if (n.frequency > 1)
                     {
                        n.frequency--;
                    } else {
                        current.bst.delete(w);
                    }


                     //print the BST after the word is deleted
                    System.out.println(current.chapterTitle + " " + current.bst.preorderBST(current.bst.root));
                }


                current = current.next;
             }
            if (!wordFound) {
                System.out.println("NOT FOUND");
            }
        }
    }

    }
