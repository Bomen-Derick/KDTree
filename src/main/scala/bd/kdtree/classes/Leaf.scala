package bd.kdtree.classes

import bd.kdtree.empty
import bd.kdtree.traits.{Comparison, KD_Tree}
import bd.kdtree.traits.given

/**
 * Created by Bomen Derick.
 */

/**
 * Leaf representation of a KD-Tree (a KD-Tree with empty left and right branches)
 *
 * @param lData
 * the point hold at the leaf level
 * @tparam T
 * type of the point
 */
case class Leaf[+T](lData: Point[T]) extends KD_Tree[T]:
  
  /**
   * @return
   * point representation of a given KD-Tree
   */
  override val data: Point[T] = lData

  /**
   * @return
   * left branch of a given KD-Tree
   */
  override val left: KD_Tree[T] = empty

  /**
   * @return
   * right branch of a given KD-Tree
   */
  override val right: KD_Tree[T] = empty

  /**
   * Dimension of points in a given KD-Tree
   */
  override val dimension: Int = lData.value.size

  /**
   * Determine whether a given KD-Tree is empty or not
   *
   * @return
   * returns true if a KD-Tree contains no points, false otherwise
   */
  override def isEmpty: Boolean = false

  /**
   * Determine the depth of a given KD-Tree (How deep a KD-Tree is)
   * the depth of a leaf if zero(0) since the left and right branches are empty.
   * It's worth noting that a KD-Tree start with a base level of zero(0) at the root level
   * @return
   * returns the integer representation of the depth of a KD-Tree (in other words the number of levels a KD-Tree has)
   */
  override def depth: Int = 0

  /**
   * Determine if a KD-Tree contains a given point
   *
   * @param point
   * the point to search as a sequence
   * @param depth
   * the depth of a given KD-Tree
   * @tparam TT
   * concrete type of point
   * @return
   * returns true if point is found false otherwise
   */
  override def contains[TT >: T : Comparison](point: Point[TT], depth: Int = 0): Boolean =
    assume(point.value.size == dimension, s"Required dimension is $dimension")
    point == data

  /**
   * Determine if a KD-Tree contains a given point
   *
   * @param point
   * one or more numeric numbers in a sequence of type TT
   * @tparam TT
   * concrete type of point
   * @return
   * returns true if point is found false otherwise
   */
  override def contains[TT >: T : Comparison](point: TT*): Boolean =
    val pointToSearch: Point[TT] = Point(point.toSeq)
    contains(pointToSearch)

  /**
   * Determine if a KD-Tree contains another KD_Tree
   *
   * @param node
   * the KD-Tree to search
   * @tparam TT
   * concrete type of points in the given KD-Tree
   * @return
   * returns true if node is found, false otherwise
   */
  override def contains[TT >: T : Comparison](kd_tree: KD_Tree[TT]): Boolean =
    kd_tree match
      case Leaf(lData) => contains(lData)
      case KDTree(tData, _, _) => contains(tData)
      case _ => false

  /**
   * A utility method to insert a given point to a KD-Tree
   * other insert methods serves as a wrapper over this method.
   * @param point
   * given point to be inserted 
   * @param depth
   * depth at which to insert the point
   * @param dimension
   * the dimension of the given point
   * @tparam TT
   * the type of the given point
   * @return
   * returns a new KD-Tree with the given point inserted if insertion criteria are met(eg: same dimension)
   */
  override def insertAPoint[TT >: T](point: Point[TT], depth: Int)(using comparison: Comparison[TT]): KD_Tree[TT] =
    lazy val axis: Int = depth % dimension
    if comparison.isLessThan(point.value(axis), data.value(axis)) then KDTree(data, Leaf(point), empty)
    else KDTree(data, empty, Leaf(point))

  /**
   * Insert a data to a KD-Tree as a point without precising the depth and the dimension
   * a wrapper over insertAPoint
   * @param data
   * representation of the point to be inserted
   * @tparam TT
   * the type of the point
   * @return
   * returns a new KD-Tree with the point inserted if insertion criteria are met(eg: same dimension) 
   * @see
   * [[Point]]
   */
  override def insert[TT >: T : Comparison](data: Point[TT]): KD_Tree[TT] =
    assume(data.value.size == dimension, s"Required dimension is $dimension-D")
    insertAPoint(data, 0)

  /**
   * Insert a sequence of numbers to a KD-Tree as a point
   *
   * @param point
   * given point to be inserted as a sequence of numbers
   * @tparam TT
   * the type of the point
   * @return
   * returns a new KD-Tree with the point inserted if insertion criteria are met(eg: same dimension) 
   * @see
   * [[Point]]
   */
  override def insert[TT >: T : Comparison](point: TT*): KD_Tree[TT] =
    lazy val pointToAdd: Point[TT] = Point(point.toSeq)
    assume(pointToAdd.value.size == dimension, s"Required dimension is $dimension-D")
    insertAPoint(pointToAdd, 0)

  /**
   * Insert a KD-Tree at a particular depth in the tree
   *
   * @param node
   * given KD-Tree to be inserted
   * @param depth
   * depth at which the KD-Tree is to be inserted
   * @tparam TT
   * concrete type of points in the KD-Tree
   * @return
   * returns a new KD-Tree with the given KD-Tree inserted if insertion criteria are met(eg: same dimension)
   */
  override def insert[TT >: T](kdtree: KDTree[TT], depth: Int)(using comparison: Comparison[TT]): KD_Tree[TT] =
    assume(dimension == kdtree.dimension, s"Required dimension is $dimension")
    val axis: Int = depth % dimension
    if comparison.isLessThan(kdtree.data.value(axis), data.value(axis)) then KDTree(data, kdtree, empty)
    else KDTree(data, empty, kdtree)

  /**
   * Insert a KD-Tree to another KD-Tree
   *
   * @param tree
   * given KD-Tree to be inserted
   * @tparam TT
   * concrete type of points in the given KD-Tree
   * @return
   * returns a new KD-Tree with the given KD-Tree inserted if insertion criteria are met(eg: same dimension)
   */
  override def insert[TT >: T : Comparison](kd_tree: KD_Tree[TT]): KD_Tree[TT] = kd_tree match
    case leaf: Leaf[TT] => insert(leaf.data)
    case kdtree: KDTree[TT] => insert(kdtree, 0)
    case _ => this

  /**
   * Remove a given point from a KD-Tree
   *
   * @param point
   * point to be removed
   * @param depth
   * depth at which to remove the point
   * @tparam TT
   * type of the point to be removed
   * @return
   * returns a new KD-Tree excluding the given point if deletion criteria are met(eg: same dimension)
   */
  override def delete[TT >: T : Comparison](point: Point[TT], depth: Int = 0): KD_Tree[TT] =
    assume(point.value.size == dimension, s"Required dimension is $dimension-D")
    if point == data then empty
    else this

  /**
   * Remove a point from a KD-Tree given as a sequence of numbers
   *
   * @param point
   * point to be removed given as a sequence of numbers
   * @tparam TT
   * type of the point
   * @return
   * returns a new KD-Tree excluding the given point if deletion criteria are met(eg: same dimension)
   */
  override def delete[TT >: T : Comparison](point: TT*): KD_Tree[TT] =
    val pointToDelete: Point[TT] = Point(point.toSeq)
    delete(pointToDelete)

  /**
   * remove a given KD-Tree
   *
   * @param node
   * KD-Tree to be removed
   * @tparam TT
   * type of points in the given KD-Tree
   * @return
   * returns a new KD-Tree excluding the given KD-Tree if deletion criteria are met(eg: same dimension)
   */
  override def delete[TT >: T : Comparison](kd_tree: KD_Tree[TT]): KD_Tree[TT] =
    if contains(kd_tree) then
      delete(kd_tree.data)
      .delete(kd_tree.left)
      .delete(kd_tree.right)
    else this

  /**
   * Search a given point from a KD-Tree
   *
   * @param point
   * point to be searched
   * @tparam TT
   * type of point
   * @return
   * if the point is found, return a new KD-Tree with the given point as the root else return an empty KD-Tree
   */
  override def search[TT >: T : Comparison](point: TT*): KD_Tree[TT] = ???

  /**
   * Search a given point from a KD-Tree
   *
   * @param point
   * point to be searched
   * @param depth
   * depth at which to search the point
   * @tparam TT
   * type of the point
   * @return
   * if the point is found, return a new KD-Tree with the given point as the root else return an empty KD-Tree
   */
  override def search[TT >: T : Comparison](point: Point[TT], depth: Int): KD_Tree[TT] = ???

  /**
   * display all points of a KD-Tree
   */
  override def display: String = data.value.mkString("(", ",", ")")

  /**
   * Display all points of a KD-Tree in a Tree like structure
   */
  override def prettyPrint(): Unit = ???
  
end Leaf
