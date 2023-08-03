package bd.kdtree

import bd.kdtree.classes.{Leaf, Point}
import bd.kdtree.traits.{Comparison, KD_Tree}

/**
 * Created by Bomen Derick.
 */
case object empty extends KD_Tree[Nothing]:

  /**
   * @return
   * point representation of a given KD-Tree
   */
  override val data: Point[Nothing] = Point()

  /**
   * @return
   * left branch of a given KD-Tree
   */
  override val left: KD_Tree[Nothing] = empty

  /**
   * @return
   * right branch of a given KD-Tree
   */
  override val right: KD_Tree[Nothing] = empty

  /**
   * Dimension of points in a given KD-Tree
   */
  override val dimension: Int = 0

  /**
   * Determine whether a given KD-Tree is empty or not
   *
   * @return
   * returns true if a KD-Tree contains no points, false otherwise
   */
  override def isEmpty: Boolean = true

  /**
   * Determine the depth of a given KD-Tree (How deep a KD-Tree is)
   *
   * @return
   * returns the integer representation of the depth of a KD-Tree (in other words the number of levels a KD-Tree has)
   */
  override def depth: Int = 0

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
  override def contains[TT >: Nothing : Comparison](point: TT*): Boolean = false

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
  override def contains[TT >: Nothing : Comparison](point: Point[TT], depth: Int): Boolean = false

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
  override def contains[TT >: Nothing : Comparison](node: KD_Tree[TT]): Boolean = false

  /**
   * A protected utility method to insert a given point to a KD-Tree
   *
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
  override def insertAPoint[TT >: Nothing : Comparison](point: Point[TT], depth: Int, dimension: Int): KD_Tree[TT] = Leaf(point)

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
  override def insert[TT >: Nothing : Comparison](data: Point[TT]): KD_Tree[TT] = Leaf(data)

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
  override def insert[TT >: Nothing : Comparison](point: TT*): KD_Tree[TT] = Leaf(Point(point.toSeq))

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
  override def insert[TT >: Nothing : Comparison](node: KD_Tree[TT], depth: Int): KD_Tree[TT] = node

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
  override def insert[TT >: Nothing : Comparison](tree: KD_Tree[TT]): KD_Tree[TT] = tree

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
  override def delete[TT >: Nothing : Comparison](point: Point[TT], depth: Int): KD_Tree[TT] = empty

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
  override def delete[TT >: Nothing : Comparison](point: TT*): KD_Tree[TT] = empty

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
  override def delete[TT >: Nothing : Comparison](node: KD_Tree[TT]): KD_Tree[TT] = empty

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
  override def search[TT >: Nothing : Comparison](point: TT*): KD_Tree[TT] = empty

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
  override def search[TT >: Nothing : Comparison](point: Point[TT], depth: Int): KD_Tree[TT] = empty

  /**
   * display all points of a KD-Tree
   */
  override def print(): Unit = println(empty)

  /**
   * Display all points of a KD-Tree in a Tree like structure
   */
  override def prettyPrint(): Unit = println(empty)

end empty

