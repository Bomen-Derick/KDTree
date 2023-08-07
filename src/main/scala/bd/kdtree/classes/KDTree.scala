package bd.kdtree.classes

import bd.kdtree.empty
import bd.kdtree.traits.{Comparison, KD_Tree}
import bd.kdtree.traits.given

import scala.annotation.{tailrec, targetName}

/**
 * Created by Bomen Derick.
 */

/**
 * A KD-Tree having data as point with left and right branches
 * @param tData
 * point representation of the KD-Tree
 * @param tLeft
 * left branch of the KD-Tree
 * @param tRight
 * right branch of the KD-Tree
 * @tparam T
 * type of points hold by the KD-Tree
 */
case class KDTree[+T](tData: Point[T], tLeft: KD_Tree[T], tRight: KD_Tree[T]) extends KD_Tree[T]:
  
  /**
   * @return
   * point representation of a given KD-Tree
   */
  override val data: Point[T] = tData

  /**
   * @return
   * left branch of a given KD-Tree
   */
  override val left: KD_Tree[T] = tLeft

  /**
   * @return
   * right branch of a given KD-Tree
   */
  override val right: KD_Tree[T] = tRight

  /**
   * Dimension of points in a given KD-Tree
   */
  override val dimension: Int = tData.value.size

  /**
   * Determine whether a given KD-Tree is empty or not
   *
   * @return
   * returns true if a KD-Tree contains no points, false otherwise
   */
  override def isEmpty: Boolean =
    if tData.value.isEmpty then true else false

  /**
   * Determine the depth of a given KD-Tree (How deep a KD-Tree is)
   *
   * @return
   * returns the integer representation of the depth of a KD-Tree (in other words the number of levels a KD-Tree has)
   */
  override def depth: Int =
    if left.isEmpty && right.isEmpty then 0
    else
     lazy val leftDepth = 1 + left.depth
     lazy val rightDepth = 1 + right.depth
      if leftDepth > rightDepth then leftDepth else rightDepth

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
  override def contains[TT >: T](point: Point[TT], depth: Int = 0)(using comparison: Comparison[TT]): Boolean =
    assume(point.value.size == dimension, s"Required dimension is $dimension")
    lazy val axis: Int = depth % dimension
    if data == point then true
    else if comparison.isLessThan(point.value(axis), data.value(axis)) then left.contains(point, depth + 1)
    else right.contains(point, depth + 1)

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
    lazy val pointToSearch: Point[TT] = Point(point.toSeq)
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
  override def contains[TT >: T : Comparison](kd_tree: KD_Tree[TT]): Boolean = kd_tree match
    case Leaf(lData) => contains(lData)
    case KDTree(tData, tLeft, tRight) =>
      if tLeft.isEmpty && tRight.isEmpty then contains(tData)
      else if tLeft.isEmpty then contains(tData) && contains(tRight)
      else contains(tData) && contains(tLeft)
    case _ => false

  /**
   * A protected utility method to insert a given point to a KD-Tree
   *
   * @param point
   * given point to be inserted 
   * @param depth
   * depth at which to insert the point
   * @tparam TT
   * the type of the given point
   * @return
   * returns a new KD-Tree with the given point inserted if insertion criteria are met(eg: same dimension)
   */
  override def insertAPoint[TT >: T](point: Point[TT], depth: Int)(using comparison: Comparison[TT]): KD_Tree[TT] =
    lazy val axis: Int = depth % dimension
    if comparison.isLessThan(point.value(axis), data.value(axis)) then KDTree(data, left.insertAPoint(point, depth + 1), right)
    else KDTree(data, left, right.insertAPoint(point, depth + 1))

  /**
   * Insert a data to a KD-Tree as a point
   *
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
    assume(kdtree.dimension == dimension, s"Required dimension is $dimension-D")
    lazy val axis: Int = depth % dimension
    if comparison.isLessThan(kdtree.data.value(axis), data.value(axis)) then KDTree(data, left.insert(kdtree, depth + 1), right)
    else KDTree(data, left, right.insert(kdtree, depth + 1))

  /**
   * Insert a KD-Tree 
   *
   * @param tree
   * given KD-Tree to be inserted
   * @tparam TT
   * concrete type of points in the given KD-Tree
   * @return
   * returns a new KD-Tree with the given KD-Tree inserted if insertion criteria are met(eg: same dimension)
   */
  override def insert[TT >: T : Comparison](kd_tree: KD_Tree[TT]): KD_Tree[TT] = kd_tree match
    case Leaf(lData) => insert(lData)
    case kdtree: KDTree[TT] => insert(kdtree, 0)
    case _ => this

  /**
   * Determine the minimum of three nodes(points), including the node(point) calling the method
   * @param node_1
   * first node
   * @param node_2
   * second node
   * @param axis
   * axis of comparison
   * @param comparison
   * given instance used to compare between two points
   * @tparam TT
   * type of points each node has
   * @return
   * returns the minimum node among the three nodes(points)
   */
  @tailrec
  final private def minimumOfThreeNodes[TT >: T](node_1: KDTree[TT], node_2: KDTree[TT], axis: Int)(using comparison: Comparison[TT]): KDTree[TT] =
    if isEmpty then
      if comparison.isLessThan(node_1.data.value(axis), node_2.data.value(axis)) then node_1 else node_2
    else if node_1.isEmpty then
      if comparison.isLessThan(node_2.data.value(axis), data.value(axis)) then node_2 else KDTree(data, left, right)
    else if node_2.isEmpty then
      if comparison.isLessThan(node_1.data.value(axis), data.value(axis)) then node_1 else KDTree(data, left, right)
    else if this.isEmpty && node_1.isEmpty && node_2.isEmpty then
      KDTree()
    else if comparison.isLessThan(node_1.data.value(axis), data.value(axis)) then
      node_1.minimumOfThreeNodes(node_2, KDTree(), axis)
    else minimumOfThreeNodes(node_2, KDTree(), axis)

  /**
   * determine the minimum node(point) in a KD-Tree
   * @param givenAxis
   * given axis of comparison
   * @param depth
   * depth of the KD-Tree
   * @tparam TT
   * type of points in the KD-Tree
   * @return
   * returns the minimum node(point) in a KD-Tree
   */
  final private def findMinimumNode[TT >: T : Comparison](givenAxis: Int, depth: Int = 0): KDTree[TT] =
    lazy val currentAxis = depth % dimension
    if isEmpty then KDTree()
    else if currentAxis == givenAxis then
      left match
        case leftB: KDTree[T] => leftB.findMinimumNode(givenAxis, depth + 1)
        case empty => KDTree(data, empty, right)
    else (left, right) match
      case (leftB: KDTree[T], rightB: KDTree[T]) =>
        minimumOfThreeNodes(leftB.findMinimumNode(givenAxis, depth + 1), rightB.findMinimumNode(givenAxis, depth + 1), givenAxis)

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
  override def delete[TT >: T](point: Point[TT], depth: Int = 0)(using comparison: Comparison[TT]): KD_Tree[TT] =
    assume(point.value.size == dimension, s"required dimension is $dimension-D")
    lazy val axis = depth % dimension
    if point == data then
      if !right.isEmpty then
        right match
          case branch: KDTree[TT] =>
            lazy val newPoint = branch.findMinimumNode(axis).data
            KDTree(newPoint, left, branch.delete(newPoint, depth + 1))
          case leaf: Leaf[TT] => KDTree(leaf.data, left, empty)
        end match
      else if !left.isEmpty then
        left match
          case branch: KDTree[TT] =>
            lazy val newPoint = branch.findMinimumNode(axis).data
            KDTree(newPoint, branch.delete(newPoint, depth + 1), right)
          case leaf: Leaf[TT] => KDTree(leaf.data, empty, right)
        end match
      else empty
    else if comparison.isLessThan(point.value(axis), data.value(axis)) then
      KDTree(data, left.delete(point, depth + 1), right)
    else KDTree(data, left, right.delete(point, depth + 1))

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
  override def display: String =
    s"KDTree(${data.value.mkString("(", ",", ")")}, ${left.display}, ${right.display})"

  /**
   * Display all points of a KD-Tree in a Tree like structure
   */
  override def prettyPrint(): Unit = ???

end KDTree

/**
 * Companion object to help create multiple constructor arguments
 */
case object KDTree:
  def apply[T](): KD_Tree[T] = empty

  @targetName("Constructing KD-Tree by passing a point as Sequence collection")
  def apply[T](point: Seq[T]): KDTree[T] = KDTree(Point(point), empty, empty)

  @targetName("Constructing a KD-Tree by passing a point without specifying the collection type")
  def apply[T](point: T*): KDTree[T] = KDTree(point.toSeq)

  @targetName("Identify the point with the smallest dimension and truncate all subsequent points to it.")
  def apply[T: Comparison](points: Seq[T]*): KDTree[T] =
    lazy val dimension = points.minBy(_.size).size
    val obtained = points.map(point => if point.size > dimension then point.take(dimension) else point)
    val root = KDTree(obtained.head)

    @tailrec
    def insertGivenPoints[TT >: T : Comparison](kd_tree: KD_Tree[T], points: Seq[Seq[T]]): KD_Tree[T] =
      if points.isEmpty then kd_tree
      else insertGivenPoints(kd_tree.insert(Point(points.head)), points.tail)

    insertGivenPoints(root, obtained.tail) match
      case tree: KDTree[T] => tree
      case _ => KDTree.apply()
