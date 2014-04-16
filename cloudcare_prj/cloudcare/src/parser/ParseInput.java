package parser;
/**
 * Filename: ParseInput.java by Qichen Pan (AndrewId: pqichen)
 * 
 * Introduction: fetch a user input string and output an arraylist of strings of potential useful contents.
 * 
 * Reference: this piece of code uses StanfordNLP parser.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;

public class ParseInput {
  /* ParseInput maintains a list of useless words */
  public static ArrayList<String> StopWords;
  public LexicalizedParser lp;
  
  /* Constructor (fetching stopwords from "stopwords.txt")*/
  public ParseInput()
  {
    /* Initialize parser */
    lp = LexicalizedParser.loadModel("/Users/yingsheng/Documents/workspace_ee/cloudcare/englishPCFG.ser.gz");
    
    /* Initialize stopwords */
    StopWords = new ArrayList<String>();
    BufferedReader br = null;
    try {
      /* Notice that stopwords are sorted. */
      br = new BufferedReader(new FileReader("/Users/yingsheng/Documents/workspace_ee/cloudcare/stopwords.txt"));
      String tmpStr;
      while((tmpStr = br.readLine()) != null)
      {
        StopWords.add(tmpStr);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  /* Fetch user input string, return an arraylist of useful strings */
  public ArrayList<String> getSymptoms(String inputString)
  {
    /* Get tree stucture of a sentence */
    Tree InputParser = lp.parse(inputString);
    String [] nodeValue = InputParser.toString().trim().replaceAll("[^a-zA-Z ]", "").split("\\s+");
    
    /* Store useful nodes information */
    ArrayList<String> usefulValue = new ArrayList<String>();
    for(int i = 0; i < nodeValue.length - 1; i++)
    {
      if(nodeValue[i].contains("NN") || nodeValue[i].contains("VB") || nodeValue[i].contains("JJ"))
      {
        usefulValue.add(nodeValue[i + 1].toLowerCase());
      }
    }
    Collections.sort(usefulValue);
    
    /* Filter stopwords */
    for(int i = 0; i < usefulValue.size(); i++)
    {
      for(int j = 0; j < StopWords.size(); j++)
      {
        /* Since both lists are sorted. Use this shortcut to reduce computing time.*/
        if(usefulValue.get(i).compareTo(StopWords.get(j)) < 0) break;
        if(usefulValue.get(i).equals(StopWords.get(j)))
        {
          usefulValue.remove(i);
          i--;
          break;
        }
      }
    }

    return usefulValue;
  }
  
  /* A simple test for parser. */
  
  public static void main(String [] args) throws IOException
  {
    ParseInput PI = new ParseInput();
    while(true)
    {
      System.out.println("");
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      System.out.println("Please briefly describe your symptom:");
      String inputString = br.readLine();
      ArrayList<String> usefulValue = PI.getSymptoms(inputString);
      System.out.println("Potential useful information from your input:");
      for(int i = 0; i < usefulValue.size(); i++)
      {
        System.out.println(usefulValue.get(i));
      }
    }
  }
  
}
