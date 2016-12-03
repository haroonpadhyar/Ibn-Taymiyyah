package com.maktashaf.taymiyyah.analysis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeSource;

//import org.apache.lucene.analysis.tokenattributes.TermAttribute;

/**
 * @author: Haroon Anwar Padhyar.
 */
public class AnalyzerUtils {

  public static AttributeSource[] tokensFromAnalysis(Analyzer analyzer,
                                                     String text) throws IOException {

    TokenStream stream = analyzer.tokenStream("contents", new StringReader(text));
    stream.reset();
      ArrayList tokenList = new ArrayList();
    while (true) {
      if (!stream.incrementToken())
        break;
      tokenList.add(stream.cloneAttributes());
    }
    stream.close();

    AttributeSource[] attributeSources = new AttributeSource[tokenList.size()];
    for(int i = 0; i < tokenList.size(); i++){
//      System.out.println(tokenList.get(i).getClass().getName());
      AttributeSource state = (AttributeSource)tokenList.get(i);
//      System.out.println(state.toString());
      attributeSources[i] = state;
    }

    return attributeSources;
  }

  public static void displayTokens(Analyzer analyzer,
                                   String text) throws IOException {
    AttributeSource[] tokens = tokensFromAnalysis(analyzer, text);
    for (int i = 0; i < tokens.length; i++) {
      AttributeSource token = tokens[i];
      CharTermAttribute charTermAttribute = token.addAttribute(CharTermAttribute.class);
//      TermAttribute term = (TermAttribute) token.addAttribute(TermAttribute.class);
      System.out.print("[  " + charTermAttribute.toString() + "  ] ");
    }
  }
}
