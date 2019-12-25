const fs = require("fs");
const input = fs.readFileSync("Day22Inputpart2.txt", "utf-8");

const INSTRUCTION_TYPE_NEW_DECK = 0;
const INSTRUCTION_TYPE_POSITIVE_CUT = 1;
const INSTRUCTION_TYPE_NEGATIVE_CUT = 2;
const INSTRUCTION_TYPE_INCREAMENT = 4;

const deckOfCards = 10007;

const instructions = processInput(input);
const cards = generateCards(deckOfCards);
// console.log('cards---', JSON.stringify(cards));

let processedCards = cards;
let isMatched = false;
let processedCount = 0;
while (processedCount < 10000) {
  processedCards = processInstructions(instructions, JSON.parse(JSON.stringify(processedCards)));
  processedCount++;
  // console.log(JSON.stringify(processedCards));
  // console.log(processedCards[2020]);
  fs.appendFileSync('message.txt', `${processedCards[2020]} \n`);
}


// const positionOf2019thCard = processedCards.findIndex(function(cardValue) {
//   return cardValue == 2019;
// });

// console.log(
//   "After shuffling your factory order deck of 10007 cards, what is the position of card 2019?",
//   positionOf2019thCard
// );

function processInstructions(instructions, cards) {
  for (const instruction of instructions) {
    switch (instruction.type) {
      case INSTRUCTION_TYPE_NEW_DECK:
        cards = performNewDeck(cards);
        break;
      case INSTRUCTION_TYPE_POSITIVE_CUT:
        cards = performPositiveCut(cards, instruction.data);
        break;
      case INSTRUCTION_TYPE_NEGATIVE_CUT:
        cards = performNegativeCut(cards, instruction.data);
        break;
      case INSTRUCTION_TYPE_INCREAMENT:
        cards = performIncreament(cards, instruction.data);
        break;
    }
  }
  return cards;
}

function performNewDeck(cards) {
  return cards.reverse();
}

function performPositiveCut(cards, data) {
  const cut = cards.splice(0, data);
  return cards.concat(cut);
}

function performNegativeCut(cards, data) {
  const cut = cards.splice(cards.length - data, cards.length);
  return cut.concat(cards);
}
function performIncreament(cards, data) {
  const newCards = [];
  newCards[cards.length - 1] = undefined;
  let currentPosition = 0;
  while (cards.length) {
    newCards[currentPosition] = cards.shift();
    for (
      let increamentFactor = 1;
      increamentFactor <= data;
      increamentFactor++
    ) {
      if (currentPosition + 1 == newCards.length) {
        currentPosition = 0;
      } else {
        currentPosition++;
      }
    }
  }
  return newCards;
}

function generateCards(deckOfCards) {
  const cards = [];
  for (let c = 0; c < deckOfCards; c++) {
    cards.push(c);
  }
  return cards;
}

function processInput(input) {
  const instructionsInput = input.split("\n");
  const instructions = [];
  for (let instructionString of instructionsInput) {
    const instruction = {};
    if (instructionString.includes("deal into new stack")) {
      instruction.type = INSTRUCTION_TYPE_NEW_DECK;
    } else if (instructionString.includes("deal with increment ")) {
      instruction.type = INSTRUCTION_TYPE_INCREAMENT;
      instruction.data = parseInt(
        instructionString.replace("deal with increment ", "")
      );
    } else if (instructionString.includes("cut ")) {
      let data = parseInt(instructionString.replace("cut ", ""));
      if (data < 0) {
        instruction.type = INSTRUCTION_TYPE_NEGATIVE_CUT;
      } else {
        instruction.type = INSTRUCTION_TYPE_POSITIVE_CUT;
      }
      instruction.data = Math.abs(data);
    }
    instructions.push(instruction);
  }

  return instructions;
}
