/*
--- Day 24: Planet of Discord ---
You land on Eris, your last stop before reaching Santa. As soon as you do, your sensors start picking up strange life forms moving around: Eris is infested with bugs! With an over 24-hour roundtrip for messages between you and Earth, you'll have to deal with this problem on your own.

Eris isn't a very large place; a scan of the entire area fits into a 5x5 grid (your puzzle input). The scan shows bugs (#) and empty spaces (.).

Each minute, The bugs live and die based on the number of bugs in the four adjacent tiles:

A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
Otherwise, a bug or empty space remains the same. (Tiles on the edges of the grid have fewer than four adjacent tiles; the missing tiles count as empty space.) This process happens in every location simultaneously; that is, within the same minute, the number of adjacent bugs is counted for every tile first, and then the tiles are updated.

Here are the first few minutes of an example scenario:

Initial state:
....#
#..#.
#..##
..#..
#....

After 1 minute:
#..#.
####.
###.#
##.##
.##..

After 2 minutes:
#####
....#
....#
...#.
#.###

After 3 minutes:
#....
####.
...##
#.##.
.##.#

After 4 minutes:
####.
....#
##..#
.....
##...
To understand the nature of the bugs, watch for the first time a layout of bugs and empty spaces matches any previous layout. In the example above, the first layout to appear twice is:

.....
.....
.....
#....
.#...
To calculate the biodiversity rating for this layout, consider each tile left-to-right in the top row, then left-to-right in the second row, and so on. Each of these tiles is worth biodiversity points equal to increasing powers of two: 1, 2, 4, 8, 16, 32, and so on. Add up the biodiversity points for tiles with bugs; in this example, the 16th tile (32768 points) and 22nd tile (2097152 points) have bugs, a total biodiversity rating of 2129920.

What is the biodiversity rating for the first layout that appears twice?

Your puzzle answer was 27777901.
*/
const fs = require("fs");
const input = fs.readFileSync("Day24Input.txt", "utf-8");

const TYPE_BUG = '#';
const TYPE_EMPTY = '.';

const erisArea = parseInput(input);

const width = erisArea[0].length;
const height = erisArea.length;

const generatedLayouts = [];
generatedLayouts.push(erisArea.join(''));

let outputArea = JSON.parse(JSON.stringify(erisArea)); 
const firstTwiceLayout = getFirstTwiceLayout(outputArea);
const bioDiversityBugPoints = calculateBioDiversity(firstTwiceLayout);
console.log('bioDiversityBugPoints--', bioDiversityBugPoints);

function calculateBioDiversity(layout) {
  let bioDiversityBugPoints = 0;
  for (let i = 0; i< layout.length; i++) {
    if (layout[i] === TYPE_BUG) {
      bioDiversityBugPoints += 2 ** i;
    }
  }
  return bioDiversityBugPoints;
}

function getFirstTwiceLayout(outputArea) {
  while(true) {
    outputArea = generateLayout(outputArea);
    let genertedLayout = outputArea.join('');
    generatedLayouts.push(genertedLayout);
    if (checkIfGeneratedLayoutComesTwise(generatedLayouts, genertedLayout)) {
      console.log('firstTwiceLayouttedLayout--', genertedLayout);
      return genertedLayout;
    }
  }
}

function checkIfGeneratedLayoutComesTwise(generatedLayouts, layout) {
  let count = 0;
  for (let generatedLayout of generatedLayouts) {
    if (layout === generatedLayout) {
      count++;
    }
    if (count == 2) {
      return true;
    }
  }
  return false;
}




function generateLayout (erisArea) {
  let outputArea = [];
  for (let y = 0; y < height; y++) {
    let pattern = "";
    for (let x = 0; x < width; x++) {
      const currentArea = erisArea[y][x];
      const { bugsCount, emptySpaceCount} = getAdjacentAreaBugsAndEmptySpaces(x, y, erisArea);
      if (currentArea === TYPE_EMPTY) {
        if (bugsCount === 1 || bugsCount === 2) {
          pattern = pattern + TYPE_BUG;
        } else {
          pattern = pattern + TYPE_EMPTY;
        }
      } else if (currentArea === TYPE_BUG) {
        if (bugsCount === 1) {
          pattern = pattern + TYPE_BUG;
        } else {
          pattern = pattern + TYPE_EMPTY;
        }
      }
    }
    outputArea.push(pattern);
  }
  return outputArea;
}

function getAdjacentAreaBugsAndEmptySpaces(x, y, erisArea) {
  const leftPoint = erisArea[y][x-1];
  const rightPoint = erisArea[y][x+1];
  const topPoint = erisArea[y-1] ? erisArea[y-1][x] : undefined;
  const bottomPoint = erisArea[y+1] ? erisArea[y+1][x] : undefined;
  let bugsCount = 0;
  let emptySpaceCount = 0;
  if (leftPoint == TYPE_BUG) {
    bugsCount++;
  } else {
    emptySpaceCount++;
  }

  if (rightPoint == TYPE_BUG) {
    bugsCount++;
  } else {
    emptySpaceCount++;
  }

  if (topPoint == TYPE_BUG) {
    bugsCount++;
  } else {
    emptySpaceCount++;
  }

  if (bottomPoint == TYPE_BUG) {
    bugsCount++;
  } else {
    emptySpaceCount++;
  }

  return {
    bugsCount,
    emptySpaceCount
  };

}

function parseInput(input) {
  const inputArea = input.split("\n");
  return inputArea;
}
