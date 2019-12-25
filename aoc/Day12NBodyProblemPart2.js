/*--- Part Two ---
All this drifting around in space makes you wonder about the nature of the universe. Does history really repeat itself? You're curious whether the moons will ever return to a previous state.

Determine the number of steps that must occur before all of the moons' positions and velocities exactly match a previous point in time.

For example, the first example above takes 2772 steps before they exactly match a previous point in time; it eventually returns to the initial state:

After 0 steps:
pos=<x= -1, y=  0, z=  2>, vel=<x=  0, y=  0, z=  0>
pos=<x=  2, y=-10, z= -7>, vel=<x=  0, y=  0, z=  0>
pos=<x=  4, y= -8, z=  8>, vel=<x=  0, y=  0, z=  0>
pos=<x=  3, y=  5, z= -1>, vel=<x=  0, y=  0, z=  0>

After 2770 steps:
pos=<x=  2, y= -1, z=  1>, vel=<x= -3, y=  2, z=  2>
pos=<x=  3, y= -7, z= -4>, vel=<x=  2, y= -5, z= -6>
pos=<x=  1, y= -7, z=  5>, vel=<x=  0, y= -3, z=  6>
pos=<x=  2, y=  2, z=  0>, vel=<x=  1, y=  6, z= -2>

After 2771 steps:
pos=<x= -1, y=  0, z=  2>, vel=<x= -3, y=  1, z=  1>
pos=<x=  2, y=-10, z= -7>, vel=<x= -1, y= -3, z= -3>
pos=<x=  4, y= -8, z=  8>, vel=<x=  3, y= -1, z=  3>
pos=<x=  3, y=  5, z= -1>, vel=<x=  1, y=  3, z= -1>

After 2772 steps:
pos=<x= -1, y=  0, z=  2>, vel=<x=  0, y=  0, z=  0>
pos=<x=  2, y=-10, z= -7>, vel=<x=  0, y=  0, z=  0>
pos=<x=  4, y= -8, z=  8>, vel=<x=  0, y=  0, z=  0>
pos=<x=  3, y=  5, z= -1>, vel=<x=  0, y=  0, z=  0>
Of course, the universe might last for a very long time before repeating. Here's a copy of the second example from above:

<x=-8, y=-10, z=0>
<x=5, y=5, z=10>
<x=2, y=-7, z=3>
<x=9, y=-8, z=-3>
This set of initial positions takes 4686774924 steps before it repeats a previous state! Clearly, you might need to find a more efficient way to simulate the universe.

How many steps does it take to reach the first state that exactly matches a previous state?

Your puzzle answer was 278013787106916.*/

// let input = '<x=-8, y=-10, z=0>:<x=5, y=5, z=10>:<x=2, y=-7, z=3>:<x=9, y=-8, z=-3>';
// let steps = new Array(100);

// const fs = require('fs');
// var output = fs.createWriteStream('output.txt', {
//   flags: 'a' // 'a' means appending (old data will be preserved)
// })


// let input = '<x=-1, y=0, z=2>:<x=2, y=-10, z=-7>:<x=4, y=-8, z=8>:<x=3, y=5, z=-1>';
// let steps = new Array(10);


// output
// xMatchingCount-- 286332
// yMatchingCount-- 161428
// zMatchingCount-- 96236
// output LCM of above 3

let input ='<x=0, y=4, z=0>:<x=-10, y=-6, z=-14>:<x=9, y=-16, z=-3>:<x=6, y=-1, z=2>';

let moonsInputArray = input.split(":");
let moons = [];
let count = 1;
for (let input of moonsInputArray) {
  input = input.replace(/</g, "");
  input = input.replace(/>/g, "");
  input = input.replace(/\s/g, "");
  input = input.split(",");
  const moon = {
    id: count
  };
  for (let input1 of input) {
    input1 = input1.split("=");
    moon[input1[0]] = Number.parseInt(input1[1]);
    moon['v' + input1[0]] = 0;
  }
  count++;
  moons.push(moon);
}

let initialMoons = JSON.parse(JSON.stringify(moons));

function match(axisKey, velacityKey, moons) {
  let isMatched = false;
  let stepsTaken = 0;
  while (!isMatched) {
    applyGravity(moons);
    applyVelacity(moons);
    isMatched = checkIfMoonAxisAreSame(axisKey, velacityKey, initialMoons, moons);
    stepsTaken++;
  }
  return stepsTaken;
}


let xMatchingCount = match('x', 'vx', JSON.parse(JSON.stringify(initialMoons)));
console.log('xMatchingCount--', xMatchingCount);

let yMatchingCount = match('y', 'vy', JSON.parse(JSON.stringify(initialMoons)));
console.log('yMatchingCount--', yMatchingCount);

let zMatchingCount = match('z', 'vz', JSON.parse(JSON.stringify(initialMoons)));
console.log('zMatchingCount--', zMatchingCount);






function checkIfMoonAxisAreSame(axisKey, velacityKey, initialMoons, moons) {
  let matchedCount = 0;
  for (let i = 0; i < initialMoons.length; i++) {
    if (initialMoons[i][axisKey] == moons[i][axisKey]
      && initialMoons[i][velacityKey] == moons[i][velacityKey]) {
        matchedCount++;
    } else {
      break;
    }
  }
  return (matchedCount === 4) ? true : false;
}


function calculateEnergy(moons) {
  let totalEnergy = 0;
  for (moon of moons) {
    const keys = ['x', 'y', 'z'];
    const velacityKeys = ['vx', 'vy', 'vz'];
    let pot = 0;
    for (key of keys) {
      pot = pot + Math.abs(moon[key]);
    }

    let kin = 0;
    for (key of velacityKeys) {
      kin = kin + Math.abs(moon[key]);
    }

    let energy = pot * kin;
    totalEnergy = totalEnergy + energy;
    console.log(energy);
  }
  return totalEnergy;
}


function applyGravity(moons) {
  for (moon1 of moons) {
    for (moon2 of moons) {
      if (moon1.id !== moon2.id) {
        const keys = ['x', 'y', 'z'];
        for (key of keys) {
          let moon1AxisValue = moon1[key];
          let moon2AxisValue = moon2[key];
          if (moon1AxisValue < moon2AxisValue) {
            moon1['v' + key] = moon1['v' + key] + 1;
          } else if (moon1AxisValue > moon2AxisValue) {
            moon1['v' + key] = moon1['v' + key] - 1;
          }
        }
      }
    }
  }
  return moons;
}

function applyVelacity(moons) {
  for (moon of moons) {
    const keys = ['x', 'y', 'z'];
    for (key of keys) {
      moon[key] = moon[key] + moon['v' + key];
    }
  }
  return moons;
}