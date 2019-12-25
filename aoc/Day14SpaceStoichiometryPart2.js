/*
--- Day 14: Space Stoichiometry ---
As you approach the rings of Saturn, your ship's low fuel indicator turns on. There isn't any fuel here, but the rings have plenty of raw material. Perhaps your ship's Inter-Stellar Refinery Union brand nanofactory can turn these raw materials into fuel.

You ask the nanofactory to produce a list of the reactions it can perform that are relevant to this process (your puzzle input). Every reaction turns some quantities of specific input chemicals into some quantity of an output chemical. Almost every chemical is produced by exactly one reaction; the only exception, ORE, is the raw material input to the entire process and is not produced by a reaction.

You just need to know how much ORE you'll need to collect before you can produce one unit of FUEL.

Each reaction gives specific quantities for its inputs and output; reactions cannot be partially run, so only whole integer multiples of these quantities can be used. (It's okay to have leftover chemicals when you're done, though.) For example, the reaction 1 A, 2 B, 3 C => 2 D means that exactly 2 units of chemical D can be produced by consuming exactly 1 A, 2 B and 3 C. You can run the full reaction as many times as necessary; for example, you could produce 10 D by consuming 5 A, 10 B, and 15 C.

Suppose your nanofactory produces the following list of reactions:

10 ORE => 10 A
1 ORE => 1 B
7 A, 1 B => 1 C
7 A, 1 C => 1 D
7 A, 1 D => 1 E
7 A, 1 E => 1 FUEL
The first two reactions use only ORE as inputs; they indicate that you can produce as much of chemical A as you want (in increments of 10 units, each 10 costing 10 ORE) and as much of chemical B as you want (each costing 1 ORE). To produce 1 FUEL, a total of 31 ORE is required: 1 ORE to produce 1 B, then 30 more ORE to produce the 7 + 7 + 7 + 7 = 28 A (with 2 extra A wasted) required in the reactions to convert the B into C, C into D, D into E, and finally E into FUEL. (30 A is produced because its reaction requires that it is created in increments of 10.)

Or, suppose you have the following list of reactions:

9 ORE => 2 A
8 ORE => 3 B
7 ORE => 5 C
3 A, 4 B => 1 AB
5 B, 7 C => 1 BC
4 C, 1 A => 1 CA
2 AB, 3 BC, 4 CA => 1 FUEL
The above list of reactions requires 165 ORE to produce 1 FUEL:

Consume 45 ORE to produce 10 A.
Consume 64 ORE to produce 24 B.
Consume 56 ORE to produce 40 C.
Consume 6 A, 8 B to produce 2 AB.
Consume 15 B, 21 C to produce 3 BC.
Consume 16 C, 4 A to produce 4 CA.
Consume 2 AB, 3 BC, 4 CA to produce 1 FUEL.
Here are some larger examples:

13312 ORE for 1 FUEL:

157 ORE => 5 NZVS
165 ORE => 6 DCFZ
44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
179 ORE => 7 PSHF
177 ORE => 5 HKGWZ
7 DCFZ, 7 PSHF => 2 XJWVT
165 ORE => 2 GPVTF
3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT
180697 ORE for 1 FUEL:

2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG
17 NVRVD, 3 JNWZP => 8 VPVL
53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL
22 VJHF, 37 MNCFX => 5 FWMGM
139 ORE => 4 NVRVD
144 ORE => 7 JNWZP
5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC
5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV
145 ORE => 6 MNCFX
1 NVRVD => 8 CXFTF
1 VJHF, 6 MNCFX => 4 RFSQX
176 ORE => 6 VJHF
2210736 ORE for 1 FUEL:

171 ORE => 8 CNZTR
7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
114 ORE => 4 BHXH
14 VRPVC => 6 BMBT
6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
5 BMBT => 4 WPTQ
189 ORE => 9 KTJDG
1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
12 VRPVC, 27 CNZTR => 2 XDBXC
15 KTJDG, 12 BHXH => 5 XCVML
3 BHXH, 2 VRPVC => 7 MZWV
121 ORE => 7 VRPVC
7 XCVML => 6 RJRHP
5 BHXH, 4 VRPVC => 5 LTCX
Given the list of reactions in your puzzle input, what is the minimum amount of ORE required to produce exactly 1 FUEL?

Your puzzle answer was 1920219.


--- Part Two ---
After collecting ORE for a while, you check your cargo hold: 1 trillion (1000000000000) units of ORE.

With that much ore, given the examples above:

The 13312 ORE-per-FUEL example could produce 82892753 FUEL.
The 180697 ORE-per-FUEL example could produce 5586022 FUEL.
The 2210736 ORE-per-FUEL example could produce 460664 FUEL.
Given 1 trillion ORE, what is the maximum amount of FUEL you can produce?
*/
let input =
  "3 NPNGZ, 3 TBFQ, 1 RZBF => 2 LQNR:1 GWZRW => 9 CHNX:1 DBVD, 10 VCHK, 12 WNHV, 1 FMKT, 1 DKFT, 1 BTLR, 12 VHKXD => 4 ZMWC:2 RZBF, 13 JSBVZ => 3 JLVRS:15 BZNB, 1 JSBVZ => 5 ZTCM:28 GMSF, 18 LTGJ => 9 BTLR:1 RZDM, 3 BNJD, 1 FLXL => 1 FDBX:2 BZNB, 1 JLVRS => 2 GMSF:1 FDBX, 2 ZSQR, 1 XMBS, 2 FMKT, 1 BNJD, 12 TRXVN => 7 CRNMW:1 PCHWB, 6 LXJPK, 2 ZSQR => 5 DBVD:5 LMTM, 9 RZBF => 7 TBFQ:2 KVWJG => 2 RZBF:1 LBFXF, 17 NRBGS => 6 JSBVZ:1 VQTFW => 5 LMTM:20 DBVD => 2 SRFQ:3 XSVZ, 7 JSBVZ, 5 NPNGZ => 2 VWMQZ:1 ZQDN, 1 RZBF, 1 NDNKB => 9 FMKT:7 BZVGH, 9 NDNKB => 6 LTGJ:3 VWMQZ, 1 XSVZ, 4 GKDGX => 1 TRXVN:3 VXFJM, 14 FMKT => 6 NFTJ:21 WXWHD => 1 VCHK:1 TJZVQ => 6 NDNKB:6 NFTJ => 3 RZDM:1 VHKXD, 1 TBPWN, 1 FDBX, 2 XMBS, 2 WJTRC, 20 BTLR => 8 VSBV:19 XSVZ, 7 LJQG, 10 ZTCM => 1 GKDGX:3 NPNGZ, 2 RZBF, 8 GWZRW => 1 QVDFQ:1 TBFQ => 7 VHKXD:3 LTGJ, 1 ZXWB, 2 MNPBV => 2 BNJD:9 LQNR, 2 QVDFQ, 10 GMSF => 4 XSVZ:13 VHKXD, 17 CHNX, 1 NDNKB => 8 VXFJM:122 ORE => 4 LBFXF:18 NPNGZ => 5 LXJPK:1 TJZVQ, 1 FXGH => 5 GWZRW:9 BZNB, 4 JLVRS => 3 KDCG:1 SNLNK => 8 WNHV:4 VHKXD => 4 DGFN:1 RZDM => 6 SNLNK:3 CHNX, 8 LTGJ => 4 TBPWN:2 DGFN, 1 NFTJ => 2 RNSXD:1 FXGH, 1 BDCLW => 2 LJQG:3 DGCMV, 2 BZVGH, 7 RZBF => 8 MNPBV:14 WXWHD => 2 XMBS:1 BZVGH => 8 FLXL:8 VXFJM, 1 NFTJ => 2 WXWHD:2 LXJPK => 9 ZSQR:5 NRBGS, 1 LBFXF => 9 FXGH:4 NRBGS, 27 CHNX => 9 PCHWB:3 LBFXF => 4 TJZVQ:185 ORE => 2 VQTFW:1 RTVFM, 1 TBPWN => 6 DGCMV:10 NRBGS => 3 ZQDN:5 JSBVZ, 8 FXGH, 1 TJZVQ => 7 NPNGZ:1 PCHWB, 6 LXJPK, 5 LTGJ => 2 ZXWB:1 NPNGZ, 24 FMKT => 6 WJTRC:4 KDCG, 33 BZNB => 3 KGNH:2 KGNH, 4 ZTCM, 15 CHNX => 6 BDCLW:10 LXJPK, 18 TJZVQ, 1 FXGH => 7 BZVGH:27 DBVD, 2 CRNMW, 8 ZTCM, 8 RNSXD, 14 VSBV, 6 ZMWC, 37 TBPWN, 53 SRFQ => 1 FUEL:19 FXGH, 4 TJZVQ => 3 BZNB:17 QVDFQ, 26 KDCG, 7 CHNX => 3 RTVFM:111 ORE => 6 KVWJG:3 ZTCM => 6 DKFT:124 ORE => 6 NRBGS";
// let input = '157 ORE => 5 NZVS:165 ORE => 6 DCFZ:44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL:12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ:179 ORE => 7 PSHF:177 ORE => 5 HKGWZ:7 DCFZ, 7 PSHF => 2 XJWVT:165 ORE => 2 GPVTF:3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT';  
let reactions = input.split(":");
let reactionsObj = {};

for (reaction of reactions) {
  let splitedReactions = reaction.split("=>");
  let reactionInputs = splitedReactions[0].trim().split(",");
  let reactionOutput = splitedReactions[1].trim();

  let chemicalObj = {
    count: getProducedChecmicalCount(reactionOutput),
    reactionInputs: {}
  };

  for (inputChemical of reactionInputs) {
    inputChemical = inputChemical.trim();
    const inputChemicalName = getChecmicalName(inputChemical);
    const producedChecmicalCount = getProducedChecmicalCount(inputChemical);
    chemicalObj["reactionInputs"][inputChemicalName] = {
      count: producedChecmicalCount
    };
  }
  reactionsObj[getChecmicalName(reactionOutput)] = chemicalObj;
}


// let currentORE = 1;
// let totalORES = 1000000000000;
// let fuelCount = 1;
// while(currentORE <= totalORES) {
//   currentORE = runReaction("FUEL", fuelCount);
//   fuelCount++;
// }
// console.log('fuelCount--', fuelCount);

let outputMap = {};
let startIndex = 1;
let endIndex = 1;
let temp = runReaction("FUEL", 1);
while (temp < 1000000000000) {
  temp = runReaction("FUEL", endIndex);
  // startIndex = startIndex * 10;
  endIndex = endIndex * 10;
}
startIndex = startIndex / 10; 
while (startIndex !== endIndex) {
  let mid = Math.floor(startIndex + (endIndex - startIndex) / 2);
  temp = runReaction("FUEL", mid);
  if (temp < 1000000000000 ) {
    startIndex = mid;
  } else {
    endIndex = mid - 1;
  }
}

console.log('fuelCount--', startIndex);

function runReaction(reactionKey, coefficient) {
  let sum = 0;
  const reaction = reactionsObj[reactionKey];
  const product = Math.ceil(coefficient / reaction.count);
  let reactionInputs = reaction.reactionInputs;
  const reactionInputKeys = Object.keys(reactionInputs);

  reactionInputKeys.map((key)=> {
    if (key === "ORE") {
      sum += product * reactionInputs[key].count;
    } else {
      if (!outputMap[key]) {
        outputMap[key] = 0;
      }
      if (outputMap[key] < product * reactionInputs[key].count) {
        sum += runReaction(key, product * reactionInputs[key].count - outputMap[key]);
      }
      outputMap[key] -= product * reactionInputs[key].count;
    }
  });


  outputMap[reactionKey] += product * reaction.count;

  return sum;
}

function getChecmicalName(checmical) {
  return checmical.split(" ")[1];
}

function getProducedChecmicalCount(checmical) {
  return parseInt(checmical.split(" ")[0]);
}
