/*# -*- coding: utf-8 -*-
"""
Created on Thu May  9 10:09:02 2019

@author: 
"""
*/

/* 
 * Consists of original BlockData, GasData, GsRead2, and GsCalc driver program
 * almagamated
 */

package gscalc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class GsCalc{


public static String[] name = new String[150];
public static int[] ipr = new int[150];
public static int[] nch = new int[150];
public static int[] nel = new int[150];
public static int[][] nat = new int[5][150];
public static int[][] zat = new int[5][150];
public static double[] ip = new double[150];
public static double[] comp = new double[40];
public static double[] awt = new double[150];

public static double[][] logk = new double[5][150];
public static double[] logwt = new double[150];

public static int[] ntot = new int[150];
public static int[] neut = new int[150];
public static int[] idel = new int[150];
public static int[] natsp = new int[150];
public static int[][] iatsp = new int[40][40];

public static int[][][][][] indx = makeIndx();

public static int[][][][][] makeIndx(){

   int[][][][][] indx = new int[4][26][7][5][2];
   for (int i = 0; i < 4; i++){
       for (int j = 0; j < 26; j++){
           for (int k = 0; k < 7; k++){
               for (int l = 0; l < 5; l++){
                  for (int m = 0; m < 2; m++){
                     indx[i][j][k][l][m] = 149;
                  }
               }
           }
       }
   }

   return indx;

} //end method makeIndx

public static int[] iat = makeIat();
public static int[] indsp = makeIndsp();
public static int[] indzat = makeIndzat();

//Initialization:

public static int[] makeIat(){
   int[] iat = new int[150];
   for (int i = 0; i < 150; i++){
      iat[i] = 39;
   }
   return iat;
}//end method

public static int[] makeIndsp(){
    int[] indsp = new int[40];
    for (int i = 0; i < 40; i++){
       indsp[i] = 149;
    }
    return indsp;
} //end method

public static int[] makeIndzat(){
    int[] indzat = new int[100];
    for (int i = 0; i < 100; i++){
       indzat[i] = 39;
    }
    return indzat;
} //end method


public static int[] ixn = new int[70];


public static int[] lin1 = new int[40];
public static int[] lin2 = new int[40];
public static int[] linv1 = new int[40];
public static int[] linv2 = new int[40];

public static int nspec;
public static int natom;   //#neutral atomic species counter
public static int nlin1;
public static int nlin2;

public static int[] type0 = new int[150];

/*
 *
 *
 */

   public static void main(String[] args){

double kbol = GasInit.kbol;
double hmass = GasInit.hmass;
boolean print0 = GasInit.print0;
int[] itab = GasInit.itab; 
int[] ntab = GasInit.ntab;
int nix = GasInit.nix;
int[][] ixa = GasInit.ixa;
String[] chix = GasInit.chix;
int iprint = GasInit.iprint;

/*
 *
 *
 *
 */

/* Main "Gas data" table for input to GAS package (Phil Bennett) */
//This would require substantial re-arranging to put into a Class :
name[0] = "H";      ipr[0] = 1; nch[0] =  0; nel[0] = 1; nat[0][0] = 1;  zat[0][0] = 1;  awt[0] =  1.008; comp[0] = 9.32e-01;
name[1] = "H+";     ipr[1] = 1; nch[1] = +1; ip[1] = 13.598;  logwt[1] = 0.000;
name[2] = "H-";     ipr[2] = 1; nch[2] = -1; ip[2] =  0.754;  logwt[2] = 0.600;
name[3] = "He";     ipr[3] = 2; nch[3] =  0; nel[3] = 1; nat[0][3] = 1;  zat[0][3] = 2;  awt[3] =  4.003; comp[1] = 6.53e-02;
name[4] = "He+";    ipr[4] = 2; nch[4] = +1; ip[4] = 24.587;  logwt[4] = 0.600;
name[5] = "C";      ipr[5] = 1; nch[5] =  0; nel[5] = 1; nat[0][5] = 1;  zat[0][5] = 6;  awt[5] = 12.011; comp[2] = 4.94e-04;
name[6] = "C+";     ipr[6] = 1; nch[6] = +1; ip[6] = 11.260;  logwt[6] = 0.100;
name[7] = "N";      ipr[7] = 1; nch[7] =  0; nel[7] = 1; nat[0][7] = 1;  zat[0][7] = 7;  awt[7] = 14.007; comp[3] = 8.95e-04;
name[8] = "N+";     ipr[8] = 1; nch[8] = +1; ip[8] = 14.534;  logwt[8] = 0.650;
name[9] = "O";      ipr[9] = 1; nch[9] =  0; nel[9] = 1; nat[0][9] = 1;  zat[0][9] = 8;  awt[9] = 16.000; comp[4] = 8.48e-04;
name[10] = "O+";    ipr[10] = 1; nch[10] = +1; ip[10] = 13.618; logwt[10] = -0.050;
name[11] = "Ne";    ipr[11] = 2; nch[11] =  0; nel[11] = 1; nat[0][11] = 1;  zat[0][11] = 10; awt[11] = 20.179; comp[5] = 7.74e-05;
name[12] = "Ne+";   ipr[12] = 2; nch[12] = +1; ip[12] = 21.564;  logwt[12] = 1.080;
name[13] = "Na";    ipr[13] = 2; nch[13] =  0; nel[13] = 1; nat[0][13] = 1;  zat[0][13] = 11; awt[13] = 22.990; comp[6] = 1.68e-06;
name[14] = "Na+";   ipr[14] = 2; nch[14] = +1; ip[14] =  5.139;  logwt[14] = 0.000;
name[15] = "Mg";    ipr[15] = 2; nch[15] =  0; nel[15] = 1; nat[0][15] = 1;  zat[0][15] = 12; awt[15] = 24.305; comp[7] = 2.42e-05;
name[16] = "Mg+";   ipr[16] = 2; nch[16] = +1; ip[16] =  7.644;  logwt[16] = 0.600;
name[17] = "Mg++";  ipr[17] = 2; nch[17] = +2; ip[17] = 15.031;  logwt[17] = 0.000;
name[18] = "Al";    ipr[18] = 2; nch[18] =  0; nel[18] = 1; nat[0][18] = 1;  zat[0][18] = 13; awt[18] = 26.982; comp[8] = 2.24e-06;
name[19] = "Al+";   ipr[19] = 2; nch[19] = +1; ip[19] =  5.984; logwt[19] = -0.480;
name[20] = "Si";    ipr[20] = 1; nch[20] =  0; nel[20] = 1; nat[0][20] = 1;  zat[0][20] = 14; awt[20] = 28.086; comp[9] = 3.08e-05;
name[21] = "Si+";   ipr[21] = 1; nch[21] = +1; ip[21] =  8.149;  logwt[21] = 0.120;
name[22] = "S";     ipr[22] = 1; nch[22] =  0; nel[22] = 1; nat[0][22] = 1;  zat[0][22] = 16; awt[22] = 32.060; comp[10] = 1.49e-05;
name[23] = "S+";    ipr[23] = 1; nch[23] = +1; ip[23] = 10.360; logwt[23] = -0.050;
name[24] = "Cl";    ipr[24] = 3; nch[24] =  0; nel[24] = 1; nat[0][24] = 1;  zat[0][24] = 17; awt[24] = 35.453; comp[11] = 3.73e-07;
name[25] = "Cl-";   ipr[25] = 3; nch[25] = -1; ip[25] =  3.613;  logwt[25] = 1.080;
name[26] = "K";     ipr[26] = 2; nch[26] =  0; nel[26] = 1; nat[0][26] = 1;  zat[0][26] = 19; awt[26] = 39.102; comp[12] = 8.30e-08;
name[27] = "K+";    ipr[27] = 2; nch[27] = +1; ip[27] =  4.339;  logwt[27] = 0.000;
name[28] = "Ca";    ipr[28] = 2; nch[28] =  0; nel[28] = 1; nat[0][28] = 1;  zat[0][28] = 20; awt[28] = 40.080; comp[13] = 1.86e-06;
name[29] = "Ca+";   ipr[29] = 2; nch[29] = +1; ip[29] =  6.111;  logwt[29] = 0.600;
name[30] = "Ca++";  ipr[30] = 2; nch[30] = +2; ip[30] = 11.868;  logwt[30] = 0.000;
name[31] = "Sc";    ipr[31] = 3; nch[31] =  0; nel[31] = 1; nat[0][31] = 1;  zat[0][31] = 21; awt[31] = 44.956; comp[14] = 1.49e-09;
name[32] = "Sc+";   ipr[32] = 3; nch[32] = +1; ip[32] =  6.540;  logwt[32] = 0.480;
name[33] = "Ti";    ipr[33] = 3; nch[33] =  0; nel[33] = 1; nat[0][33] = 1;  zat[0][33] = 22; awt[33] = 47.900; comp[15] = 1.21e-07;
name[34] = "Ti+";   ipr[34] = 3; nch[34] = +1; ip[34] =  6.820;  logwt[34] = 0.430;
name[35] = "V";     ipr[35] = 3; nch[35] =  0; nel[35] = 1; nat[0][35] = 1;  zat[0][35] = 23; awt[35] = 50.941; comp[16] = 2.33e-08;
name[36] = "V+";    ipr[36] = 3; nch[36] = +1; ip[36] =  6.740;  logwt[36] = 0.250;
name[37] = "Cr";    ipr[37] = 3; nch[37] =  0; nel[37] = 1; nat[0][37] = 1;  zat[0][37] = 24; awt[37] = 51.996; comp[17] = 6.62e-07;
name[38] = "Cr+";   ipr[38] = 3; nch[38] = +1; ip[38] =  6.766;  logwt[38] = 0.230;
name[39] = "Mn";    ipr[39] = 3; nch[39] =  0; nel[39] = 1; nat[0][39] = 1;  zat[0][39] = 25; awt[39] = 54.938; comp[18] = 2.33e-07;
name[40] = "Mn+";   ipr[40] = 3; nch[40] = +1; ip[40] =  7.435;  logwt[40] = 0.370;
name[41] = "Fe";    ipr[41] = 2; nch[41] =  0; nel[41] = 1; nat[0][41] = 1;  zat[0][41] = 26; awt[41] = 55.847; comp[19] = 3.73e-05;
name[42] = "Fe+";   ipr[42] = 2; nch[42] = +1; ip[42] =  7.870;  logwt[42] = 0.380;
name[43] = "Co";    ipr[43] = 3; nch[43] =  0; nel[43] = 1; nat[0][43] = 1;  zat[0][43] = 27; awt[43] = 58.933; comp[20] = 1.12e-07;
name[44] = "Co+";   ipr[44] = 3; nch[44] = +1; ip[44] =  7.860;  logwt[44] = 0.180;
name[45] = "Ni";    ipr[45] = 2; nch[45] =  0; nel[45] = 1; nat[0][45] = 1;  zat[0][45] = 28; awt[45] = 58.710; comp[21] = 1.86e-06;
name[46] = "Ni+";   ipr[46] = 2; nch[46] = +1; ip[46] =  7.635; logwt[46] = -0.020;
name[47] = "Sr";    ipr[47] = 3; nch[47] =  0; nel[47] = 1; nat[0][47] = 1;  zat[0][47] = 38; awt[47] = 87.620; comp[22] = 6.62e-10;
name[48] = "Sr+";   ipr[48] = 3; nch[48] = +1; ip[48] =  5.695;  logwt[48] = 0.500;
name[49] = "Y";     ipr[49] = 3; nch[49] =  0; nel[49] = 1; nat[0][49] = 1;  zat[0][49] = 39; awt[49] = 88.906; comp[23] = 5.87e-11;
name[50] = "Y+";    ipr[50] = 3; nch[50] = +1; ip[50] =  6.380;  logwt[50] = 0.500;
name[51] = "Zr";    ipr[51] = 3; nch[51] =  0; nel[51] = 1; nat[0][51] = 1;  zat[0][51] = 40; awt[51] = 91.220; comp[24] = 2.98e-10;
name[52] = "Zr+";   ipr[52] = 3; nch[52] = +1; ip[52] =  6.840;  logwt[52] = 0.420;
name[53] = "H2";    ipr[53] = 1; nch[53] =  0; nel[53] = 1; nat[0][53] = 2;  zat[0][53] = 1;           logk[0][53] = 12.739; logk[1][53] = -5.1172;  logk[2][53] = 0.12572; logk[3][53] = -1.4149e-02; logk[4][53] = 6.3021e-04;
name[54] = "H2+";   ipr[54] = 1; nch[54] = +1; ip[54] = 15.422;  logwt[54] = 0.600;
name[55] = "C2";    ipr[55] = 1; nch[55] =  0; nel[55] = 1; nat[0][55] = 2;  zat[0][55] = 6;           logk[0][55] = 12.804; logk[1][55] = -6.5178;  logk[2][55] = .097719; logk[3][55] = -1.2739e-02;  logk[4][55] = 6.2603e-04;
name[56] = "C3";    ipr[56] = 1; nch[56] =  0; nel[56] = 1; nat[0][56] = 3;  zat[0][56] = 6;           logk[0][56] = 25.230; logk[1][56] = -14.445;  logk[2][56] = 0.12547; logk[3][56] = -1.7390e-02;  logk[4][56] = 8.8594e-04;
name[57] = "N2";    ipr[57] = 1; nch[57] =  0; nel[57] = 1; nat[0][57] = 2; zat[0][57] = 7;           logk[0][57] = 13.590; logk[1][57] = -10.585;  logk[2][57] = 0.22067; logk[3][57] = -2.9997e-02;  logk[4][57] = 1.4993e-03;
name[58] = "O2";    ipr[58] = 1; nch[58] =  0; nel[58] = 1; nat[0][58] = 2; zat[0][58] = 8;           logk[0][58] = 13.228; logk[1][58] = -5.5181;  logk[2][58] = .069935; logk[3][58] = -8.1511e-03;  logk[4][58] = 3.7970e-04;
name[59] = "CH";    ipr[59] = 1; nch[59] =  0; nel[59] = 2; nat[0][59] = 1; zat[0][59] = 6;  nat[1][59] = 1;  zat[1][59] = 1;  nat[2][59] = 0;  zat[2][59] = 0; logk[0][59] = 12.135; logk[1][59] = -4.0760; logk[2][59] =  0.12768; logk[3][59] = -1.5473e-02; logk[4][59] =  7.2661e-04;
name[60] = "C2H2";  ipr[60] = 1; nch[60] =  0; nel[60] = 2; nat[0][60] = 2; zat[0][60] = 6;  nat[1][60] = 2;  zat[1][60] = 1;  nat[2][60] = 0;  zat[2][60] = 0; logk[0][60] = 38.184; logk[1][60] = -17.365; logk[2][60] =  .021512; logk[3][60] = -8.8961e-05; logk[4][60] = -2.8720e-05;
name[61] = "NH";    ipr[61] = 1; nch[61] =  0; nel[61] = 2; nat[0][61] = 1; zat[0][61] = 7;  nat[1][61] = 1;  zat[1][61] = 1;  nat[2][61] = 0;  zat[2][61] = 0; logk[0][61] = 12.033; logk[1][61] = -3.8435; logk[2][61] =  0.13629; logk[3][61] = -1.6643e-02; logk[4][61] =  7.8691e-04;
name[62] = "NH2";   ipr[62] = 1; nch[62] =  0; nel[62] = 2; nat[0][62] = 1; zat[0][62] = 7;  nat[1][62] = 2;  zat[1][62] = 1;  nat[2][62] = 0;  zat[2][62] = 0; logk[0][62] = 24.603; logk[1][62] = -8.6300; logk[2][62] =  0.20048; logk[3][62] = -2.4124e-02; logk[4][62] =  1.1484e-03;
name[63] = "NH3";   ipr[63] = 1; nch[63] =  0; nel[63] = 2; nat[0][63] = 1; zat[0][63] = 7;  nat[1][63] = 3;  zat[1][63] = 1;  nat[2][63] = 0;  zat[2][63] = 0; logk[0][63] = 37.554; logk[1][63] = -13.059; logk[2][63] =  0.12910; logk[3][63] = -1.2338e-02; logk[4][63] =  5.3429e-04;
name[64] = "OH";    ipr[64] = 1; nch[64] =  0; nel[64] = 2; nat[0][64] = 1; zat[0][64] = 8;  nat[1][64] = 1;  zat[1][64] = 1;  nat[2][64] = 0;  zat[2][64] = 0; logk[0][64] = 12.371; logk[1][64] = -5.0578; logk[2][64] =  0.13822; logk[3][64] = -1.6547e-02; logk[4][64] =  7.7224e-04;
name[65] = "H2O";   ipr[65] = 1; nch[65] =  0; nel[65] = 2; nat[0][65] = 1; zat[0][65] = 8;  nat[1][65] = 2;  zat[1][65] = 1;  nat[2][65] = 0;  zat[2][65] = 0; logk[0][65] = 25.420; logk[1][65] = -10.522; logk[2][65] =  0.16939; logk[3][65] = -1.8368e-02; logk[4][65] =  8.1730e-04;
name[66] = "MgH";   ipr[66] = 2; nch[66] =  0; nel[66] = 2; nat[0][66] = 1; zat[0][66] = 12; nat[1][66] = 1;  zat[1][66] = 1;  nat[2][66] = 0;  zat[2][66] = 0; logk[0][66] = 11.285; logk[1][66] = -2.7164; logk[2][66] =  0.19658; logk[3][66] = -2.7310e-02; logk[4][66] =  1.3816e-03;
name[67] = "AlH";   ipr[67] = 2; nch[67] =  0; nel[67] = 2; nat[0][67] = 1; zat[0][67] = 13; nat[1][67] = 1;  zat[1][67] = 1;  nat[2][67] = 0;  zat[2][67] = 0; logk[0][67] = 12.191; logk[1][67] = -3.7636; logk[2][67] =  0.25557; logk[3][67] = -3.7261e-02; logk[4][67] =  1.9406e-03;
name[68] = "SiH";   ipr[68] = 1; nch[68] =  0; nel[68] = 2; nat[0][68] = 1; zat[0][68] = 14; nat[1][68] = 1;  zat[1][68] = 1;  nat[2][68] = 0;  zat[2][68] = 0; logk[0][68] = 11.852; logk[1][68] = -3.7418; logk[2][68] =  0.15999; logk[3][68] = -2.0629e-02; logk[4][68] =  9.9897e-04;
name[69] = "HS";    ipr[69] = 1; nch[69] =  0; nel[69] = 2; nat[0][69] = 1; zat[0][69] = 16; nat[1][69] = 1;  zat[1][69] = 1;  nat[2][69] = 0;  zat[2][69] = 0; logk[0][69] = 12.019; logk[1][69] = -4.2922; logk[2][69] =  0.14913; logk[3][69] = -1.8666e-02; logk[4][69] =  8.9438e-04;
name[70] = "H2S";   ipr[70] = 1; nch[70] =  0; nel[70] = 2; nat[0][70] = 1; zat[0][70] = 16; nat[1][70] = 2;  zat[1][70] = 1;  nat[2][70] = 0;  zat[2][70] = 0; logk[0][70] = 24.632; logk[1][70] = -8.4616; logk[2][70] =  0.17014; logk[3][70] = -2.0236e-02; logk[4][70] =  9.5782e-04;
name[71] = "HCl";   ipr[71] = 3; nch[71] =  0; nel[71] = 2; nat[0][71] = 1; zat[0][71] = 17; nat[1][71] = 1;  zat[1][71] = 1;  nat[2][71] = 0;  zat[2][71] = 0; logk[0][71] = 12.528; logk[1][71] = -5.1827; logk[2][71] =  0.18117; logk[3][71] = -2.4014e-02; logk[4][71] =  1.1994e-03;
name[72] = "CaH";   ipr[72] = 3; nch[72] =  0; nel[72] = 2; nat[0][72] = 1; zat[0][72] = 20; nat[1][72] = 1;  zat[1][72] = 1;  nat[2][72] = 0;  zat[2][72] = 0; logk[0][72] = 11.340; logk[1][72] = -3.0144; logk[2][72] =  0.42349; logk[3][72] = -6.1467e-02; logk[4][72] =  3.1639e-03;
name[73] = "CN";    ipr[73] = 1; nch[73] =  0; nel[73] = 2; nat[0][73] = 1; zat[0][73] = 7;  nat[1][73] = 1;  zat[1][73] = 6;  nat[2][73] = 0;  zat[2][73] = 0; logk[0][73] = 12.805; logk[1][73] = -8.2793; logk[2][73] =  .064162; logk[3][73] = -7.3627e-03; logk[4][73] =  3.4666e-04;
name[74] = "NO";    ipr[74] = 1; nch[74] =  0; nel[74] = 2; nat[0][74] = 1; zat[0][74] = 8;  nat[1][74] = 1;  zat[1][74] = 7;  nat[2][74] = 0;  zat[2][74] = 0; logk[0][74] = 12.831; logk[1][74] = -7.1964; logk[2][74] =  0.17349; logk[3][74] = -2.3065e-02; logk[4][74] =  1.1380e-03;
name[75] = "CO";    ipr[75] = 1; nch[75] =  0; nel[75] = 2; nat[0][75] = 1; zat[0][75] = 8;  nat[1][75] = 1;  zat[1][75] = 6;  nat[2][75] = 0;  zat[2][75] = 0; logk[0][75] = 13.820; logk[1][75] = -11.795; logk[2][75] =  0.17217; logk[3][75] = -2.2888e-02; logk[4][75] =  1.1349e-03;
name[76] = "CO2";   ipr[76] = 1; nch[76] =  0; nel[76] = 2; nat[0][76] = 2; zat[0][76] = 8;  nat[1][76] = 1;  zat[1][76] = 6;  nat[2][76] = 0;  zat[2][76] = 0; logk[0][76] = 27.478; logk[1][76] = -17.098; logk[2][76] =  .095012; logk[3][76] = -1.2579e-02; logk[4][76] =  6.4058e-04;
name[77] = "MgO";   ipr[77] = 3; nch[77] =  0; nel[77] = 2; nat[0][77] = 1; zat[0][77] = 12; nat[1][77] = 1;  zat[1][77] = 8;  nat[2][77] = 0;  zat[2][77] = 0; logk[0][77] = 11.702; logk[1][77] = -5.0326; logk[2][77] =  0.29641; logk[3][77] = -4.2811e-02; logk[4][77] =  2.2023e-03;
name[78] = "AlO";   ipr[78] = 2; nch[78] =  0; nel[78] = 2; nat[0][78] = 1; zat[0][78] = 13; nat[1][78] = 1;  zat[1][78] = 8;  nat[2][78] = 0;  zat[2][78] = 0; logk[0][78] = 12.739; logk[1][78] = -5.2534; logk[2][78] =  0.18218; logk[3][78] = -2.5793e-02; logk[4][78] =  1.3185e-03;
name[79] = "SiO";   ipr[79] = 1; nch[79] =  0; nel[79] = 2; nat[0][79] = 1; zat[0][79] = 14; nat[1][79] = 1;  zat[1][79] = 8;  nat[2][79] = 0;  zat[2][79] = 0; logk[0][79] = 13.413; logk[1][79] = -8.8710; logk[2][79] =  0.15042; logk[3][79] = -1.9581e-02; logk[4][79] =  9.4828e-04;
name[80] = "SO";    ipr[80] = 1; nch[80] =  0; nel[80] = 2; nat[0][80] = 1; zat[0][80] = 16; nat[1][80] = 1;  zat[1][80] = 8;  nat[2][80] = 0;  zat[2][80] = 0; logk[0][80] = 12.929; logk[1][80] = -6.0100; logk[2][80] =  0.16253; logk[3][80] = -2.1665e-02; logk[4][80] =  1.0676e-03;
name[81] = "CaO";   ipr[81] = 2; nch[81] =  0; nel[81] = 2; nat[0][81] = 1; zat[0][81] = 20; nat[1][81] = 1;  zat[1][81] = 8;  nat[2][81] = 0;  zat[2][81] = 0; logk[0][81] = 12.260; logk[1][81] = -6.0525; logk[2][81] =  0.58284; logk[3][81] = -8.5805e-02; logk[4][81] =  4.4425e-03;
name[82] = "ScO";   ipr[82] = 3; nch[82] =  0; nel[82] = 2; nat[0][82] = 1; zat[0][82] = 21; nat[1][82] = 1;  zat[1][82] = 8;  nat[2][82] = 0;  zat[2][82] = 0; logk[0][82] = 13.747; logk[1][82] = -8.6420; logk[2][82] =  0.48072; logk[3][82] = -6.9670e-02; logk[4][82] =  3.5747e-03;
name[83] = "ScO2";  ipr[83] = 3; nch[83] =  0; nel[83] = 2; nat[0][83] = 1; zat[0][83] = 21; nat[1][83] = 2;  zat[1][83] = 8;  nat[2][83] = 0;  zat[2][83] = 0; logk[0][83] = 26.909; logk[1][83] = -15.824; logk[2][83] =  0.39999; logk[3][83] = -5.9363e-02; logk[4][83] =  3.0875e-03;
name[84] = "TiO";   ipr[84] = 2; nch[84] =  0; nel[84] = 2; nat[0][84] = 1; zat[0][84] = 22; nat[1][84] = 1;  zat[1][84] = 8;  nat[2][84] = 0;  zat[2][84] = 0; logk[0][84] = 13.398; logk[1][84] = -8.5956; logk[2][84] =  0.40873; logk[3][84] = -5.7937e-02; logk[4][84] =  2.9287e-03;
name[85] = "VO";    ipr[85] = 3; nch[85] =  0; nel[85] = 2; nat[0][85] = 1; zat[0][85] = 23; nat[1][85] = 1;  zat[1][85] = 8;  nat[2][85] = 0;  zat[2][85] = 0; logk[0][85] = 13.811; logk[1][85] = -7.7520; logk[2][85] =  0.37056; logk[3][85] = -5.1467e-02; logk[4][85] =  2.5861e-03;
name[86] = "VO2";   ipr[86] = 3; nch[86] =  0; nel[86] = 2; nat[0][86] = 1; zat[0][86] = 23; nat[1][86] = 2;  zat[1][86] = 8;  nat[2][86] = 0;  zat[2][86] = 0; logk[0][86] = 27.754; logk[1][86] = -14.040; logk[2][86] =  0.33613; logk[3][86] = -4.8215e-02; logk[4][86] =  2.4780e-03;
name[87] = "YO";    ipr[87] = 3; nch[87] =  0; nel[87] = 2; nat[0][87] = 1; zat[0][87] = 39; nat[1][87] = 1;  zat[1][87] = 8;  nat[2][87] = 0;  zat[2][87] = 0; logk[0][87] = 13.514; logk[1][87] = -8.7775; logk[2][87] =  0.40700; logk[3][87] = -5.8053e-02; logk[4][87] =  2.9535e-03;
name[88] = "YO2";   ipr[88] = 3; nch[88] =  0; nel[88] = 2; nat[0][88] = 1; zat[0][88] = 39; nat[1][88] = 2;  zat[1][88] = 8;  nat[2][88] = 0;  zat[2][88] = 0; logk[0][88] = 26.764; logk[1][88] = -16.447; logk[2][88] =  0.39991; logk[3][88] = -5.8916e-02; logk[4][88] =  3.0506e-03;
name[89] = "ZrO";   ipr[89] = 3; nch[89] =  0; nel[89] = 2; nat[0][89] = 1; zat[0][89] = 40; nat[1][89] = 1;  zat[1][89] = 8;  nat[2][89] = 0;  zat[2][89] = 0; logk[0][89] = 13.296; logk[1][89] = -9.0129; logk[2][89] =  0.19562; logk[3][89] = -2.9892e-02; logk[4][89] =  1.6010e-03;
name[90] = "ZrO2";  ipr[90] = 3; nch[90] =  0; nel[90] = 2; nat[0][90] = 1; zat[0][90] = 40; nat[1][90] = 2;  zat[1][90] = 8;  nat[2][90] = 0;  zat[2][90] = 0; logk[0][90] = 26.793; logk[1][90] = -16.151; logk[2][90] =  0.46988; logk[3][90] = -6.4636e-02; logk[4][90] =  3.2277e-03;
name[91] = "CS";    ipr[91] = 1; nch[91] =  0; nel[91] = 2; nat[0][91] = 1; zat[0][91] = 16; nat[1][91] = 1;  zat[1][91] = 6;  nat[2][91] = 0;  zat[2][91] = 0; logk[0][91] = 13.436; logk[1][91] = -8.5574; logk[2][91] =  0.18754; logk[3][91] = -2.5507e-02; logk[4][91] =  1.2735e-03;
name[92] = "SiS";   ipr[92] = 1; nch[92] =  0; nel[92] = 2; nat[0][92] = 1; zat[0][92] = 14; nat[1][92] = 1;  zat[1][92] = 16; nat[2][92] = 0;  zat[2][92] = 0; logk[0][92] = 13.182; logk[1][92] = -7.1147; logk[2][92] =  0.19300; logk[3][92] = -2.5826e-02; logk[4][92] =  1.2648e-03;
name[93] = "TiS";   ipr[93] = 2; nch[93] =  0; nel[93] = 2; nat[0][93] = 1; zat[0][93] = 22; nat[1][93] = 1;  zat[1][93] = 16; nat[2][93] = 0;  zat[2][93] = 0; logk[0][93] = 13.316; logk[1][93] = -6.2216; logk[2][93] =  0.45829; logk[3][93] = -6.4903e-02; logk[4][93] =  3.2788e-03;
name[94] = "SiC";   ipr[94] = 1; nch[94] =  0; nel[94] = 2; nat[0][94] = 1; zat[0][94] = 14; nat[1][94] = 1;  zat[1][94] = 6;  nat[2][94] = 0;  zat[2][94] = 0; logk[0][94] = 12.327; logk[1][94] = -5.0419; logk[2][94] =  0.13941; logk[3][94] = -1.9363e-02; logk[4][94] =  9.6202e-04;
name[95] = "SiC2";  ipr[95] = 1; nch[95] =  0; nel[95] = 2; nat[0][95] = 1; zat[0][95] = 14; nat[1][95] = 2;  zat[1][95] = 6;  nat[2][95] = 0;  zat[2][95] = 0; logk[0][95] = 25.623; logk[1][95] = -13.085; logk[2][95] = -.055227; logk[3][95] =  9.3363e-03; logk[4][95] = -4.9876e-04;
name[96] = "NaCl";  ipr[96] = 2; nch[96] =  0; nel[96] = 2; nat[0][96] = 1; zat[0][96] = 11; nat[1][96] = 1;  zat[1][96] = 17; nat[2][96] = 0;  zat[2][96] = 0; logk[0][96] = 11.768; logk[1][96] = -4.9884; logk[2][96] =  0.23975; logk[3][96] = -3.4837e-02; logk[4][96] =  1.8034e-03;
name[97] = "MgCl";  ipr[97] = 2; nch[97] =  0; nel[97] = 2; nat[0][97] = 1; zat[0][97] = 12; nat[1][97] = 1;  zat[1][97] = 17; nat[2][97] = 0;  zat[2][97] = 0; logk[0][97] = 11.318; logk[1][97] = -4.2224; logk[2][97] =  0.21137; logk[3][97] = -3.0174e-02; logk[4][97] =  1.5480e-03;
name[98] = "AlCl";  ipr[98] = 2; nch[98] =  0; nel[98] = 2; nat[0][98] = 1; zat[0][98] = 13; nat[1][98] = 1;  zat[1][98] = 17; nat[2][98] = 0;  zat[2][98] = 0; logk[0][98] = 11.976; logk[1][98] = -5.2228; logk[2][98] = -.010263; logk[3][98] =  3.9344e-03; logk[4][98] = -2.6236e-04;
name[99] = "CaCl";  ipr[99] = 2; nch[99] =  0; nel[99] = 2; nat[0][99] = 1; zat[0][99] = 20; nat[1][99] = 1;  zat[1][99] = 17; nat[2][99] = 0;  zat[2][99] = 0; logk[0][99] = 12.314; logk[1][99] = -5.1814; logk[2][99] =  0.56532; logk[3][99] = -8.2868e-02; logk[4][99] =  4.2822e-03;
name[100] = "HCN";  ipr[100] = 1; nch[100] =  0; nel[100] = 3; nat[0][100] = 1; zat[0][100] = 7;  nat[1][100] = 1;  zat[1][100] = 6;  nat[2][100] = 1;  zat[2][100] = 1; logk[0][100] = 25.635; logk[1][100] = -13.833; logk[2][100] =  0.13827; logk[3][100] = -1.8122e-02; logk[4][100] =  9.1645e-04;
name[101] = "HCO";  ipr[101] = 1; nch[101] =  0; nel[101] = 3; nat[0][101] = 1; zat[0][101] = 8;  nat[1][101] = 1;  zat[1][101] = 6;  nat[2][101] = 1;  zat[2][101] = 1; logk[0][101] = 25.363; logk[1][101] = -13.213; logk[2][101] =  0.18451; logk[3][101] = -2.2973e-02; logk[4][101] =  1.1114e-03;
name[102] = "MgOH"; ipr[102] = 2; nch[102] =  0; nel[102] = 3; nat[0][102] = 1; zat[0][102] = 12; nat[1][102] = 1;  zat[1][102] = 8;  nat[2][102] = 1;  zat[2][102] = 1; logk[0][102] = 24.551; logk[1][102] = -9.3818; logk[2][102] =  0.19666; logk[3][102] = -2.7178e-02; logk[4][102] =  1.3887e-03;
name[103] = "AlOH"; ipr[103] = 2; nch[103] =  0; nel[103] = 3; nat[0][103] = 1; zat[0][103] = 13; nat[1][103] = 1;  zat[1][103] = 8;  nat[2][103] = 1;  zat[2][103] = 1; logk[0][103] = 25.707; logk[1][103] = -10.624; logk[2][103] =  .097901; logk[3][103] = -1.1835e-02; logk[4][103] =  5.8121e-04;
name[104] = "CaOH"; ipr[104] = 2; nch[104] =  0; nel[104] = 3; nat[0][104] = 1; zat[0][104] = 20; nat[1][104] = 1;  zat[1][104] = 8;  nat[2][104] = 1;  zat[2][104] = 1; logk[0][104] = 24.611; logk[1][104] = -10.910; logk[2][104] =  0.60803; logk[3][104] = -8.7197e-02; logk[4][104] =  4.4736e-03;

/*
#############################################
#
#
#
#    Initial set-up:
#     - import all python modules
#     - set input parameters 
#     
#
#
##############################################
*/


String absPath0 = "./";  //#default

String absPath = absPath0 + '/';

//##makePlot = Input.makePlot
//#makePlot = "yes"
//#print("")
//#print("Will make plot: ", makePlot)
//#print("")
//#stop
//#color platte for plt plotting
//#palette = ['black', 'brown','red','orange','yellow','green','blue','indigo','violet']
//#grayscale
//#stop
//#Grayscale:
int numPal = 12;
String[] palette = new String[numPal];
double delPal = 0.04;
//#for i in range(numPal):
//#    ii = float(i)
//#    helpPal = 0.481 - ii*delPal
//#    palette[i] = str(helpPal) 

for (int i = 0; i < numPal; i++){
    palette[i] = Double.toString( 0.481 - Double.valueOf(i)*delPal );
}
int numClrs = palette.length;


//#General file for printing ad hoc quantities
//#dbgHandle = open("debug.out", 'w')
String outPath = absPath + "/Outputs/";

//#fileStem = Input.fileStem
String fileStem = "GsCalc";

//#Set input pressure and isolv here for file naming:
double pt = 1.0e5;
int isolv = 1;

fileStem = fileStem + ".Read2." + "pt" + Double.toString((int)(Math.log10(pt))).trim() + "is" + Integer.toString(isolv);
String outFileString = outPath+fileStem+".out";  //#Report for humans
String outFileString2 = outPath+fileStem+".2.out"; //#PPs for plotting
System.out.println(" ");
System.out.println("Writing to files "+ outFileString);
System.out.println(" ");
//outFile = open(outFileString, "w")
//outFile2 = open(outFileString2, "w")
//Path path = FileSystems.getDefault().getPath(".", name);
Path path = FileSystems.getDefault().getPath(outPath, fileStem+".out");
Path path2 = FileSystems.getDefault().getPath(outPath, fileStem+".2.out");
//Create the files:
try{
   Files.write( path, "".getBytes(), StandardOpenOption.CREATE); 
   } catch (Exception e) {
     System.out.println("path: Caught exception");
   } 
try{
   Files.write( path2, "".getBytes(), StandardOpenOption.CREATE); 
   } catch (Exception e) {
     System.out.println("path2: Caught exception");
   }


String outString = "";


 double[] p0 = new double[40];
 double[] pp = new double[150];
 double[] p = new double[40];
 double[] ppix = new double[30];
 double[] a = new double[625];

int neq;

double pe, gmu, rho, rholog;

double fe;

//#c cis:
     
 double[] fp = new double[150];

//#print("Calling GsRead:")

    int nelt, nat1, nleft, nats, nsp1;
    int j, k, kp, nn;

    double sum0;
 
    double[] it = new double[150];   
    double[] kt = new double[150];  

 
    //int[] type0 = new int[150];
   
    int iprt = 0;
    int ncht = 0;
    int[] ix = new int[5];
    
    //#blank = ' '
    String ename = "e-";
    int mxatom = 30;
    int mxspec = 150;
   
    int n = 0;   //#record counter
    int np = 0;
    natom = -1;   //#neutral atomic species counter
    nlin1 = -1;
    nlin2 = -1;
    double tcomp = 0.0e0;
   
    if (iprint == 0){
        print0 = false;
    } else {
        print0 = true;
    }

    //#nspec = len(name)
    //#print("nspec ", nspec)
    while (name[n] != null){
    //#for n in range(nspec):    
        //#c
        //#c Each following input line specifies a distinct chemical species.
	//#c
    
    //#1 
        //#namet = name[n]
        iprt = ipr[n];
        ncht = nch[n];
        idel[n] = 1;  
        //#print("iprt ", iprt, " ncht ", ncht)
    //#c
    //#c Determine the species type:
    //#c TYPE(N) = 1 --> Neutral atom
    //#c         = 2 --> Neutral molecule
    //#c         = 3 --> Negative ion
    //#c         = 4 --> Positive ion
    //#c
           
        if (nch[n] == 0){
//#c
//#c Species is neutral
//#c
            np = n;
            nelt = nel[n];
            nat1 = nat[0][n];
        
            if (nelt <= 1 && nat1 <= 1){
//#c
//#c Neutral atom (one atom of single element Z present)
//#c
                type0[n] = 1;
                natom = natom + 1;
                if (natom >= mxatom){
                    System.out.println(" *20 Error: Too many elements specified. " + "  Limit is " + mxatom);
                } 
        
                iat[n] = natom;
                //#print("Setting indsp, n: ", n, " natom ", natom)
                indsp[natom] = n;  //#pointer to iat[], etc....
                //System.out.println("n " + n + " zat[0][n] " + zat[0][n]);
                indzat[zat[0][n]-1] = natom;   //#indzat's index is atomic number - 1
                ntot[n] = 1;
                neut[n] = n;

                tcomp = tcomp + comp[natom];
                iprt = ipr[n];
                if (iprt == 1){
                    nlin1 = nlin1 + 1;
                    lin1[natom] = nlin1;
                    linv1[nlin1] = natom;
                }

                if ( (iprt == 1) || (iprt == 2) ){
                    nlin2 = nlin2 + 1;
                    lin2[natom] = nlin2;
                    linv2[nlin2] = natom;
                }
                        
            } else{  // <= 1 and nat1 <= 1 <= 1 and nat1 <= 1 condition
                    
//#c
//#c Neutral molecule ( >1 atom present in species)
//#c
                type0[n] = 2;
                ntot[n] = nat1;
                neut[n] = n;
                
                nleft = (nelt - 1)*2;
                //#print("Neutral mol: n ", n, " name ", name[n], " nelt ", nelt, " nleft ", nleft)
                    
                if (nleft > 0){
                    for (int ii = 1; ii < 3; ii++){ 
                        ntot[n] = ntot[n] + nat[ii][n];
                    }
                }
            //#print("5: n ", n, " logk ", logk[0][n], " ", logk[1][n], " ", logk[2][n], " ", logk[3][n], " ", logk[4][n])
            }   // <= 1 and nat1 <= 1 <= 1 and nat1 <= 1 condition
        } else {  //nch[n]=0 condition   
//#c
//#c Ionic species (nch .ne. 0)
//#c
            
            if (np <= -1){
                System.out.println(" *** error: ionic species encountered out of " + " sequence");
            }
            
            if (ncht < 0){
                type0[n] = 3;
            } else if (ncht > 0){
                type0[n] = 4;
            }
          
            neut[n] = np;
            nel[n] = nel[np];
            nelt = nel[n];
            for (int i = 0; i < nelt; i++){ 
                nat[i][n] = nat[i][np];
                zat[i][n] = zat[i][np];
            }

            ntot[n] = ntot[np];
         }  //nch[n]=0 condition

//#print("6: n ", n, " ip ", ip[n], " logwt ", logwt[n])
        
//#c
//#c Generate master array tying chemical formula of species to
//#c its table index. A unique index is generated for a given
//#c (possibly charged) species containing up to 4 atoms.
//#c
//#c Index #1 <--  Ionic charge + 2  (dim. 4, allows chg -1 to +2) 
//#c       #2 <--> Index to Z of 1st atom in species (23 allowed Z)
//#c       #3 <-->    "          2nd        "        ( 6 allowed Z)
//#c       #4 <-->    "          3rd        "        ( 4 allowed Z)
//#c       #5 <-->    "          4th        "        ( 1 allowed Z)
//#c

        //#ix[0] = nch[n] + 2;
        ix[0] = nch[n] + 1;
        nelt = nel[n];
        //#k = 1;
        k = 0;
        
        //#print("n ", n, " name ", name[n])
        for (int i = 0; i < nelt; i++){
                
            nats = nat[i][n];
            for (int j99 = 0; j99 < nats; j99++){
                    
                k = k + 1;
                if (k > 4){
                    System.out.println(" *21 Error: species " + " contains > 4 atoms " + name[n]);
                }

                ix[k] = itab[zat[i][n]-1];
                //System.out.println("n "+ n + " name "+ name[n]+ " k "+ k+ " i "+ i+ " n "+ n+ " zat "+ zat[i][n]+ " itab "+ itab[zat[i][n]-1]);
                //#print("i ", i, " j ", j, " k ", k, " ix ", ix[k], "ntab ", ntab[k])
                //#print("zat-1 ", zat[i][n]-1, "itab ", itab[zat[i][n]-1])
                if ( (ix[k] <= 0) || (ix[k] > ntab[k]) ){
                    //System.out.println(" *22 Error: species atom z= not in allowed element list" 
                    //  + name[n] + " " + (zat[i][n]-1).toString());
                }
            }
        } 
     
        if (k < 4){
            //System.out.println("k < 4, k= "+ k );
            kp = k + 1;
            for (int kk = kp; kk < 5; kk++){
                //System.out.println("kk "+ kk);
                ix[kk] = 0;
            }
            //#print("kk ", kk, " ix ", ix[kk])
        }
        

        indx[ix[0]][ix[1]][ix[2]][ix[3]][ix[4]] = n;
        //System.out.println("n " + n + " name " + name[n] + " ix[] " + ix[0] + " " + ix[1] + " " + ix[2] + " " + ix[3] + " " + ix[4]);
        n = n + 1;
            //#print("n ", n, " name ", name[n], " ix ", ix[0], ix[1], ix[2], ix[3], ix[4],\
            //#      " indx ", indx[ix[0]][ix[1]][ix[2]][ix[3]][ix[4]])
                       
    }//end while
    //#go to 1
    //#Ends if namet != ''??
        
    //#Get next line of data and test of end-of-file:
    //#gsline = inputHandle.readline()
    //#lineLength = len(gsline)
            //#print("lineLength = ", lineLength)
    //#Ends file read loop "with open(infile...??)
        
    //#After read loop:
    
//#c
//#c Normalize abundances such that SUM(COMP) = 1
//#c
    nspec = n;
    //#name[nspec+1] = ename
    name[nspec] = ename;
    iat[mxspec-1] = mxatom;
    comp[mxatom-1] = 0.0e0;
    neut[mxspec-1] = mxspec;
    nsp1 = nspec + 1;

    for (int n99 = nsp1-1; n99 < mxspec; n99++){ 
        idel[n99] = 0;
    }
        
        
    //#print("GsRead: nspec ", nspec, " natom ", natom)
    if (nspec != 0){
        
        for (int j99 = 0; j99 < natom; j99++){ 
            natsp[j99] = - 1;
            comp[j99] = comp[j99]/tcomp;
        }

//#c
//#c Calculate the atomic (molecular) weight of each constituent
//#c
        for (int n99 = 0; n99 < nspec; n99++){
            
            //#print("name ", name[n], " nel ", nel[n])
            nelt = nel[n99];
            sum0 = 0.0e0;
            iprt = ipr[n99];
            
            for (int i = 0; i < nelt; i++){
                
                //#print("i ", i, " n ", n, " zat ", zat[i][n]-1, " indzat ", indzat[zat[i][n]-1])
                j = indzat[zat[i][n99]-1];
                //#print("j ", j)
                nn = indsp[j];
                //#print(" nn ", nn)
                natsp[j] = natsp[j] + 1;
                iatsp[j][natsp[j]] = n99;
                sum0 = sum0 + nat[i][n99]*awt[nn];
                if (ipr[nn] > iprt){ 
                    iprt = ipr[nn];
                }
            }
            awt[n99] = sum0;
            ipr[n99] = iprt;
        }
    
//#c
//#c Fill array of direct indices of species needed for opacity
//#c calculations.
//#c
        if (nix > 0){
            for (int i = 0; i < nix; i++){ 
                ixn[i] = indx[ixa[0][i]][ixa[1][i]][ixa[2][i]][ixa[3][i]][ixa[4][i]];
                //System.out.println("i "+ i+ " indx " +
                //  indx[ixa[0][i]][ixa[1][i]][ixa[2][i]][ixa[3][i]][ixa[4][i]] +
                //  " ixa[] "+ ixa[0][i]+ " " + ixa[1][i]+ " " + ixa[2][i]+ " " + ixa[3][i]+ " " + ixa[4][i]);
                if (ixn[i] == 149){
                    System.out.println("0*** Warning: Opacity source " + " not included in GAS data tables " + chix[i]);
                }
            }
        }

//#c
//#c Output species table
//#c
//        #print("I am here!")
        int ityp, ii;
        for (int j99 = 1; j99 < 5; j99++){
            //#outString = "j99 " + str(j) + "\n"
            //#outFile.write(outString)
            if (j99 == 1){
                outString = String.format("1 %5s %10s %8s %5s %7s\n", "#", "Name", "At.Weight", "Z", "Abundance");
                //#outFile.write("1  #  Name      At.Weight   Z   Abundance\n")
                try{
                   Files.write( path, outString.getBytes(), StandardOpenOption.APPEND); 
                } catch (Exception e) {
                   System.out.println("path: Caught exception");
                } 
            } else if (j99 == 2){
                outString = String.format("0 %5s %10s %8s %5s", "#", "Name", "At.Weight", "Nel");
                outString = outString + " n1   Z1   n2   Z2 ...\n";
                //#outFile.write("0  #  Name      At.Weight Nel   n1   Z1   n2   Z2 ...\n")
                try{
                   Files.write( path, outString.getBytes(), StandardOpenOption.APPEND);
                } catch (Exception e) {
                   System.out.println("path: Caught exception");
                }
            } else if (j99 == 3){
                outString = "0  #  Name      At.Weight Chg  Natom    I.P.   Log(2*g1/g0)\n";
                try{
                   Files.write( path, outString.getBytes(), StandardOpenOption.APPEND);
                } catch (Exception e) {
                   System.out.println("path: Caught exception");
                }
            }

            for (int i99 = 0; i99 < nspec; i99++){
                ityp = type0[i99];
                //#outString = "i99 " + str(i99) + " type " + str(type0[i99]) + "\n"
                //#outFile.write(outString)
                if (ityp == j99){
                    if (ityp == 1){
                        ii = iat[i99];
                        //#print("i99 ", i99, " iat ", iat[i99])
                        //#outString = str(i99) + " " + str(name[i99]) + " " + str(awt[i99]) + " " + str(zat[0][i99]) + " " + str(comp[ii]) + "\n"
                        outString = String.format("%5d %10s %8.3f %5d %7.2e\n", i99, name[i99], awt[i99], zat[0][i99], comp[ii]);
                        try{
                           Files.write( path, outString.getBytes(), StandardOpenOption.APPEND);
                        } catch (Exception e) {
                           System.out.println("path: Caught exception");
                        }
  
                    } else if (ityp == 2){
                        nelt = nel[i99];
                        //#outString = str(i99) + " " + str(name[i99]) + " " + str(awt[i99]) + " " + str(nelt) + "\n"
                        outString = String.format("%5d %10s %8.3f %5d", i99, name[i99], awt[i99], nelt);
                        //#outFile.write(outString)
                        for (int k99 = 0; k99 < nelt; k99++){
                            outString = outString + "  " + Integer.toString(nat[k99][i99]) + "  " + Integer.toString(zat[k99][i99]);
                        }
                        outString += "\n";
                        try{
                           Files.write( path, outString.getBytes(), StandardOpenOption.APPEND);
                        } catch (Exception e) {
                           System.out.println("path: Caught exception");
                        }

                    } else{
                        outString = String.format("%5d %10s %8.3f %6d %6d %10.3f %7.3f\n" , i99, name[i99], awt[i99], nch[i99], neut[i99], ip[i99], logwt[i99]);
                        //#outString = str(i99) + " " + str(name[i99]) + " " + str(awt[i99]) + " " + str(nch[i99]) + " " + str(neut[i99]) + " " + str(ip[i99]) + " " + str(logwt[i99]) + "\n"
                        try{
                           Files.write( path, outString.getBytes(), StandardOpenOption.APPEND);
                        } catch (Exception e) {
                           System.out.println("path: Caught exception");
                        }

                    }
                } //if ityp = j99 
            } //i99 loop
        } //j99 loop
        //#cis: Try this:
        nlin1+=1;
        nlin2+=1;
        natom+=1;

    } // if nspec !=0 condition


//int nspec = GsRead2.nspec;
//String[] name = GsRead2.name;
//#print("GsCalc: nspec: ", nspec)
outString = "";
for (int k99 = 0; k99 < nspec; k99++){
    outString = outString + " " + name[k99];
}
outString+="\n";
//outFile2.write(outString)
try{
     Files.write( path2, outString.getBytes(), 
                     StandardOpenOption.APPEND); 
   } catch (Exception e) {
     System.out.println("path2: Caught exception");
   }

//#For now:
//#t = 6000.0
double t1 = 1500.0;
double t2 = 6500.0;
double dt = 100.0;
//# Set this above: pt = 100000.0
//#pe0 = 100.0
double tol = 1.0e-4;
int maxit = 100;
//# Set this above: isolv = 1

double ntF = (t2 - t1) / dt + 1.0;
int nt = (int) ntF;
double pe0 = 0.01 * pt;

outString = String.format("%4s %12.3e\n" ,"PT ", pt);
System.out.println(outString); //test
//outFile.write(outString)
//outFile2.write(outString)
try{
     Files.write( path, outString.getBytes(), 
                     StandardOpenOption.APPEND); 
     Files.write( path2, outString.getBytes(), 
                     StandardOpenOption.APPEND); 
   } catch (Exception e) {
     System.out.println("path, path2: Caught exception");
   }

//String outStringHead = String.format("%11s %8s %5s %4s %4s %8s\n",
//             "t ", " rholog ", " gmu ", " fd ", " fe ", " fp(k) ");
String outStringHead = String.format("%11s %8s %5s %4s %8s\n",
             "t ", " rholog ", " gmu ", " fe ", " fp(k) ");
//outFile2.write(outStringHead)     
try{
     Files.write( path2, outStringHead.getBytes(),
                     StandardOpenOption.APPEND);
   } catch (Exception e) {
     System.out.println("path2: Caught exception");
   }

//#testing:
//#tol = 1.0e-1
//#maxit = 1

//original = sys.stdout
//#sys.stdout = open('./redirect.txt', 'w')

   double kF, t; 

for (int kI = 0; kI < nt; kI++){
    
    kF = Double.valueOf(kI);
    t = t1 + dt*kF;
    
    //#Try making return value a tuple:
    //#print("Before GasEst pe0 ", pe0)
    //#gasestReturn = GasEst.gasest(isolv, t, pt, pe0)
    //gasestReturn = GasEst.gasest(isolv, t, pt);
    double[] returnGasEst = GasEst.gasest(isolv, t, pt);
/* No!
    pe0 = GasEst.pe; 
    p0 = GasEst.p;
    neq = GasEst.neq; 
*/
// Unpack structure returned by GasEst - sigh!
   neq = (int) returnGasEst[0];
   pe0 = returnGasEst[1];
   for (int k99 = 2; k99 < 42; k99++){
      p0[k99-2] = returnGasEst[k99];
   }
 

//#print("GsCalc: pe0 ", pe0, " p0 ", p0, " neq ", neq)

    //#print("Before gas pe0 ", pe0)
    //gasReturn = Gas.gas(isolv, t, pt, pe0, p0, neq, tol, maxit, outFile)
    double[] returnGas = Gas.gas(isolv, t, pt, pe0, p0, neq, tol, maxit, path);

/* No!
    pe = Gas.pe; 
    pp = Gas.pp; 
    gmu = Gas.gmu; 
    rho = Gas.rho; 
*/
   pe = returnGas[0];
   rho = returnGas[1];
   gmu = returnGas[2];
   for (int k99 = 3; k99 < 153; k99++){
      pp[k99-3] = returnGas[k99];
   }
    

//#print("GsCalc: rho ", rho)
//#print("GsCalc: gmu ", gmu)

    rholog= Math.log10(rho);
    //fd= -99.0e0
    //if(pd/pt > 0.0e0):
    //    fd = Math.log10(pd/pt)
    fe= -99.0e0;
    if(pe/pt > 0.0e0){ 
        fe = Math.log10(pe/pt);
    }

    for (int n99 = 0; n99 < nspec; n99++){
        fp[n99]= -99.0e0;
        if (pp[n99]/pt > 0.0e0){
            fp[n99]= Math.log10( pp[n99]/pt);
        }
    }

    //#c      write(7,200) t,rholog,gmu,fd,fe,(fp(k),k=1,nspec)
        
    //#print("t ", t, " pt ", pt, " rholog ", rholog, " gmu ", gmu, " fd ", fd, " fe ",fe)

    //#print("pp, fp:")
    //#for k in range(nspec):
    //#    print("k ", k, pp[k], fp[k])
    //#  200 format(1x,f10.2,160f10.5)
    //outFile.write(outStringHead)
//try{
//     Files.write( path2, outStringHead.getBytes(),
//                     StandardOpenOption.APPEND);
//   } catch (Exception e) {
//     System.out.println("path2: Caught exception");
//   }
    outString = String.format("%11.2f %10.5f %10.5f %10.5f\n",
                 t, rholog, gmu, fe);
    //outFile.write(outString)
    //outFile2.write(outString)
try{
     Files.write( path, outString.getBytes(),
                     StandardOpenOption.APPEND);
     Files.write( path2, outString.getBytes(),
                     StandardOpenOption.APPEND);
   } catch (Exception e) {
     System.out.println("path, path2: Caught exception");
   }
    
    //outString = ""    
    for (int j99 = 0; j99 < nspec; j99++){
        outString = outString + " " + Double.toString(fp[j99]);
    }
    outString+="\n";
    //outFile.write(outString)
    //outFile2.write(outString)
try{
     Files.write( path, outString.getBytes(),
                     StandardOpenOption.APPEND);
     Files.write( path2, outString.getBytes(),
                     StandardOpenOption.APPEND);
   } catch (Exception e) {
     System.out.println("path, path2: Caught exception");
   }

} //k loop

//#sys.stdout = original
//outFile.close()
//outFile2.close()


   } //end main method

} //end public class GsCalc

