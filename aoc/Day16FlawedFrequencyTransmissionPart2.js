/*
 --- Part Two ---
Now that your FFT is working, you can decode the real signal.

The real signal is your puzzle input repeated 10000 times. Treat this new signal as a single input list. Patterns are still calculated as before, and 100 phases of FFT are still applied.

The first seven digits of your initial input signal also represent the message offset. The message offset is the location of the eight-digit message in the final output list. Specifically, the message offset indicates the number of digits to skip before reading the eight-digit message. For example, if the first seven digits of your initial input signal were 1234567, the eight-digit message would be the eight digits after skipping 1,234,567 digits of the final output list. Or, if the message offset were 7 and your final output list were 98765432109876543210, the eight-digit message would be 21098765. (Of course, your real message offset will be a seven-digit number, not a one-digit number like 7.)

Here is the eight-digit message in the final output list after 100 phases. The message offset given in each input has been highlighted. (Note that the inputs given below are repeated 10000 times to find the actual starting input lists.)

03036732577212944063491565474664 becomes 84462026.
02935109699940807407585447034323 becomes 78725270.
03081770884921959731165446850517 becomes 53553731.
After repeating your input signal 10000 times and running 100 phases of FFT, what is the eight-digit message embedded in the final output list?

Your puzzle answer was 18650834.
 */
let input = '59791871295565763701016897619826042828489762561088671462844257824181773959378451545496856546977738269316476252007337723213764111739273853838263490797537518598068506295920453784323102711076199873965167380615581655722603274071905196479183784242751952907811639233611953974790911995969892452680719302157414006993581489851373437232026983879051072177169134936382717591977532100847960279215345839529957631823999672462823375150436036034669895698554251454360619461187935247975515899240563842707592332912229870540467459067349550810656761293464130493621641378182308112022182608407992098591711589507803865093164025433086372658152474941776320203179747991102193608';
let steps = new Array(10000);

//  let input = '03081770884921959731165446850517';
//  let steps = new Array(10000);


let inputArray = [];
for (let index = 0; index < input.length; index++) {
  inputArray.push(parseInt(input.charAt(index)));
}
let outputArray = [];
for (let step of steps) {
  for (let index = 0; index < inputArray.length; index++) {
    outputArray.push(inputArray[index]);
  }
}

let inputOffset = parseInt(inputArray.slice(0,7).join(' ').replace(/\s/g, ''));
console.log("inputOffset---", inputOffset);

outputArray = outputArray.slice(inputOffset);

steps = new Array(100);
for (let step of steps) {
  for (let index = outputArray.length-1; index >=0; index--) {
    outputArray[index] = Math.abs((outputArray[index + 1] || 0) + outputArray[index]) % 10;
  }
}
output = outputArray.join(' ').replace(/\s/g, '');
console.log('what is the eight-digit message embedded in the final output list?', output.slice(0, 8));