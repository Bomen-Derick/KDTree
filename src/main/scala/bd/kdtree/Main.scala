package bd.kdtree

import bd.kdtree.classes.{KDTree, Point, Leaf}
import bd.kdtree.traits.KD_Tree
import bd.kdtree.traits.given
/**
 * Created by Bomen Derick.
 */
object Main extends App {

  val kd_tree = KDTree(Seq(4,5), Seq(2,3), Seq(5,6), Seq(1,3), Seq(5, 2))
  val kdtree1 = Leaf(Point(Seq(2,3)))

  println("Leaf: " + kdtree1.display)
  println("tree: " + kd_tree.display)
  println("-"*100)
  println(kdtree1.delete(2,3).delete(2,3).display)
  println(kd_tree.delete(1,3).delete(1,3).display)

}
