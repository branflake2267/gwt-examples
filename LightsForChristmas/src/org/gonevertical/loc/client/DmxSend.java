package org.gonevertical.loc.client;

import org.gonevertical.loc.client.digit.Digits;
import org.gonevertical.loc.client.rpc.Rpc;
import org.gonevertical.loc.client.rpc.RpcServiceAsync;
import org.gonevertical.loc.client.snowball.SnowBallColumns;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DmxSend {

  private RpcServiceAsync rpc = null;
  
  private SnowBallColumns sb;
  private Padels wPadels;
  private SideScore wScoreA;
  private SideScore wScoreB;
  private Digits wDigitsA;
  private Digits wDigitsB;
  private TimerSystem wTimerSystem;
  private Winner wWinner;
  private GameControls wControls;
  private ButtonLights wButtonLights = null;

  private ChannelData cd = new ChannelData();
  
  public DmxSend() {
    rpc  = Rpc.initRpc();
    
    // repeat it every 50ms
   repeatSend();
  }
  
  public void startSending() {
    repeatSend();
  }
  
  public void setElements(
      SnowBallColumns sb, 
      Padels wPadels, 
      SideScore wScoreA, 
      SideScore wScoreB, 
      Digits wDigitsA, 
      Digits wDigitsB, 
      TimerSystem wTimerSystem, 
      Winner wWinner, 
      GameControls wControls,
      ButtonLights wButtonLights) {
    
    this.sb = sb;
    this.wPadels = wPadels;
    this.wScoreA = wScoreA;
    this.wScoreB = wScoreB;
    this.wDigitsA = wDigitsA;
    this.wDigitsB = wDigitsB;
    this.wTimerSystem = wTimerSystem;
    this.wWinner = wWinner;
    this.wControls = wControls;
    this.wButtonLights = wButtonLights;
  }
  
  private boolean[] getSnowBalls() {
    boolean[] m = sb.getMap();
    return m;
  }
  
  private boolean[] getPadelsSquares() {
    boolean[] m = wPadels.getMap();
    return m;
  }
  
  private boolean[] getButtonLights() {
    boolean[] m = wButtonLights.getMap();
    return m;
  }
  
  private boolean[] getScoreDigits() {
   boolean[] d1 = wDigitsA.getMap(); // 21
   boolean[] d2 = wDigitsB.getMap(); // 21
   
   boolean[] m = new boolean[42];
   m[0] = d1[0];
   m[1] = d1[1];
   m[2] = d1[2];
   m[3] = d1[3];
   m[4] = d1[4];
   m[5] = d1[5];
   m[6] = d1[6];
   
   m[7] = d1[7];
   m[8] = d1[8];
   m[9] = d1[9];
   m[10] = d1[10];
   m[11] = d1[11];
   m[12] = d1[12];
   m[13] = d1[13];
   
   m[14] = d1[14];
   m[15] = d1[15];
   m[16] = d1[16];
   m[17] = d1[17];
   m[18] = d1[18];
   m[19] = d1[19];
   m[20] = d1[20];
   
   m[21] = d2[0];
   m[22] = d2[1];
   m[23] = d2[2];
   m[24] = d2[3];
   m[25] = d2[4];
   m[26] = d2[5];
   m[27] = d2[6];
   
   m[28] = d2[7];
   m[29] = d2[8];
   m[30] = d2[9];
   m[31] = d2[10];
   m[32] = d2[11];
   m[33] = d2[12];
   m[34] = d2[13];
   
   m[35] = d2[14];
   m[36] = d2[15];
   m[37] = d2[16];
   m[38] = d2[17];
   m[39] = d2[18];
   m[40] = d2[19];
   m[41] = d2[20];
   
   return m;
  }
  
  private boolean[] getTimerDigits() {
    boolean[] m = wTimerSystem.getMap(); // 21
    return m;
  }
    
  private boolean[] getMasterMap() {
    boolean[] m1 = getSnowBalls(); // 56
    
    boolean[] m2 = getPadelsSquares(); // 60
    
    boolean[] m3 = getScoreDigits(); // 42
    
    boolean[] m4 = getTimerDigits(); // 21
    
    boolean[] m5 = getButtonLights(); // 6
    
    boolean[] m = new boolean[192];
    m[0] = m1[0]; // snowballs 56
    m[1] = m1[1];
    m[2] = m1[2];
    m[3] = m1[3];
    m[4] = m1[4];
    m[5] = m1[5];
    m[6] = m1[6];
    m[7] = m1[7];
    m[8] = m1[8];
    m[9] = m1[9];
    m[10] = m1[10];
    m[11] = m1[11];
    m[12] = m1[12];
    m[13] = m1[13];
    m[14] = m1[14];
    m[15] = m1[15];
    m[16] = m1[16];
    m[17] = m1[17];
    m[18] = m1[18];
    m[19] = m1[19];
    m[20] = m1[20];
    m[21] = m1[21];
    m[22] = m1[22];
    m[23] = m1[23];
    m[24] = m1[24];
    m[25] = m1[25];
    m[26] = m1[26];
    m[27] = m1[27];
    m[28] = m1[28];
    m[29] = m1[29];
    m[30] = m1[30];
    m[31] = m1[31];
    m[32] = m1[32];
    m[33] = m1[33];
    m[34] = m1[34];
    m[35] = m1[35];
    m[36] = m1[36];
    m[37] = m1[37];
    m[38] = m1[38];
    m[39] = m1[39];
    m[40] = m1[40];
    m[41] = m1[41];
    m[42] = m1[42];
    m[43] = m1[43];
    m[44] = m1[44];
    m[45] = m1[45];
    m[46] = m1[46];
    m[47] = m1[47];
    m[48] = m1[48];
    m[49] = m1[49];
    m[50] = m1[50];
    m[51] = m1[51];
    m[52] = m1[52];
    m[53] = m1[53];
    m[54] = m1[54];
    m[55] = m1[55];

    
    m[56] = m2[0]; // padels 60
    m[57] = m2[1];
    m[58] = m2[2];
    m[59] = m2[3];
    m[60] = m2[4];
    m[61] = m2[5];
    m[62] = m2[6];
    m[63] = m2[7];
    m[64] = m2[8];
    m[65] = m2[9];
    m[66] = m2[10];
    m[67] = m2[11];
    m[68] = m2[12];
    m[69] = m2[13];
    m[70] = m2[14];
    m[71] = m2[15];
    m[72] = m2[16];
    m[73] = m2[17];
    m[74] = m2[18];
    m[75] = m2[19];
    m[76] = m2[20];
    m[77] = m2[21];
    m[78] = m2[22];
    m[79] = m2[23];
    m[80] = m2[24];
    m[81] = m2[25];
    m[82] = m2[26];
    m[83] = m2[27];
    m[84] = m2[28];
    m[85] = m2[29];
    m[86] = m2[30];
    m[87] = m2[31];
    m[88] = m2[32];
    m[89] = m2[33];
    m[90] = m2[34];
    m[91] = m2[35];
    m[92] = m2[36];
    m[93] = m2[37];
    m[94] = m2[38];
    m[95] = m2[39];
    m[96] = m2[40];
    m[97] = m2[41];
    m[98] = m2[42];
    m[99] = m2[43];
    m[100] = m2[44];
    m[101] = m2[45];
    m[102] = m2[46];
    m[103] = m2[47];
    m[104] = m2[48];
    m[105] = m2[49];
    m[106] = m2[50];
    m[107] = m2[51];
    m[108] = m2[52];
    m[109] = m2[53];
    m[110] = m2[54];
    m[111] = m2[55];
    m[112] = m2[56];
    m[113] = m2[57];
    m[114] = m2[58];
    m[115] = m2[59];
    
    m[116] = m3[14]; // p1 score digits 42
    m[117] = m3[18];
    m[118] = m3[20];
    m[119] = m3[17];
    m[120] = m3[16];
    m[121] = m3[19];
    m[122] = m3[15];
    
    m[123] = m3[7]; 
    m[124] = m3[11];
    m[125] = m3[13];
    m[126] = m3[10];
    m[127] = m3[9]; 
    m[128] = m3[8]; 
    m[129] = m3[12]; 
    
    m[130] = m3[0];
    m[131] = m3[4];
    m[132] = m3[6];
    m[133] = m3[3];
    m[134] = m3[2];
    m[135] = m3[1];
    m[136] = m3[5];
    
    m[137] = true; // player 1 letters
    
    m[138] = m3[21]; // player 2 lettering
    m[139] = m3[22];
    m[140] = m3[23];
    m[141] = m3[24];
    m[142] = m3[27];
    m[143] = m3[25];
    m[144] = m3[26];
    
    m[145] = m3[28];
    m[146] = m3[29];
    m[147] = m3[30];
    m[148] = m3[31];
    m[149] = m3[34];
    m[150] = m3[32];
    m[151] = m3[33];
    
    m[152] = m3[35];
    m[153] = m3[36];
    m[154] = m3[37];
    m[155] = m3[38];
    m[156] = m3[39];
    m[157] = m3[41]; 
    m[158] = m3[40]; 
    m[159] = true; // player 2 lettering
    
    m[160] = m4[14];
    m[161] = m4[18];
    m[162] = m4[20];
    m[163] = m4[17];
    m[164] = m4[16];
    m[165] = m4[15];
    m[166] = m4[19];
    
    m[167] = m4[7];
    m[168] = m4[11];
    m[169] = m4[13];
    m[170] = m4[10];
    m[171] = m4[9];
    m[172] = m4[8];
    m[173] = m4[12];
    
    m[174] = m4[0]; 
    m[175] = m4[4];
    m[176] = m4[6];
    m[177] = m4[3];
    m[178] = m4[2];
    m[179] = m4[1];
    m[180] = m4[5];
    
    m[181] = true; // timer lettering light
    
    m[182] = m5[0]; // game control lights 6
    m[183] = m5[1]; 
    m[184] = m5[2];
    m[185] = m5[3];
    m[186] = m5[4];
    m[187] = m5[5];
    
    //m = testMap();
    
    return m;
  }
  
  private void sendData() {
    boolean[] map = getMasterMap();
    cd.setChannel(map);
    //sendChannelData(cd);
  }
  
  private void repeatSend() {
    //System.out.println("repeat");
    Timer t = new Timer() {
      public void run() {
        sendData();
        repeatSend();
      }
    };
    t.schedule(SnowBallGameFrame.SENDDMX_WAIT);
  }
  
  private void sendChannelData(ChannelData cd) {
    rpc.sendChannelData(cd, new AsyncCallback<Boolean>() {
      public void onSuccess(Boolean b) {
      }
      public void onFailure(Throwable caught) {
        //System.out.println("failed to send");
      }
    });
  }
  
  private boolean[] testMap() {
    
    boolean[] m = new boolean[192];
    m[0] = false;
    m[1] = false;
    m[2] = false;
    m[3] = false;
    m[4] = false;
    m[5] = false;
    m[6] = false;
    m[7] = false;
    m[8] = true;
    m[9] = true;
    m[10] = true;
    m[11] = true;
    m[12] = true;
    m[13] = true;
    m[14] = true;
    m[15] = true;
    m[16] = false;
    m[17] = false;
    m[18] = false;
    m[19] = false;
    m[20] = false;
    m[21] = false;
    m[22] = false;
    m[23] = false;
    m[24] = false;
    m[25] = false;
    m[26] = false;
    m[27] = false;
    m[28] = false;
    m[29] = false;
    m[30] = false;
    m[31] = false;
    m[32] = false;
    m[33] = false;
    m[34] = false;
    m[35] = false;
    m[36] = false;
    m[37] = false;
    m[38] = false;
    m[39] = false;
    m[40] = false;
    m[41] = false;
    m[42] = false;
    m[43] = false;
    m[44] = false;
    m[45] = false;
    m[46] = false;
    m[47] = false;
    m[48] = false;
    m[49] = false;
    m[50] = false;
    m[51] = false;
    m[52] = false;
    m[53] = false;
    m[54] = false;
    m[55] = false;

    
    m[56] = false; // padels 60
    m[57] = false;
    m[58] = false;
    m[59] = false;
    m[60] = false;
    m[61] = false;
    m[62] = false;
    m[63] = false;
    m[64] = false;
    m[65] = false;
    m[66] = false;
    m[67] = false;
    m[68] = false;
    m[69] = false;
    m[70] = false;
    m[71] = false;
    m[72] = false;
    m[73] = false;
    m[74] = false;
    m[75] = false;
    m[76] = false;
    m[77] = false;
    m[78] = false;
    m[79] = false;
    m[80] = false;
    m[81] = false;
    m[82] = false;
    m[83] = false;
    m[84] = false;
    m[85] = false;
    m[86] = false;
    m[87] = false;
    m[88] = false;
    m[89] = false;
    m[90] = false;
    m[91] = false;
    m[92] = false;
    m[93] = false;
    m[94] = false;
    m[95] = false;
    m[96] = false;
    m[97] = false;
    m[98] = false;
    m[99] = false;
    m[100] = false;
    m[101] = false;
    m[102] = false;
    m[103] = false;
    m[104] = false;
    m[105] = false;
    m[106] = false;
    m[107] = false;
    m[108] = false;
    m[109] = false;
    m[110] = false;
    m[111] = false;
    m[112] = false;
    m[113] = false;
    m[114] = false;
    m[115] = false;
    
    m[116] = false; // p1 score digits 42
    m[117] = false;
    m[118] = false;
    m[119] = false;
    m[120] = false;
    m[121] = false;
    m[122] = false;
    m[123] = false;
    m[124] = false;
    m[125] = false;
    m[126] = false;
    m[127] = false;
    m[128] = false;
    m[129] = false;
    m[130] = false;
    m[131] = false;
    m[132] = false;
    m[133] = false;
    m[134] = false;
    m[135] = false;
    m[136] = false;
    m[137] = false;
    m[138] = false;
    m[139] = false;
    m[140] = false;
    m[141] = false;
    m[142] = false;
    m[143] = false;
    m[144] = false;
    m[145] = false;
    m[146] = false;
    m[147] = false;
    m[148] = false;
    m[149] = false;
    m[150] = false;
    m[151] = false;
    m[152] = false;
    m[153] = false;
    m[154] = false;
    m[155] = false;
    m[156] = false;
    m[157] = false;
    
    m[158] = false; // timer 21
    m[159] = false;
    m[160] = false;
    m[161] = false;
    m[162] = false;
    m[163] = false;
    m[164] = false;
    m[165] = false;
    m[166] = false;
    m[167] = false;
    m[168] = false;
    m[169] = false;
    m[170] = false;
    m[171] = false;
    m[172] = false;
    m[173] = false;
    m[174] = false;
    m[175] = false;
    m[178] = false;
    m[179] = false;
    m[180] = false;
    
    m[181] = false; // game control lights 6
    m[182] = false; 
    m[183] = false;
    m[184] = false;
    m[185] = false;
    m[186] = false;
    
    return m;
  }
  
}
