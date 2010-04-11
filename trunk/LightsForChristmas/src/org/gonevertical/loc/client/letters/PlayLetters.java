package org.gonevertical.loc.client.letters;

import java.util.ArrayList;

import org.gonevertical.loc.client.SnowBallGameFrame;
import org.gonevertical.loc.client.snowball.SnowBallColumns;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;

public class PlayLetters extends Composite {

  private SnowBallColumns sb;
  
  private LetterDef loadedLetters = new LetterDef();
  
  private boolean loop = false;
  
  // which snowball row to start with
  private int colPos = 8;
  
  // which position in the letter columns 
  private int letPos = 0;
  
  private ArrayList<boolean[]> columns = null;

  private int blankColumnIndex = 0;
  
  public PlayLetters() {
  }
  
  public void clearLetters() {
	  sb.displayAll(false);
  }
  
  public void drawLoop() {
    loop = true;
    colPos = 8;
    letPos = 0;
    blankColumnIndex = 0;
    loop();
  }
  
  private void loop() {
    Timer timer = new Timer() {
      public void run() {
        if (loop == true) {
          draw();
          loop();
        } else {
          fire();
        }
      }
    };
    timer.schedule(SnowBallGameFrame.PLAYLETTERDELAY);
  }
  
  private void draw() {
    
    if (colPos > 0) {
      colPos--;
    }
    
    if (colPos == 0 && letPos < columns.size()) {
      letPos++;
    } else {
      letPos = 0;
    }
    
    drawColumns();
    
  }
  
  private void drawColumns() {
    
    System.out.println("Index: drawColIndexPos: " + colPos + " letterIndexPos: " + letPos);
    
    int ll = 7 - colPos; // letter limit
    
    // get the columns to play to snowballs, 7 columns at a time from the index of the column's array
    int start = letPos;
    int limit = ll + 1;
    ArrayList<boolean[]> cols = getColumns(start, limit);

    if (cols == null) {
      loop = false;
      return;
    } 
    
    // loop through columns
    int colPosIndex = colPos;
    for (int c=0; c < cols.size(); c++) {
      
      boolean[] bc = cols.get(c); // column data
      
      System.out.println("\t setSB: column:" + c);
      
      for (int r=0; r < 9; r++) { // loop through the column rows
        sb.setBall(r, colPosIndex, bc[r]);
      }
      
      colPosIndex++;
    }
    
  }

  /**
   * convert letter def array into columns
   * 
   * @param start
   * @param limit
   * @return
   */
  private ArrayList<boolean[]> getColumns(int start, int limit) {

    ArrayList<boolean[]> cols = new ArrayList<boolean[]>();
    int end = start + limit;
    
    for (int i=start; i < end; i++) {
      
      if (i > columns.size() - 1) { // if limit is greater than column array, put in blanks, up to 6 times
        blankColumnIndex++;
        cols.add(getBlankColumn());
        
      } else {
        boolean[] tc = columns.get(i);
        cols.add(tc);
      }
    }

    if (blankColumnIndex > 25) {
      loop = false;
    }
    
    return cols;
  }

  private ArrayList<boolean[]> loadLettersPlayArray(String[] sletters) {
    
    // get the letters into an array
    ArrayList<boolean[][]> theLetters = new ArrayList<boolean[][]>();
    for (int i=0; i < sletters.length; i++) {
      boolean[][] l = loadedLetters.getLetter(sletters[i]); 
      theLetters.add(l);
    }
    
    // make one long column array
    ArrayList<boolean[]> columns = new ArrayList<boolean[]>();
    
    for (int let=0; let < theLetters.size(); let++) {
      
      // work with letter
      boolean[][] l = theLetters.get(let);
      
      // loop through the row
      for (int c=0; c < 5; c++) {
        
        // make a column
        boolean[] col = new boolean[9];
        
        for (int r=0; r < 9; r++) {
          boolean b =  l[r][c];
          //System.out.println("row: " + r + " col: " + c + " boolean: " + b);
          col[r] = b;
        }

        // add the column
        columns.add(col);
      }
    }
    return columns;
  }

  public void setSnowBalls(SnowBallColumns sb) {
    this.sb = sb;
  }

  private boolean[] getBlankColumn() {
    return new boolean[9];
  }

  public void playWinnerTie() {
    Timer t = new Timer() {
      public void run() {    
        clearLetters();
        String s = "players 1 and 2 tie.";
        columns = loadLettersPlayArray(getLettersArray(s));
        drawLoop();
      }
    };
    t.schedule(0);
  }

  public void playWinnerA() {
    Timer t = new Timer() {
      public void run() {
        clearLetters();
        String s = "players 1 wins.";
        columns = loadLettersPlayArray(getLettersArray(s));
        drawLoop();
      }
    };
    t.schedule(1);
  }

  public void playWinnerB() {
    Timer t = new Timer() {
      public void run() {
        clearLetters();
        String s = "players 2 wins.";
        columns = loadLettersPlayArray(getLettersArray(s));
        drawLoop(); 
      }
    };
    t.schedule(1);
  }
  
  public void playThanks_Google() {
    Timer t = new Timer() {
      public void run() {
        clearLetters();
        String s = "gwt powered. " +
        		"thanks google. ";

        columns = loadLettersPlayArray(getLettersArray(s));
        drawLoop(); 
      }
    };
    t.schedule(1);
  }
  
  public void playThanks_To() {
    Timer t = new Timer() {
      public void run() {
        clearLetters();
        /*
        String s = "Credits: thanks to: " +
        		"my wife angie, for being patient with me. " +
        		"eric blue co-creator and electronic design. " +
        		"clary riensma, warm beach woodworking, for building this structure." +
        		"kieth yarter for believing i could do it. " +
        		"dad, rod donnelson, for helping with everything at home during development. " +
        		"thanks for every bodies prayers. ";
        		*/
        String s = "Thanks angie donnelson, eric blue, clary riensma, kieth yarter, rod donnelson.";
        columns = loadLettersPlayArray(getLettersArray(s));
        drawLoop(); 
      }
    };
    t.schedule(1);
  }
  
 public void playTest() {
    Timer t = new Timer() {
      public void run() {
        clearLetters();
        setCombo_TestAll();
        drawLoop();
      }
    };
    t.schedule(1);
  }
  
  private String[] getLettersArray(String s) {
    char[] cc = s.toCharArray();
    String[] letters = new String[cc.length];
    for (int i=0; i < cc.length; i++) {
      letters[i] = Character.toString(cc[i]);
    }
    return letters;
  }

  public void setCombo_TestAll() {
    String[] letters = new String[40];
    letters[0] = "a";
    letters[1] = "b";
    letters[2] = "c"; 
    letters[3] = "d";
    letters[4] = "e";
    letters[5] = "f";
    letters[6] = "g";
    letters[7] = "h";
    letters[8] = "i";
    letters[9] = "j";
    letters[10] = "k";
    letters[11] = "l";
    letters[12] = "m";
    letters[13] = "n";
    letters[14] = "o";
    letters[15] = "p";
    letters[16] = "q";
    letters[17] = "r";
    letters[18] = "s";
    letters[19] = "t";
    letters[20] = "u";
    letters[21] = "v";
    letters[22] = "w";
    letters[23] = "x";
    letters[24] = "y";
    letters[25] = "z";
    
    letters[26] = "0";
    letters[27] = "1";
    letters[28] = "2";
    letters[29] = "3";
    letters[30] = "4";
    letters[31] = "5";
    letters[32] = "6";
    letters[33] = "7";
    letters[34] = "8";
    letters[35] = "9";
    
    letters[36] = ".";
    letters[37] = ",";
    letters[38] = "-";
    letters[39] = ":";
    
    columns = loadLettersPlayArray(letters);
  }
  
  
  private void fire() {
    NativeEvent nativeEvent = Document.get().createChangeEvent();
    ChangeEvent.fireNativeEvent(nativeEvent, this);
  }
  
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {
    return addDomHandler(handler, ChangeEvent.getType());
  }
  
  
}
