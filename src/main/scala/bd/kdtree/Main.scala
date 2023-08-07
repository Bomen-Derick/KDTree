package bd.kdtree

import bd.kdtree.classes.{KDTree, Point, Leaf}
import bd.kdtree.traits.KD_Tree
import bd.kdtree.traits.given
/**
 * Created by Bomen Derick.
 */
object Main extends App {

  val kd_tree1 = KDTree(Seq(4,5), Seq(2,3), Seq(5,6), Seq(1,3), Seq(5, 2))
  val kd_tree2 = Leaf(Point(Seq(2,3)))
  val kd_tree3 = KDTree(Seq(2,3),Seq(1,3),Seq(1,7))
  val kd_tree4 = KDTree(Point(5,6), KDTree(Point(5,2)), empty)

  println("Leaf: " + kd_tree2.display)
  println("tree: " + kd_tree1.display)
  println("-"*100)
  println(kd_tree2.delete(2,3).delete(5,2).display)
  println(kd_tree1.delete(2,3).delete(5,2).display)
  println(kd_tree1.delete(1,3).contains(1,3))
  println(kd_tree2.contains(1,3))
  println(kd_tree3.delete(1,3).contains(KDTree(Seq(1,3))))
  println(KDTree(Seq(1,3)).display)
  println(kd_tree1.contains(kd_tree3.delete(1,7)))
  println(kd_tree1.delete(kd_tree3.delete(1,7)).display)
}
