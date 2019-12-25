const {PriorityQueue} = require('./queue/index');

const fs = require('fs');
const input = fs.readFileSync('Day20DonutMazePart1Input.txt', 'utf-8');
const REGX_A_Z = /[A-Z]/;
const REGX_AZ_POST_DOT = /([A-Z][A-Z])\./g;   // Ex) AB. , AC.
const REGX_AZ_PRE_DOT = /\.([A-Z][A-Z])/g;   // Ex) .AB , .AC



const {donutMapArray, points, gateways} = processInput(input);
// console.log(donutMapArray);
// console.log(points);
// console.log(gateways);

const sourcePoint = [...points.AA[0], 0];
const destinationPoint = [...points.ZZ[0], 0];

let steps = findSteps(sourcePoint, destinationPoint);
console.log('--steps taken', steps);

function findSteps(sourcePoint, destinationPoint) {
  const parents = new Map();
  const graphScores = new Map();
  graphScores.set(getKey(sourcePoint), 0);

  const keyDestinationPoint = getKey(destinationPoint);

  const queue = new PriorityQueue();
  queue.enqueue(sourcePoint, getDefaultScore());
  while (!queue.isEmpty()) {
    const currentPoint = queue.dequeue().element;
    const keyCurrentPoint = getKey(currentPoint);
    if (keyCurrentPoint === keyDestinationPoint) {
      // return result
      const finalPath = getPath(parents, currentPoint);
      // console.log('finalPath--', finalPath);
      return finalPath.cost;
    }

    for (let adjacentPoint of getAdujacentPoints(currentPoint)) {
      const keyAdjacentPoint = getKey(adjacentPoint);
      const graphScore = graphScores.get(keyCurrentPoint) + getDefaultCost(currentPoint, adjacentPoint);
      // Calculate scores of each points
      if (!graphScores.has(keyAdjacentPoint) || graphScores.get(keyAdjacentPoint) > graphScore) {
        parents.set(keyAdjacentPoint, currentPoint);
        graphScores.set(keyAdjacentPoint, graphScore);
        queue.enqueue(adjacentPoint, graphScore); // Add each adjacent points to queue
      }
    }
  }
}


function getPath(parents, currentPoint) {
  const path = [currentPoint]; // Destination point
  let totalCost = 0;
  while (parents.has(getKey(currentPoint))) { // Find parent of destination point
    const parent = parents.get(getKey(currentPoint));
    totalCost += getDefaultCost(parent, currentPoint);
    path.unshift(parent);
    currentPoint = parent;  // Set the parent oint to current point, iterate utill it reaches root point
  }
  return { path, cost: totalCost };
}


function processInput(input) {
  const donutMapArray = input.split('\n');
  const points = {}; // Key is like AB, AZ, AY, Values like [[1,0]], [[1,0], [2,0]]
  let width = donutMapArray[0].length;

  for (let x = 0; x < width; x++) {
    let y = 2; // To read top row
    if (REGX_A_Z.test(donutMapArray[y-2][x])) {
      const key = donutMapArray[y-2][x] + donutMapArray[y-1][x];
      points[key] = points[key] || [];
      points[key].push([x, y]);
    }
  }

  for (let x = 0; x < width; x++) {
    let y = 106; // To read bottom row
    if (REGX_A_Z.test(donutMapArray[y+1][x])) {
      const key = donutMapArray[y+1][x] + donutMapArray[y+2][x];
      points[key] = points[key] || [];
      points[key].push([x, y]);
    }
  }

  for (let x = 0; x < width; x++) {
    let y = 28; // To read top mid row
    if (REGX_A_Z.test(donutMapArray[y+1][x])) {
      const key = donutMapArray[y+1][x] + donutMapArray[y+2][x];
      points[key] = points[key] || [];
      points[key].push([x, y]);
    }
  }

  for (let x = 0; x < width; x++) {
    let y = 80; // To read bottom mid row
    if (REGX_A_Z.test(donutMapArray[y-2][x])) {
      const key = donutMapArray[y-2][x] + donutMapArray[y-1][x];
      points[key] = points[key] || [];
      points[key].push([x, y]);
    }
  }

  for (let y = 0; y < donutMapArray.length; ++y) { // To iterate entire row string
    let match;
    while (match = REGX_AZ_POST_DOT.exec(donutMapArray[y])) {  // /([A-Z][A-Z])\./g.exec('AB.....##....#.#')
      const key = match[1];  //(2) ["AB.", "AB", index: 0, input: "AB.....##....#.#", groups: undefined]
      const x = match.index + 2;
      points[key] = points[key] || [];
      points[key].push([x, y]);
    }
    while (match = REGX_AZ_PRE_DOT.exec(donutMapArray[y])) { // /\.([A-Z][A-Z])/g.exec('.....##....#.#.AB')
      const key = match[1];  //(2) [".AB", "AB", index: 14, input: ".....##....#.#.AB", groups: undefined]
      const x = match.index;
      points[key] = points[key] || [];
      points[key].push([x, y]);
    }
  }

  const gateways = {};
  for (let pointValues of Object.values(points)) {
    if (pointValues.length !==2) { //Ex) {XV: [ [ 33, 2 ], [ 37, 80 ] ]}
      continue;
    }

     // If gate-1 point is outer gateway then add -1 to the gate2 
    // let [gate1, gate2] = pointValues;
    // if (gate1[0] === 2 || gate1[1] === 2  || gate1[0] === 110 || gate1[1] === 110) { 
    //   gateways[gate1.join(',')] = [...gate2, -1];
    //   gateways[gate2.join(',')] = [...gate1, 1];
    // } else {
    //   gateways[gate1.join(',')] = [...gate2, 1];
    //   gateways[gate2.join(',')] = [...gate1, -1];
    // }

    let [gate1, gate2] = pointValues;
		if (gate1[0] === 2 || gate1[1] === 2 || gate1[0] === 110 || gate1[1] === 110) {
			gateways[gate1.join(',')] = [...gate2, -1];
			gateways[gate2.join(',')] = [...gate1, 1];
		} else {
			gateways[gate1.join(',')] = [...gate2, 1];
			gateways[gate2.join(',')] = [...gate1, -1];
		}
  }

  return {donutMapArray, points, gateways};
}

function getKey(xyArray) {
  return xyArray.join(',');
}

function getDefaultCost() {
  return 1;
}

function getDefaultScore() {
  return 0;
}

function getAdujacentPoints([x,y, z]) {
  const result = [];
  if (donutMapArray[y][x-1] === '.') { // Left point
    result.push([x-1, y, z]);
  }
  if (donutMapArray[y][x+1] === '.') { // Right point
    result.push([x+1, y, z]);
  }
  if (donutMapArray[y-1][x] === '.') { // Top point
    result.push([x, y-1, z]);
  }
  if (donutMapArray[y+1][x] === '.') { // Bottom point
    result.push([x, y+1, z]);
  }
  if (gateways[`${x},${y}`]) {
      const [gx, gy, gz] = gateways[`${x},${y}`];
				if (z + gz >= 0) {
					result.push([gx, gy, z + gz]);
				}
  }
  return result;
}


/*let priorityQueue = new PriorityQueue();
priorityQueue.enqueue(5, 2); // data, priority
priorityQueue.enqueue(1, 6);

priorityQueue.enqueue(1, 9);

priorityQueue.enqueue(2, 2);

priorityQueue.enqueue(9, 10);

priorityQueue.enqueue(1, 1);

console.log(priorityQueue.items);
/*[ QElement { element: 1, priority: 1 },
  QElement { element: 5, priority: 2 },
  QElement { element: 2, priority: 2 },
  QElement { element: 1, priority: 6 },
  QElement { element: 1, priority: 9 },
  QElement { element: 9, priority: 10 } ]*/





