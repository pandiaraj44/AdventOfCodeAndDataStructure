const QElement = require('./QElement');

class PriorityQueue {
    constructor() {
      this.items = []; // Queue items
    }

    isEmpty() {
      return this.items.length === 0;
    }
  
    enqueue(element, priority) { // Adding elements to the queue
      const qElement = new QElement(element, priority);
      const index = this.items.findIndex((item) => item.priority > qElement.priority);
      if (index === -1) {
        this.items.push(qElement); // Add the element to the last
      } else {
        this.items.splice(index, 0, qElement); // Place the element next to the index element
      }
    }
  
    dequeue() { // Removing elements from the queue
      if (this.isEmpty()) {
        throw new Error("Underflow");
      } else {
        return this.items.shift();
      }
    }
  }

  module.exports = PriorityQueue;