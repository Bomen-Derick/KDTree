package bd.kdtree

import bd.kdtree.classes.KDTree
import bd.kdtree.traits.KD_Tree
import bd.kdtree.traits.given
/**
 * Created by Bomen Derick.
 */
object Main extends App {

  val kd_tree = KDTree()
  val kdtree = kd_tree.insert(3,4)

  println(kdtree)
  println(kdtree.contains(3,4))
}
