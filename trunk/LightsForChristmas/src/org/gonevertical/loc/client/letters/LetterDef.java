package org.gonevertical.loc.client.letters;

public class LetterDef {

  public LetterDef() {
  }
    
  public boolean[][] getLetter(String s) {
    int i = 0;
    if (s != null && s.length() > 0) {
      i = s.toLowerCase().charAt(0);
    }
    boolean[][] l = null;
    switch (i) {
    case 45: // -
      l = getHyphen();
      break;
    case 44: // ,
      l = getComma();
      break;
    case 58: // :
      l = getColon();
      break;
    case 46: // .
      l = getPeriod();
      break;
    case 48: // 0
      l = get0();
      break;
    case 49: // 1
      l = get1();
      break;
    case 50: // 2
      l = get2();
      break; 
    case 51: // 3
      l = get3();
      break;
    case 52: // 4
      l = get4();
      break;
    case 53: // 5
      l = get5();
      break;
    case 54: // 6
      l = get6();
      break;
    case 55: // 7
      l = get7();
      break;
    case 56: // 8
      l = get8();
      break;
    case 57: // 9
      l = get9();
      break;
    case 97: // a
      l = geta();
      break;
    case 98: // b
      l = getb();
      break;
    case 99: // c
      l = getc();
      break;
    case 100: // d
      l = getd();
      break;
    case 101: // e
      l = gete();
      break;
    case 102: // f
      l = getf();
      break;
    case 103: // g
      l = getg();
      break;
    case 104: // h
      l = geth();
      break;
    case 105: // i
      l = geti();
      break; 
    case 106: // j
      l = getj();
      break;
    case 107: // k
      l = getk();
      break;
    case 108: // l
      l = getl();
      break;
    case 109: // m
      l = getm();
      break;
    case 110: // n
      l = getn();
      break;
    case 111: // o
      l = geto();
      break;
    case 112: // p
      l = getp();
      break;
    case 113: // q
      l = getq();
      break;
    case 114: // r
      l = getr();
      break;
    case 115: // s
      l = gets();
      break;
    case 116: // t
      l = gett();
      break;
    case 117: // u
      l = getu();
      break;
    case 118: // v
      l = getv();
      break;
    case 119: // w
      l = getw();
      break;
    case 120: // x
      l = getx();
      break;
    case 121: // y
      l = gety();
      break;
    case 122: // z 
      l = getz();
      break;
    default:
      l = getSpace();
      break;
    }
    
    if (l == null) {
      System.out.println("Pause");
    }
    
    return l;
  }
  
  private boolean[][] getHyphen() {
    boolean[][] l = initArray();
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    return l;
  }

  private boolean[][] getComma() {
    boolean[][] l = initArray();
    l[6][1] = true;
    l[7][0] = true;
    return l;
  }

  private boolean[][] getColon() {
    boolean[][] l = initArray();
    l[3][0] = true;
    l[5][0] = true;
    return l;
  }

  private boolean[][] initArray() {
    boolean[][] l = new boolean[9][5];
    for (int r=0; r < 8; r++) {
      for (int c=0; c < 5; c++) {
        l[r][c] = false;
      }
    }
    return l;
  }

  private boolean[][] getSpace() {
    boolean[][] l = initArray();
    return l;
  }

  private boolean[][] getPeriod() {
    boolean[][] l = initArray();
    l[6][0] = true;
    return l;
  }

  private boolean[][] get0() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] get1() {
    boolean[][] l = initArray();
    l[2][2] = true;
    l[3][2] = true;
    l[4][2] = true;
    l[5][2] = true;
    l[6][2] = true;
    return l;
  }

  private boolean[][] get2() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] get3() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][3] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] get4() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][3] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] get5() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] get6() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] get7() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][3] = true;
    l[4][3] = true;
    l[5][3] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] get8() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] get9() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][3] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] geta() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getb() {
    boolean[][] l = initArray();
    l[0][0] = true;
    l[1][0] = true;
    l[2][0] = true;
    l[3][0] = true;
    l[4][0] = true;
    l[5][0] = true;
    l[6][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][3] = true;
    l[4][3] = true;
    l[5][3] = true;
    l[6][3] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getc() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[4][0] = true;
    l[5][0] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getd() {
    boolean[][] l = initArray();
    l[0][3] = true;
    l[1][3] = true;
    l[2][3] = true;
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] gete() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[5][0] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getf() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[5][0] = true;
    l[6][0] = true;
    return l;
  }

  private boolean[][] getg() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    l[7][3] = true;
    l[8][0] = true;
    l[8][1] = true;
    l[8][2] = true;
    l[8][3] = true;
    return l;
  }

  private boolean[][] geth() {
    boolean[][] l = initArray();
    l[0][0] = true;
    l[1][0] = true;
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] geti() {
    boolean[][] l = initArray();
    l[1][1] = true;
    l[3][1] = true;
    l[4][1] = true;
    l[5][1] = true;
    l[6][1] = true;
    return l;
  }

  private boolean[][] getj() {
    boolean[][] l = initArray();
    l[1][2] = true;
    l[3][2] = true;
    l[4][2] = true;
    l[5][2] = true;
    l[6][2] = true;
    l[7][1] = true;
    l[8][0] = true;
    return l;
  }

  private boolean[][] getk() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][2] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[5][0] = true;
    l[5][2] = true;
    l[6][0] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getl() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[3][0] = true;
    l[4][0] = true;
    l[5][0] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getm() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][3] = true;
    l[2][4] = true;
    l[3][0] = true;
    l[3][2] = true;
    l[3][4] = true;
    l[4][0] = true;
    l[4][2] = true;
    l[4][4] = true;
    l[5][0] = true;
    l[5][2] = true;
    l[5][4] = true;
    l[6][0] = true;
    l[6][4] = true;
    return l;
  }

  private boolean[][] getn() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] geto() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getp() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    l[7][0] = true;
    l[8][0] = true;
    return l;
  }

  private boolean[][] getq() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    l[7][3] = true;
    l[8][3] = true;
    return l;
  }

  private boolean[][] getr() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[6][0] = true;
    return l;
  }

  private boolean[][] gets() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[4][0] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[4][3] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] gett() {
    boolean[][] l = initArray();
    l[0][1] = true;
    l[1][1] = true;
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][1] = true;
    l[4][1] = true;
    l[5][1] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getu() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] getv() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][1] = true;
    l[6][2] = true;
    return l;
  }

  private boolean[][] getw() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][4] = true;
    l[3][0] = true;
    l[3][2] = true;
    l[3][4] = true;
    l[4][0] = true;
    l[4][2] = true;
    l[4][4] = true;
    l[5][0] = true;
    l[5][2] = true;
    l[5][4] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    l[6][4] = true;
    return l;
  }

  private boolean[][] getx() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][1] = true;
    l[4][2] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][3] = true;
    return l;
  }

  private boolean[][] gety() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][3] = true;
    l[3][0] = true;
    l[3][3] = true;
    l[4][0] = true;
    l[4][3] = true;
    l[4][0] = true;
    l[5][0] = true;
    l[5][3] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    l[7][3] = true;
    l[8][0] = true;
    l[8][1] = true;
    l[8][2] = true;
    return l;
  }

  private boolean[][] getz() {
    boolean[][] l = initArray();
    l[2][0] = true;
    l[2][1] = true;
    l[2][2] = true;
    l[2][3] = true;
    l[3][3] = true;
    l[4][2] = true;
    l[5][1] = true;
    l[6][0] = true;
    l[6][1] = true;
    l[6][2] = true;
    l[6][3] = true;
    return l;
  }
  
  
}
