package com.scala.practice.happiestminds

import scala.collection.mutable

object PrintTree {

  var queue: mutable.Queue[Node] = mutable.Queue()

  def main(args: Array[String]): Unit = {

    var tree = new BinaryTree
    tree.head = new Node

    tree.head.element = 8
    tree.head.left = new Node
    tree.head.right = new Node
    tree.head.left.element = 3
    tree.head.right.element = 10

    tree.head.right.right = new Node
    tree.head.right.right.element = 14
    tree.head.right.right.left = new Node
    tree.head.right.right.left.element = 13

    tree.head.left.left = new Node
    tree.head.left.right = new Node
    tree.head.left.left.element = 1
    tree.head.left.right.element = 6

    tree.head.left.right.left = new Node
    tree.head.left.right.right = new Node
    tree.head.left.right.left.element = 4
    tree.head.left.right.right.element = 7

    printElements(tree.head)
    queue.enqueue(tree.head)
    printLevels()
  }

  def printElements(node: Node): Unit = {
    if (node != null) {
      if (node.left == null && node.right == null)
        print(node.element + ",")
      else {
        if (node.left != null) {
          printElements(node.left)
          //          print(node.left.element)
        }
        if (node.right != null) {
          printElements(node.right)
          //          print(node.right.element)
        }
        print(node.element + ",")
      }

    }
  }

  def printLevels(): Unit = {
    while (queue.nonEmpty) {
      val current = queue.dequeue()
      println(current.element)
      if (current.left != null)
        queue.enqueue(current.left)
      if (current.right != null)
        queue.enqueue(current.right)
    }
  }


}
