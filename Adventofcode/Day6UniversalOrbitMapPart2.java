import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/*--- Part Two ---
Now, you just need to figure out how many orbital transfers you (YOU) need to take to get to Santa (SAN).

You start at the object YOU are orbiting; your destination is the object SAN is orbiting. An orbital transfer lets you move from any object to an object orbiting or orbited by that object.

For example, suppose you have the following map:

COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)SAN
Visually, the above map of orbits looks like this:

                          YOU
                         /
        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I - SAN
In this example, YOU are in orbit around K, and SAN is in orbit around I. To move from K to I, a minimum of 4 orbital transfers are required:

K to J
J to E
E to D
D to I
Afterward, the map of orbits looks like this:

        G - H       J - K - L
       /           /
COM - B - C - D - E - F
               \
                I - SAN
                 \
                  YOU
What is the minimum number of orbital transfers required to move from the object YOU are orbiting to the object SAN is orbiting? (Between the objects they are orbiting - not between YOU and SAN.)

Your puzzle answer was 286.
*/
public class Day6UniversalOrbitMapPart2 {

	public static void main(String args[]) throws Exception {
		Day6UniversalOrbitMapPart2 day6UniversalOrbitMapPart2 = new Day6UniversalOrbitMapPart2();
		String inputMap[] = { "KDZ)KYY", "4K8)LQM", "R6G)QHZ", "4JT)JVX", "PW1)HGF", "HL3)X5V", "X5V)PC8", "VRL)1XG",
				"6V8)T2J", "3W3)BZ5", "2XG)SXW", "YJ9)YPF", "VGB)DD4", "C9X)GKS", "Y54)WVW", "DF7)DST", "XPQ)B6D",
				"RNZ)PZ8", "3C9)Y94", "9PL)MB8", "QP4)7CT", "Y82)MJ6", "TCZ)2Z5", "ZDY)RDZ", "RGZ)XZY", "ZHK)SBB",
				"DJ8)XWC", "Q4G)H8X", "682)Y2C", "F7M)MV5", "1N8)BDF", "C5P)R1V", "M6V)S5R", "YNY)YLP", "3JL)KTM",
				"YLB)K6T", "FN2)G5S", "5C4)KHX", "FHM)RZG", "PP2)HFB", "M2M)D5S", "8D4)54Y", "6HG)SK9", "NDB)VVW",
				"YVJ)YGZ", "TQW)FHM", "CBZ)7JX", "J8T)JP4", "RVL)6NK", "P7M)4R4", "QF4)ZM4", "991)DBV", "LHG)JG3",
				"B3N)P2T", "GTT)167", "1PX)V8Q", "R3N)6PW", "2ZL)M8Z", "3ST)3C9", "GSF)H52", "TVY)879", "QZB)H8Z",
				"XDY)8F8", "2RH)YJ9", "6VF)M69", "ZL4)HWQ", "8LX)1F4", "2YN)K97", "Q1R)C78", "DV8)P27", "KLQ)4SS",
				"Q8H)XFR", "MVP)DLV", "HWD)2JB", "KXT)3DD", "ZGL)VRL", "M9M)4PT", "S2H)KLQ", "4R4)WGP", "P12)3D6",
				"KJ7)Z9T", "PPH)RPV", "S69)XXN", "KJL)96N", "1XR)ZL4", "S31)Y5W", "2K9)XSQ", "N6T)1PX", "RRD)Q2D",
				"YHJ)D97", "K5T)XZN", "5H7)2ZL", "CWS)9YL", "P3Y)4JD", "VP5)3XN", "DBV)SDP", "VBS)D2P", "FCM)Y42",
				"YFW)PHL", "H3T)PQ7", "TV1)6HG", "F4M)JZD", "B6L)HRS", "DMV)JQ8", "6PK)LH3", "M8Z)KLW", "8F4)6FX",
				"TH9)25M", "T69)4NJ", "M2F)LDX", "Y76)48X", "5XJ)ZQS", "GZX)HWD", "FWR)2KS", "6BC)55D", "NF7)YTH",
				"VMG)ZWX", "TQW)7MV", "WFL)WWJ", "B6G)VJL", "9F7)JJ9", "SSR)KWF", "RCQ)K51", "BXS)31G", "WTS)K5T",
				"XFS)RZJ", "P29)DFJ", "H5R)QX8", "BHR)B8N", "SCJ)MBX", "DYP)VT9", "3M7)LWT", "PY3)QN5", "YS6)G2R",
				"V27)8R9", "PHL)CVZ", "3BH)786", "Y76)YVJ", "HDR)RJV", "B24)QQB", "7B9)XJ8", "QQB)2YN", "52X)M45",
				"3ZG)BQ3", "542)7Y7", "9XL)FB1", "SVH)Q5P", "RDZ)4K8", "BCC)CSX", "3CY)TN4", "4ZW)583", "CFS)MPP",
				"W83)X7C", "49J)YPB", "RKC)GY6", "PDL)Y76", "1L8)SKG", "LDK)9X7", "YR5)KBT", "CYN)1Y6", "QW6)QF4",
				"SNT)MS2", "GQL)R7F", "SCP)957", "SXW)R4X", "NR5)B9X", "YPB)YYT", "9FV)991", "KCG)GWC", "NV5)XSW",
				"X4G)GTV", "ZF2)KS4", "JPV)BJ6", "SV9)MVP", "7X3)LGG", "4YY)8LX", "CMH)F5C", "44D)NJJ", "7Y7)W6C",
				"87V)Q7R", "M2L)3XT", "P4R)9FV", "HQ4)RLL", "6CD)KF4", "Z5J)W83", "4M6)M2F", "489)DGP", "R7F)1L3",
				"G4L)B16", "SYC)KJS", "9M9)P3Y", "V12)995", "FTH)NYB", "9JP)ZDY", "RSN)W1T", "Y3Z)C5J", "WYB)9WR",
				"MZV)BDV", "4W9)D8V", "WX4)SZ6", "2MH)46P", "6DR)RHJ", "QYQ)K9C", "NJT)5X2", "BDV)FHW", "S71)T2V",
				"KHX)X1Z", "MGV)RG2", "CPB)H29", "DB4)NWY", "JRZ)ZGL", "KLW)HL3", "B3T)FSK", "RH9)S4X", "X7C)SAN",
				"157)Y1L", "FVF)KRV", "GL9)NLV", "VM4)CBQ", "TGZ)CWS", "4JD)15T", "1TQ)QCF", "MPP)29X", "YGB)DB4",
				"PP2)RZM", "374)3FP", "575)S71", "PQQ)FG7", "344)DV8", "ZHK)GYD", "T4R)F9P", "S5R)NNL", "W2Y)FCM",
				"P3Y)489", "NS2)62T", "3R4)KCG", "MP2)KB4", "YSH)QPP", "SD2)2JT", "D48)4M6", "9KZ)FVF", "K9C)SD5",
				"X1Z)HDR", "GH7)5JM", "HJD)XPQ", "QH8)QWL", "3ZK)9R9", "7B5)4QR", "8C2)17B", "KPH)YS6", "NRL)T64",
				"LH3)BTP", "WNB)YLB", "G8X)4CW", "894)7PK", "JDC)SGR", "VNL)ZWY", "BBT)2K9", "P1T)YHJ", "2KS)S31",
				"HRF)PXR", "V9C)1PV", "DXF)8QB", "VWD)NTN", "F64)HBT", "2Z8)3R4", "V51)BXS", "T53)2MH", "FTR)VW5",
				"3DD)GYT", "P7Z)BSG", "GTT)JNX", "P2T)8YZ", "FG7)8YX", "1BT)N6T", "8Y3)MPV", "1BW)778", "7FG)CTN",
				"RD8)M7G", "XBH)PJD", "C78)PD6", "Q7R)R5K", "SKM)H9W", "LGF)B57", "NLV)6V8", "TWS)GRJ", "2FL)JN1",
				"N9C)QYZ", "YGJ)S9Y", "897)2R2", "PFW)5ZM", "SZ6)S2G", "7C5)PW3", "529)SCJ", "SK9)6TR", "75L)6DR",
				"X1Z)M2L", "33X)9V5", "HSG)HB8", "DD4)6R8", "H5T)HNR", "1PQ)J9Y", "DKH)KQS", "9WQ)257", "MHW)TH9",
				"B8N)QS4", "QTK)GV6", "14C)TJF", "TR4)V9B", "4JD)8D4", "9DH)9JL", "6N6)1BT", "P7T)FZZ", "RZM)X4G",
				"W93)GNH", "H95)3BY", "KGT)W2Y", "K6X)ZM1", "7B5)DHR", "WSK)K2C", "TWM)TGZ", "WJ6)9LG", "WTS)3PF",
				"X5Q)R5N", "6R3)SLB", "ZSM)PBS", "JHF)VSR", "QD9)D48", "D1N)YGB", "1WJ)5L7", "VDN)5H4", "F5C)344",
				"RLS)KSG", "PJF)6PT", "XQ8)DVW", "M3N)NYS", "TZ4)H3S", "GWC)M5W", "DDV)477", "RWL)431", "TNF)TRN",
				"Y7Q)RQG", "5HW)JFH", "NN3)W51", "P5V)MMW", "KXS)DJ8", "5HJ)374", "Z9T)RJJ", "ZWY)79J", "5ZM)DLT",
				"TJF)J4R", "7QJ)Y7Q", "NR5)XX8", "NWY)H87", "RJJ)C9X", "6ZT)MNH", "H3S)FNS", "2V3)P52", "9LG)53V",
				"7MN)187", "JSR)SNL", "VVW)GZX", "G4H)63K", "YXC)J4J", "3FP)FWR", "BZ5)MSR", "F9S)PPH", "3BY)QMS",
				"GNF)Z6V", "H8X)8ZH", "1Y6)11V", "5ZR)75L", "G2R)2HM", "BDF)C9F", "5RW)WS8", "NNL)STP", "XJ8)M3N",
				"HXN)TJW", "X2Y)9Z2", "GTV)TZ4", "PW3)VBS", "J4R)PS3", "8F2)3PJ", "CBQ)FTS", "RJJ)J8T", "DSC)2LH",
				"N75)JD7", "NYB)45P", "K2W)7B9", "2ZF)KXS", "F4H)RFX", "63K)J2W", "1B2)M6V", "54Y)7W6", "FB5)9V7",
				"QWL)G8X", "VT5)TQW", "6FX)DSC", "NNF)GFC", "VBS)CS8", "HSX)CXB", "2V3)JVV", "WXX)RT2", "LPN)ZLS",
				"GNH)767", "1GL)CJT", "PXR)HK8", "WY7)ZHK", "F9P)B6F", "BF2)NS2", "8F8)XQH", "Q2D)Z1Q", "3XT)X5Q",
				"3NP)YNY", "WS8)T53", "K88)ZJB", "DJ8)RSB", "W3R)FM5", "BBF)QY7", "2M6)P4R", "HK8)3M7", "9PL)9L4",
				"1QC)3ZG", "KQS)S69", "29X)3XF", "9WN)3WR", "6TR)JX6", "H52)9X3", "4WM)LZ8", "PBS)RVT", "5TN)THW",
				"337)QBT", "TT9)Z3D", "257)X5N", "762)3CY", "NP6)T1G", "87K)HVN", "5JM)3QM", "2R2)LHR", "MTD)33X",
				"P6J)WSK", "7HC)WX4", "9JZ)V9C", "QCF)NR5", "LVN)RCQ", "2GK)KGT", "RZJ)VDN", "FTL)DKH", "MZW)QW6",
				"K78)X8S", "CDF)WNK", "DLT)LSC", "265)HQ4", "JZF)FQS", "49R)7FG", "5XC)FDP", "WNK)JRB", "YW5)VTF",
				"9PV)CPB", "C4V)8Y3", "6NQ)GZL", "PGR)7QN", "COM)XX6", "7MV)JRZ", "66B)G8J", "LGG)BN1", "11V)DS8",
				"9X1)BPM", "M2L)BNH", "7PK)B24", "7Y7)F4H", "HK4)4YY", "FN2)K2V", "SDP)D3Y", "X9F)5LB", "ZQS)LWN",
				"WMP)N9F", "9F4)V27", "FDP)6BW", "Y94)WRM", "D9M)CFS", "69N)3V2", "TRN)DXF", "DM8)6RP", "M2D)8F4",
				"WL9)W3R", "26P)WCZ", "9BD)Y6Y", "WFZ)8KL", "JLY)NB7", "M5W)72S", "NJV)DDV", "9D9)KPH", "QK2)DTZ",
				"YC2)QLV", "R8P)ZV4", "T1B)GNF", "3XN)19S", "ZDY)R8W", "879)83T", "NW8)KBK", "LTG)B42", "SSM)LLY",
				"DST)YG6", "45P)VXL", "MPV)D94", "BG1)B73", "773)MDD", "BN1)J3X", "CTN)7M5", "SF7)7XX", "LLB)8HW",
				"J9Y)LRK", "T2J)DM8", "SSR)97S", "XY9)BHR", "RH9)SV9", "TJW)Z4M", "QLV)TRF", "KLW)4NC", "BST)VQC",
				"FQS)C4V", "RHJ)JR6", "6RP)TWM", "97S)H3F", "WCZ)3RK", "72B)PP1", "GZS)HNY", "P38)TT9", "MBB)68X",
				"HFH)Y5S", "JK5)GMH", "HLL)4WM", "JK5)MLP", "KQP)1PN", "2JT)LD6", "3XF)WFZ", "3ZM)529", "PQ7)G4K",
				"XNG)ZC6", "MB8)QV7", "VW5)SNT", "RDZ)QTK", "5K9)NGC", "N1Y)773", "QCJ)HJD", "8GC)N9Y", "W9R)7HC",
				"D2P)9F4", "D94)B3N", "268)QD9", "1F4)6CD", "9WQ)1CL", "VSR)DNY", "PJT)Y27", "CQW)S9G", "9YX)4F6",
				"R54)6ZT", "8YX)GY3", "K5M)TG4", "YF7)Q8T", "HTW)265", "FT4)1FQ", "2D2)79S", "BP1)DK8", "3PQ)5H7",
				"SLB)FSD", "4JW)GH7", "12K)9WW", "3PZ)T6K", "HGF)F64", "7C5)PV9", "46P)WX9", "V81)TCW", "K2N)RGZ",
				"J23)VZ5", "CRP)MZW", "B7V)1N8", "P6C)HXN", "CTV)VNL", "B1J)YC2", "LBW)NDB", "KJS)55S", "Y8S)DTK",
				"68T)F14", "DLV)6KL", "6PM)G4H", "4SS)LJ1", "WVW)768", "SXP)R81", "Y5W)5XC", "JM2)RRD", "43N)PGR",
				"2NV)SLY", "767)2JZ", "XQH)TV1", "542)PQQ", "JLW)NXC", "8RT)PHV", "NJJ)H1Z", "3VN)PFW", "N9F)XKF",
				"ZNV)LGS", "BSG)476", "LRK)NV5", "NXC)TCZ", "WC9)2CD", "XX8)XFS", "3BY)W3T", "D3K)9Z4", "K51)5DQ",
				"4V2)WRW", "7M5)JM2", "B73)VLS", "STS)3S5", "B6F)QZP", "J1M)9N6", "42X)87V", "8MJ)2RH", "KCD)PVP",
				"9WW)K6X", "878)RLS", "G57)RSN", "VQC)JSR", "R8K)9FR", "GYT)DN2", "49R)XY9", "5N4)682", "C4V)P38",
				"LZ8)8S4", "ZLL)ZNJ", "2YK)XNG", "Z6V)NPM", "MFZ)RBR", "LWT)5HJ", "CBB)MHW", "F37)YC1", "7XN)2D2",
				"DB8)8MM", "CSX)542", "1WW)BP1", "HRS)3SH", "1WR)JJ3", "DG1)SF7", "HB8)STS", "W6C)P5V", "XXN)F4M",
				"3PF)WL3", "Z3D)TNL", "GYG)YGJ", "ZNJ)FT4", "RSG)CRP", "DTK)KQ4", "SYS)Z3M", "ZM4)F34", "QPP)WH5",
				"GC5)J6D", "3S5)KCD", "MNH)446", "8Q1)178", "B6C)NN3", "VXL)JDC", "MLP)1WX", "Z9W)57G", "S1H)9BD",
				"S9G)B1L", "L7H)4V2", "K97)BCC", "879)D6P", "GM1)5K9", "45P)5C4", "8Y4)ND8", "431)JLW", "2LV)P29",
				"CS8)84Y", "BJ6)MTD", "25M)924", "B1L)762", "Q8T)6BC", "GZL)FN2", "KWF)RH9", "4CW)XHG", "WRW)KXT",
				"79J)LDK", "D5S)99R", "RVT)WMP", "894)FTL", "QV7)DF7", "6PW)SYC", "GFC)W34", "8ZH)ZKH", "99R)LPN",
				"C5F)5ZR", "PVP)WJ6", "XZN)2YK", "KBT)FXM", "H1H)2Z8", "C6Q)YSH", "THW)DZW", "B9X)QP4", "CHB)W3P",
				"72S)BVH", "QDK)R6G", "V9B)9Y1", "TL7)TVY", "TM5)ZSM", "2HM)RD8", "X55)HRF", "H9W)WFL", "QYQ)B1J",
				"PH2)6X1", "FMX)SPZ", "RW6)JMM", "5Z8)YT3", "LWT)R3N", "M2S)PJ2", "MMW)1NF", "1N1)QJ5", "CRF)XDY",
				"MS2)9XL", "DWV)91S", "MLM)GL9", "VMP)5PP", "JFJ)PXS", "JG2)6PM", "BN1)V51", "TNL)12K", "476)LVN",
				"DK8)VGB", "WVX)493", "8QB)9HM", "DZW)TL7", "WR1)26P", "8KG)SYS", "676)XKM", "SKM)HJV", "5KC)6N6",
				"PP7)GN7", "G2X)6R3", "1PN)JF1", "RCW)H95", "96L)XJ4", "C9F)1L8", "H8Z)K1G", "SGR)7C5", "XMD)BG1",
				"37Q)YH2", "9Y1)CMH", "PS3)1TQ", "Q7N)7N1", "7N1)WTS", "778)2PH", "S2G)8Q1", "GYD)5SJ", "P52)8SX",
				"CQK)ZLL", "FXM)Y54", "ZWX)FB5", "ZZR)JK5", "8F4)5RW", "X6W)6PK", "5DQ)X3J", "5PW)BR8", "D8N)GYG",
				"YC1)QDK", "XFR)Y38", "9FR)RZK", "M7G)X55", "HK4)66B", "PC8)BBT", "J2W)2FL", "4QR)SHK", "96N)Q7N",
				"7SG)JC4", "H9W)G2X", "D97)LBF", "DCC)9PV", "2CV)Q72", "TRF)G57", "S18)575", "XX6)WNB", "1CL)D9M",
				"53V)KQP", "HVY)P7Z", "JLZ)LR1", "QS4)7MW", "JFS)21D", "S1H)3ZM", "2PH)SP7", "LJ1)SKM", "N9Y)P9Z",
				"2JB)3ZK", "3QM)7QJ", "Z18)C1L", "XSQ)P7T", "FTS)3QR", "5L7)3W3", "P7D)9YX", "7MW)JDQ", "B57)TWS",
				"V96)H1H", "P27)ZHP", "K2V)ZBM", "RSG)TDR", "JD7)V7K", "RCC)MHX", "GKS)R54", "6QD)KDZ", "L88)YW5",
				"JFF)HN9", "BVH)K78", "ND8)NL5", "7FG)S8T", "KJF)F7M", "9WD)TNF", "JVX)BF2", "YVP)VRB", "BTP)WV3",
				"DFJ)LBW", "K8X)M14", "T2V)3PQ", "FM5)Y8S", "87K)96L", "KRV)RKC", "R5N)JFF", "B6D)V65", "6BB)NRL",
				"V7K)Q1R", "8GN)CYN", "6KL)F1G", "HVN)9JP", "187)Q8V", "HRF)P6C", "J2G)8HQ", "8HQ)RWL", "21D)2V3",
				"FJ6)3P6", "YBX)WC9", "HFB)6VF", "T3K)Q8H", "29Z)JFJ", "17B)GWJ", "PWM)R8K", "W2Y)9WQ", "G4K)DCC",
				"SD5)KJF", "KVX)53T", "W3T)XMD", "R6G)ZZG", "GWJ)8RT", "HNY)8SP", "R3H)KJL", "53T)NXN", "QN5)6BB",
				"LWN)GQL", "M3Q)7MN", "2JZ)1DW", "NVX)QM7", "DN2)LGJ", "9N6)9WD", "7XX)VWD", "R4P)29C", "NTN)GTT",
				"M9M)6VC", "8HW)FTR", "YH4)LGX", "995)CDF", "9V7)N9C", "3QR)JZF", "957)V96", "D8V)Y3Z", "GRJ)2WJ",
				"7BZ)QYQ", "ZSD)RCW", "DG1)5X8", "FSK)M5L", "WRB)4MR", "3R4)DYP", "178)H5T", "H3G)P66", "9YL)9S8",
				"KS4)PY3", "RVT)MP2", "JF1)Q4G", "S3G)9D9", "K2C)LHG", "31G)YOU", "RT2)JLZ", "VLS)QZB", "3SH)4JT",
				"8MM)CBB", "5LB)44D", "QW6)MFZ", "NQD)CRF", "ZNY)3H2", "3D6)WRB", "5X8)RNZ", "QYZ)Y5F", "LJ6)43N",
				"Y6Y)8GN", "8VB)FTH", "LGB)N75", "7N9)6QD", "2Z8)SVH", "YYT)14C", "X5N)9X1", "48X)9WN", "3LV)ZHW",
				"QMS)NW8", "LGX)Q26", "DF8)87K", "LLY)CBZ", "Y27)VMG", "CFR)H2S", "W93)PJF", "XKF)2ZF", "2FZ)ZZR",
				"KSG)VPQ", "D3Y)LYN", "HN9)V81", "NL5)ZF2", "JC4)V12", "G1L)XQ8", "446)DF8", "QZP)CHB", "1WX)WYB",
				"PMW)L44", "8F2)HTW", "ZHP)PYV", "NXN)H2P", "17S)QRF", "KQ4)GSF", "DVW)CTV", "WST)WWQ", "QRF)R1K",
				"62T)2RJ", "1XG)712", "DGB)138", "SBB)MGV", "1Y8)L15", "JP4)9JZ", "C1L)29Z", "MDD)WXX", "YLP)GLK",
				"QY7)M2M", "9Z4)VMP", "SL2)ZNY", "MBX)WVX", "RG7)BBF", "68X)LVJ", "PJ2)VP5", "FZZ)W89", "5H7)YVP",
				"QZ3)T1B", "5X2)897", "QM7)5TN", "8BD)T69", "23N)NQD", "RLL)4W9", "Y5S)RSG", "PD6)PP7", "H1Z)KHH",
				"WV3)J23", "SP7)1Y8", "WX9)RVL", "91S)ZNV", "LR1)NF7", "JLW)QK2", "ZLS)KVX", "TCW)HRH", "F37)RF9",
				"RGZ)1WR", "NB7)S2H", "167)23N", "Q26)5QR", "84Y)R8R", "9R9)S1H", "GV1)3BH", "LVJ)S3G", "MJ6)RW6",
				"9L4)YBX", "ZKH)8PJ", "J3X)1BW", "GMH)68T", "Z1Q)6GZ", "K9C)K2W", "Q72)9KR", "NL5)4ZW", "JDQ)2XK",
				"1Y6)KW4", "TM5)443", "2XK)PMW", "X3J)R4P", "RFX)X9F", "YGZ)1B2", "JN1)KJ7", "KY7)1SJ", "1CL)JHF",
				"4PT)8C2", "773)8BD", "M14)JPV", "LFZ)H5R", "QLV)K5M", "9HM)3JL", "1NF)YFW", "Y5F)M2D", "P4R)FJ6",
				"PQQ)KTH", "R4X)RCC", "WWJ)5Z8", "VWD)R3H", "DBJ)CDP", "Q5P)LGB", "JMM)R8P", "KB4)H3G", "CTN)8KG",
				"55S)XJ2", "R81)3PZ", "DHR)D8N", "2XK)Z18", "2RJ)3ST", "RSB)QCJ", "HNR)SCP", "YYT)9DH", "GN7)L7H",
				"ZDM)JLY", "29C)GV1", "FW6)49R", "ZV4)6NL", "V96)LGF", "KHH)SGJ", "2JT)8F2", "2MH)DL2", "PV9)6NQ",
				"M69)P12", "PYV)8GC", "1L3)FW6", "VTF)7XN", "KWL)BST", "HGH)5KC", "LGS)96G", "7GJ)PWM", "M45)WSV",
				"MV5)C6Q", "TDR)3NP", "TG4)TM5", "KTH)337", "T69)72B", "JJ3)B6G", "6VC)MBN", "HFB)F4C", "7W6)5PW",
				"FFJ)NNF", "F34)FMX", "JVV)GM1", "KYY)K2N", "R1V)P7D", "CDP)B7V", "3H2)4JW", "RPV)LLB", "NXC)52X",
				"MSR)894", "4PT)F37", "79S)2GK", "GY6)6R6", "2CD)37Q", "CJT)K88", "1XV)W6L", "BCC)K8X", "HFH)YF7",
				"J6D)DB8", "41P)QZ3", "7CT)PW1", "6NK)B3T", "S8T)3VN", "2LH)SD2", "C5J)GZS", "K6T)TR4", "1XH)3TL",
				"1FQ)2LV", "2W4)YXC", "1DW)HBY", "583)N4N", "QX8)2NV", "PQ7)DBJ", "ZWY)L88", "6R8)1QC", "CVZ)KWL",
				"YCD)J1M", "G5S)XBH", "5SJ)LJ6", "QPP)DWV", "F14)42X", "HBY)X2Y", "8SP)ZDM", "RCW)WR1", "9JL)CQW",
				"6BW)2W4", "HBT)JFS", "RJV)VWS", "XJ4)SSM", "477)2N8", "4CB)7X3", "KDZ)SSR", "VWS)9CL", "Y5F)F9S",
				"3V2)GTZ", "3PJ)9M9", "ZZG)7N9", "SGJ)41P", "DTZ)8MJ", "4NC)RTC", "PXS)HK4", "924)1NX", "R8W)HFH",
				"6R6)W9R", "WWQ)PH2", "MHX)NP6", "SLY)J2G", "8SX)7SG", "P9Z)RG7", "G8J)JVY", "5CD)M3Q", "Y38)FFJ",
				"YTH)KBB", "8YZ)157", "FSD)3LV", "9FV)878", "VPQ)C96", "P66)PD9", "Y1L)B6L", "W1T)2CV", "PHL)2MJ",
				"676)5CD", "LLB)LFZ", "Z3M)RND", "6X1)W93", "3QM)NVX", "H3F)3S1", "JZD)HSG", "KLQ)T3K", "786)SXP",
				"Z4M)NJV", "5H4)1WJ", "NPM)5HW", "VT9)Z9W", "9X7)DGB", "RQG)4CB", "CXB)M9M", "GV6)1XR", "768)JG2",
				"S4X)G1L", "QBT)HLL", "7M5)2XG", "W3P)59L", "5QR)B6C", "HWQ)1XH", "BNH)HGH", "2WJ)Z5J", "Q8V)P7M",
				"GY3)QH8", "LQM)SL2", "1NX)HP9", "XZY)G4L", "8PJ)HVY", "8R9)49J", "PZ8)PP2", "3P6)5TZ", "RF9)VT5",
				"QQB)GC5", "712)17S", "ZHW)YCD", "RM3)C5F", "4NJ)D3K", "2Z5)C5P", "ZBM)M2S", "PJD)PJT", "QJ8)X6W",
				"7XN)1GL", "YPF)MBB", "XKM)1XV", "DGP)ZSD", "RND)CFR", "S2H)5N4", "J4J)WST", "1GL)KY7", "6PT)D1N",
				"9Z2)9KZ", "HJV)HSX", "H3T)2M6", "H2P)DMV", "3BH)CQK", "KW4)LTG", "N6T)QJ8", "GV6)7B5", "J2G)WY7",
				"GZS)YH4", "VJL)N1Y", "W3P)NJT", "VV9)2FZ", "LWN)S18", "JQ8)MZV", "FHW)7BZ", "DNY)MLM", "B42)DG1",
				"1SJ)5XJ", "3WR)YR5", "19S)SR5", "MBN)1N1", "BNH)7GJ", "RG7)Y82", "BDF)PSF", "R8R)268", "SHK)69N",
				"WRM)8Y4", "XWC)WL9", "P1T)P6J", "NYS)8VB", "83T)RM3", "N4N)PDL", "9CL)S8K", "DS8)676", "WSV)P1T",
				"F4C)VM4", "T6K)1WW", "B16)9F7", "6GZ)HJZ", "LGJ)3LQ", "H2P)9PL", "RTC)T4R", "PSF)1PQ", "KWF)H3T",
				"SDP)VV9" };
		String inputMap1[] = {"COM)B","B)C","C)D","D)E","E)F","B)G","G)H","D)I","E)J","J)K","K)L","K)YOU","I)SAN"};
		String source = "YOU";
		String destination = "SAN";
		int orbitsCount = day6UniversalOrbitMapPart2.findMinumNumberOfOrbitsMove(inputMap, source, destination);
		System.out.println("What is the total number of direct and indirect orbits in your map data?---" + orbitsCount);

	}

	public int findMinumNumberOfOrbitsMove(String inputMap[], String source, String detination) {
		int minumNumberOfOrbitsMove = 0;
		Graph graph = createGraph(inputMap);
		Set<String> visitedSet = new LinkedHashSet<>();
		List<String> localPathList = new LinkedList<>();
		// DFSToSourceToDetination(graph, new Vertex(source), new Vertex(detination), visitedSet, "");
		DFSToSourceToDetination1(graph, new Vertex(source), new Vertex(detination), visitedSet, localPathList);
		return minumNumberOfOrbitsMove;
	}
	
	// To find a single path
	public void DFSToSourceToDetination1(Graph graph, Vertex source, Vertex destination, Set<String> visitedSet, List<String> localPathList) {
		visitedSet.add(source.orbitId); // label v as discovered
		if (source.orbitId.equalsIgnoreCase(destination.orbitId)) {
            // if match found then no need to traverse more till depth 
			System.out.println("Path List --- " + localPathList + "--size\n" + localPathList.size());
			visitedSet.remove(source.orbitId); 
			return;
		}
		for (Vertex w : graph.getAdjacentVertices(source.orbitId)) { //for all directed edges from v to w that are in G.adjacentEdges(v) do
			if (!visitedSet.contains(w.orbitId)) { // if vertex w is not labeled as discovered then
				localPathList.add(w.orbitId); // store current node in path[] 
				DFSToSourceToDetination1(graph, w, destination, visitedSet, localPathList); // recursively call DFS(G,w)
				localPathList.remove(w.orbitId);      // remove current node in path[] 
			}
		}
		visitedSet.remove(source.orbitId);
	}
	
	public void DFSToSourceToDetination(Graph graph, Vertex source, Vertex destination, Set<String> visitedSet, String path) {
        String newPath = path +  "->" + source.orbitId;
		visitedSet.add(source.orbitId); // label v as discovered
	
		for (Vertex w : graph.getAdjacentVertices(source.orbitId)) { //for all directed edges from v to w that are in G.adjacentEdges(v) do
			if (!visitedSet.contains(w.orbitId) && !w.orbitId.equalsIgnoreCase(destination.orbitId)) { // if vertex w is not labeled as discovered then
				DFSToSourceToDetination(graph, w, destination, visitedSet, newPath); // recursively call DFS(G,w)
			} else if (w.orbitId.equalsIgnoreCase(destination.orbitId)) {
                System.out.println(newPath + "->" + w.orbitId);
			}
		}
		visitedSet.remove(source.orbitId);
	}
	
//	1  procedure DFS(G,v):
//		2      label v as discovered
//		3      for all directed edges from v to w that are in G.adjacentEdges(v) do
//		4          if vertex w is not labeled as discovered then
//		5              recursively call DFS(G,w)
	

	public Graph createGraph(String inputMap[]) {
		Graph graph = new Graph();

		for (String map : inputMap) {
			final String[] orbits = map.split("\\)");
			String orbit1 = orbits[0];
			String orbit2 = orbits[1];
			graph.addVertices(orbit1);
			graph.addVertices(orbit2);
			graph.addEdges(orbit1, orbit2);
		}
		return graph;
	}

	static class Vertex {
		private String orbitId;

		Vertex(String orbitId) {
			this.orbitId = orbitId;
		}

		@Override
		public int hashCode() {
			return orbitId.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			Vertex vertex = (Vertex) obj;

			return this.orbitId.equalsIgnoreCase(vertex.orbitId.toString());
		}
	}

	static class Graph {
		private Map<Vertex, List<Vertex>> adjacentVerticesMap = new HashMap<Day6UniversalOrbitMapPart2.Vertex, List<Vertex>>();

		public void addVertices(String orbitId) {
			adjacentVerticesMap.putIfAbsent(new Vertex(orbitId), new ArrayList<>());
		}

		public void addEdges(String orbitId1, String orbitId2) {
			Vertex vertex1 = new Vertex(orbitId1);
			Vertex vertex2 = new Vertex(orbitId2);
			adjacentVerticesMap.get(vertex1).add(vertex2);
			adjacentVerticesMap.get(vertex2).add(vertex1);
		}
		
		public List<Vertex> getAdjacentVertices(String orbitId) {
			return adjacentVerticesMap.get(new Vertex(orbitId));
		}
		
		public Set<Vertex> getAllVertices() {
			return adjacentVerticesMap.keySet();
		}
		
//		public Vertex getVertexById(String orbitId) {
//			Vertex vertex = null;
//			for (Entry<Vertex, List<Vertex>> entryKeySet : adjacentVerticesMap.entrySet()) {
//				if (orbitId.equalsIgnoreCase(entryKeySet.getKey().orbitId)) {
//					vertex = entryKeySet.getKey();
//					break;
//				}
//			}
//			return vertex;
//		}
	}

}
